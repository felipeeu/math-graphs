(ns components.chart
  (:require
   [utils.utils :refer [inverted-y-axis]]
   [re-frame.core :as re-frame]
   [math-graphs.subs :as subs]))

(defn get-line-chart
  [context]
  (let [{:keys [start end ang]} @(re-frame/subscribe [::subs/get-equation-data :line])
        [x-start _] start
        [x-end _] end]

    (.beginPath context)
    (.moveTo context x-start (inverted-y-axis (* x-start ang)))
    (.lineTo context x-end (inverted-y-axis (* x-end ang)))
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



(defn circle-equation
  [context]
  (.beginPath context)
  (.arc context 100 75 50 0 (* 2 Math/PI))
  (.stroke context))

(defn ellipse-equation
  [context]
  (.beginPath context)
  (.ellipse context 100 100 50 75 (/ Math/PI 4)  0 (* 2 Math/PI))
  (.stroke context))



(def chart-map {:line get-line-chart
                :parabola get-parabola-chart
                :circle circle-equation
                :ellipse ellipse-equation})



(defn shape-to-chart
  [context]
  (let [shape @(re-frame/subscribe [::subs/shape])
        equation-func (get chart-map (keyword shape))]

    (equation-func context)))