(ns components.equations
  (:require
   [utils.utils :refer [inverted-y-axis]]
   [re-frame.core :as re-frame]
   [math-graphs.subs :as subs]
   [math-graphs.events :as events]))

(defn line-equation-control
  []
  (let [{:keys [ang]} @(re-frame/subscribe [::subs/get-equation-data :line])]
    [:div [:input {:value ang
                   :type "number"
                   :on-change (fn [e]
                                (re-frame/dispatch [::events/set-angular (-> e .-target .-value)]))}]]))

(defn line-equation
  [context]
  (let [{:keys [start end ang]} @(re-frame/subscribe [::subs/get-equation-data :line])
        [x-start _] start
        [x-end _] end]

    (.beginPath context)
    (.moveTo context x-start (inverted-y-axis (* x-start ang)))
    (.lineTo context x-end (inverted-y-axis (* x-end ang)))
    (.stroke context)))