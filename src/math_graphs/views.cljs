(ns math-graphs.views
  (:require [components.axis :as axis]
            [math-graphs.subs :as subs]
            [re-frame.core :as re-frame]))


(defn canvas-inner
  []
  (let [change-data (fn []
                      (let [cv (.getElementById js/document "canvas")
                            ctx (.getContext cv "2d")]
                        (axis/get-axis ctx :vertical)
                        (axis/get-axis ctx :horizontal)))]

    [:canvas
     {:ref change-data
      :id "canvas"
      :height "300"
      :width "500"
      :style {:background-color "grey"
              :padding "4rem"}}]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "Hello from " @name]
     (canvas-inner)]))
