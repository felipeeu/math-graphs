(ns components.axis
  (:require [re-frame.core :as re-frame]
            [math-graphs.subs :as subs]))

(defn add-scale
  [context {:keys [x1 x2 y2]} type]
  (let [scale 10]
    (case type
      :vertical (doall (map #(.fillText context %  x1  %) (range  0  y2 (quot y2 scale))))
      :horizontal (doall (map #(.fillText context %  % y2) (range  0  x2 (quot x2 scale)))))))

(defn get-axis
  "create de axis for the equation graphs"
  [context type]

  (let [axis-values @(re-frame/subscribe [::subs/get-axes type]) ;called outside of a reactive context yet.
        x1 (:x1 axis-values)
        y1 (:y1 axis-values)
        x2 (:x2 axis-values)
        y2 (:y2 axis-values)]
    (.beginPath context)
    (.moveTo context x1 y1)
    (.lineTo context x2 y2)
    (.stroke context)
    (add-scale context axis-values type)))




