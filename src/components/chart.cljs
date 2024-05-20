(ns components.chart
  (:require
   [utils.utils :refer [inverted-y-axis]]
   [re-frame.core :as re-frame]
   [math-graphs.subs :as subs]))

(defn get-line-chart
  [context]
  (let [{:keys [start end ang]} @(re-frame/subscribe [::subs/get-equation-data :line])
        [x-start y-start] start
        [x-end y-end] end
        c 60]

    (.beginPath context)
    (.moveTo context (+ x-start c) (inverted-y-axis (+ (* y-start ang) c)))
    (.lineTo context (+ x-end c)  (inverted-y-axis (+ (* y-end ang) c)))
    (.stroke context)))

(defn get-parabola-chart
  "get dynamic parabola chart to plot"
  [context]
  (let [{:keys [concavity distance vertex]} @(re-frame/subscribe [::subs/get-equation-data :parabola])
        [vert-x vert-y] vertex
        distance-db distance
        pinned-axis-value 0
        distance (* concavity distance-db)
        start-x  (+ (* -1 distance) vert-x);<---------(+ distance vert-x)  
        start-y (inverted-y-axis (+ distance vert-y))
        control-point-x (+ pinned-axis-value vert-x)  ; <--------- (+ (* -1 distance) vert-x)
        control-point-y (inverted-y-axis (+ (* -1 distance) vert-y))   ;<--------- (inverted-y-axis (+ pinned-axis-value vert-y))
        end-x (+ distance vert-x)
        end-y (inverted-y-axis (+  distance  vert-y))    ; <--------- (inverted-y-axis (+ (* -1 distance)  vert-y))
        ]
    (.beginPath context)
    (.moveTo context  start-x start-y)
    (.quadraticCurveTo context control-point-x  control-point-y end-x end-y)
    (.stroke context)))


(defn get-circle-chart
  "get dynamic circle chart to plot"
  [context]
  (let [{:keys [center radius]} @(re-frame/subscribe [::subs/get-equation-data :circle])
        [center-x center-y] center
        final-angle  (* 2 Math/PI)
        initial-angle 0]
    (.beginPath context)
    (.arc context center-x center-y radius initial-angle final-angle)
    (.stroke context)))


(defn get-ellipse-chart
  [context]
  (.beginPath context)
  (.ellipse context 100 100 50 75 (/ Math/PI 4)  0 (* 2 Math/PI))
  (.stroke context))


(def chart-map {:line get-line-chart
                :parabola get-parabola-chart
                :circle get-circle-chart
                :ellipse get-ellipse-chart})


(defn shape-to-chart
  [context]
  (let [shape @(re-frame/subscribe [::subs/shape])
        equation-func (get chart-map (keyword shape))]

    (equation-func context)))