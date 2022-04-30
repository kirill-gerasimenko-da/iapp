(ns iapp.core
  (:gen-class)
  (:require [cljfx.api :as fx]
            [clojure.pprint :refer [pprint]]
            [clojure.core.cache :as cache]
            [iapp.root :as root])
  (:import [javafx.scene.input Clipboard]
           [javafx.application Platform]))

(defn get-clipboard-text []
  @(fx/on-fx-thread
    (let [cb (.getString (Clipboard/getSystemClipboard))]
      cb)))

(def *app-context
  (atom (fx/create-context
         {:password ""
          :slots {0 {:col 0 :slot nil :value nil}
                  1 {:col 1 :slot nil :value nil}
                  2 {:col 2 :slot nil :value nil}
                  3 {:col 3 :slot nil :value nil}
                  4 {:col 4 :slot nil :value nil}}}
         cache/lru-cache-factory)))

(defmulti event-handler :event/type)
(defmethod event-handler :default [e] (pprint e))

(defn stage [_]
  {:fx/type :stage
   :showing true
   :resizable false
   :icons ["iapp/logo.png"]
   :title "Password util"
   :scene {:fx/type :scene :root {:fx/type root/component}}})

(defn- get-slot-value [password slot]
  (when-some [s slot]
    (str (get password (- s 1) nil))))

(defn- update-password-slots [password slots]
  (update-vals
   slots
   (fn [val] (assoc val :value (get-slot-value password (:slot val))))))

(defmethod event-handler :ev/set-password [{:keys [fx/event fx/context]}]
  (let [password event
        slots    (->> (fx/sub-val context :slots)
                      (update-password-slots password))]
    {:context (fx/swap-context context merge {:password password :slots slots})}))

(defmethod event-handler :ev/paste-password [{:keys [fx/context]}]
  (let [password (get-clipboard-text)
        slots    (->> (fx/sub-val context :slots)
                      (update-password-slots password))]
    {:context (fx/swap-context context merge {:password password :slots slots})}))

(defmethod event-handler :ev/set-slot-value [{:keys [fx/event fx/context col]}]
  (let [password (fx/sub-val context :password)
        slot     (parse-long event)
        value    (get-slot-value password slot)]
    {:context (fx/swap-context context assoc-in [:slots col] {:col col :slot slot :value value})}))

;; (def app
;;   (fx/create-app *app-context
;;     :event-handler event-handler
;;     :desc-fn (fn [_]
;;                {:fx/type stage})))

;; ((:renderer app))

(defn -main [& args]
  (Platform/setImplicitExit true)
  (fx/create-app *app-context
    :event-handler event-handler
    :desc-fn (fn [_]
               {:fx/type stage})))

