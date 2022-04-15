(ns iapp.slots
  (:require [iapp.slot :as slot]
            [cljfx.api :as fx]))

(defn- slot-view [{:keys [col slot value]}]
  {:fx/type slot/view
   :col col
   :slot slot
   :value value
   :grid-pane/column col
   :grid-pane/halignment :center})

(defn view [slots]
  (let [percent-width   (/ 100 (count slots))
        slot-views      (->> slots (map slot-view) (vec))
        col-constraints (map (fn [_] {:fx/type       :column-constraints
                                      :percent-width percent-width}) slots)]
    {:fx/type            :grid-pane
     :padding            10
     :column-constraints col-constraints
     :children           slot-views}))

(defn component [{:keys [fx/context]}]
  (let [slots (vals (fx/sub-val context :slots))]
    (view slots)))

