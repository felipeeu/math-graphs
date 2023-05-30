(ns components.axis
  (:require [re-frame.core :as re-frame]
            [math-graphs.subs :as subs]
            [utils.utils :refer [inverted-y-axis]]))



(defn add-scale
  "add scales to graph"
  [context {:keys [start end]} type]
  (let [scale 10]
    (case type
      :vertical (doall (map #(.fillText context (inverted-y-axis  %)  (first start)  %) (range  (second start)  (second end) (quot (second end) scale))))
      :horizontal (doall (map #(.fillText context  %  %  (+ (second end) 10))  (range  (first start) (first end) (quot (first end) scale)))))))

(defn get-axis
  "create de axis for the equation graphs"
  [context type]

  (let [axis-values @(re-frame/subscribe [::subs/get-axes type]) ;called outside of a reactive context yet.
        [x-start y-start] (:start axis-values)
        [x-end y-end] (:end axis-values)]
    (.beginPath context)
    (.moveTo context x-start y-start)
    (.lineTo context x-end y-end)
    (.stroke context)
    (add-scale context axis-values type)))

(defn init-graph
  "initialize the graph with axis"
  [context width height]
  (.translate context (/ width 2) (/ height 2))
  (get-axis context :vertical)
  (get-axis context :horizontal))


