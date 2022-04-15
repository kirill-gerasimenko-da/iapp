(ns iapp.password)

(defn view [{:keys [min-width on-change password]}]
  {:fx/type :v-box
   :spacing 5
   :min-width min-width
   :children [{:fx/type :label
               :text "Password:"}
              {:fx/type :password-field
               :text password
               :on-text-changed on-change}]})



