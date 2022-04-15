(ns iapp.root
  (:require [iapp.password :as password]
            [iapp.slots :as slots]
            [cljfx.api :as fx]))

(defn view [{:keys [password]}]
  {:fx/type :v-box
   :padding 10
   :spacing 10
   :children [{:fx/type :h-box
               :spacing 10
               :alignment :bottom-center
               :children [{:fx/type password/view
                           :min-width 300
                           :password password
                           :on-change {:event/type :ev/set-password}}
                          {:fx/type :button
                           :text "Paste"
                           :on-action {:event/type :ev/paste-password}}]}
              {:fx/type slots/component}]})

(defn component [{:keys [fx/context]}]
  (let [password (fx/sub-val context :password)]
    (view {:password password})))
