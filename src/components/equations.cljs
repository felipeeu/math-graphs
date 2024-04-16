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
                   :on-change (fn [evt]
                                (re-frame/dispatch [::events/set-angular (-> evt .-target .-value)]))}]]))

(defn line-equation
  [context]
  (let [{:keys [start end ang]} @(re-frame/subscribe [::subs/get-equation-data :line])
        [x-start _] start
        [x-end _] end]

    (.beginPath context)
    (.moveTo context x-start (inverted-y-axis (* x-start ang)))
    (.lineTo context x-end (inverted-y-axis (* x-end ang)))
    (.stroke context)))

(defn vertex-onchange
  [point-1 point-2]
  ((re-frame/dispatch [::events/set-parabola-vertex [point-1 point-2]])))

(defn vertex-control
  [vertex-point]
  (let [{:keys [vertex]} @(re-frame/subscribe [::subs/get-equation-data :parabola])
        [vert-x vert-y] vertex
        input-att-x {:type "number"
                     :value vert-x
                     :on-change (fn [evt]
                                  (vertex-onchange (js/parseInt (-> evt .-target .-value)) vert-y))}

        input-att-y {:type "number"
                     :value vert-y
                     :on-change (fn [evt]
                                  (vertex-onchange vert-x (js/parseInt (-> evt .-target .-value))))}
        current-input-att (if (= vertex-point (str "x")) input-att-x  input-att-y)]
    [:div
     [:label {:for (str "vert-" vertex-point)} (str "vert-" vertex-point)]
     [:input current-input-att]]))


(defn parabolic-equation-control
  []
  (let [{:keys [concavity distance]} @(re-frame/subscribe [::subs/get-equation-data :parabola])]
    [:div
     [:label {:for "distance"} "distance"]
     [:input {:value distance
              :type "number"
              :on-change (fn [evt]
                           (re-frame/dispatch [::events/set-parabola-distance (-> evt .-target .-value)]))}]

     [:label {:for "concavity"} "concavity"]
     [:select {:name "concavity" :value concavity :on-change (fn [evt] (re-frame/dispatch [::events/set-parabola-concavity (-> evt .-target .-value)]))}
      '([:option {:value -1 :key "down"} "Down"]
        [:option {:value 1 :key "up"} "Up"])]]))


(defn parabolic-equation
  [context]
  (let [{:keys [concavity distance vertex]} @(re-frame/subscribe [::subs/get-equation-data :parabola])
        [vert-x vert-y] vertex
        distance-db distance
        pinned-axis-value 0
        distance (* concavity distance-db)
        start-x  (+ distance vert-x)  ; <--------- (+ (* -1 distance) vert-x)
        start-y (inverted-y-axis (+ distance vert-y))
        control-point-x (+ (* -1 distance) vert-x) ; <--------- (+ pinned-axis-value vert-x)
        control-point-y (inverted-y-axis (+ pinned-axis-value vert-y));<---------(inverted-y-axis (+ (* -1 distance) vert-y))
        end-x (+ distance vert-x)
        end-y (inverted-y-axis (+ (* -1 distance)  vert-y)) ; <---------(inverted-y-axis (+  distance  vert-y))
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


(def equation-map {:line line-equation
                   :parabola parabolic-equation
                   :circle circle-equation
                   :ellipse ellipse-equation})



(defn shape-to-equation
  [context]
  (let [shape @(re-frame/subscribe [::subs/shape])
        equation-func (get equation-map (keyword shape))]

    (equation-func context)))