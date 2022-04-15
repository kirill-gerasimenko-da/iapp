(ns iapp.slot)

(defn view [{:keys [slot value col]}]
  {:fx/type   :v-box
   :spacing   10
   :alignment :center
   :children  [{:fx/type         :text-field
                :alignment       :center
                :max-width       40
                :text            (str slot)
                :on-text-changed {:event/type :ev/set-slot-value :col col}}
               {:fx/type :text
                :style   {:-fx-font [20 :serif]}
                :font-smoothing-type :gray
                :text    value}]})
