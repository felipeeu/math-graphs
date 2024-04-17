(ns components.equations
  (:require
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


(defn vertex-onchange
  "change according the point parameters"
  [point-1 point-2]
  ((re-frame/dispatch [::events/set-parabola-vertex [point-1 point-2]])))

(defn vertex-control
  "set x and y vertex points"
  [vertex-point]
  (let [{:keys [vertex]} @(re-frame/subscribe [::subs/get-equation-data :parabola])
        [vert-x vert-y] vertex
        input-att-x {:value vert-x
                     :on-change (fn [evt]
                                  (vertex-onchange (js/parseInt (-> evt .-target .-value)) vert-y))}

        input-att-y {:value vert-y
                     :on-change (fn [evt]
                                  (vertex-onchange vert-x (js/parseInt (-> evt .-target .-value))))}
        current-input-att (if (= vertex-point (str "x")) input-att-x  input-att-y)]

    [:div [:input (assoc current-input-att :type "number")]]))


(defn symmetry-axis-distance-control
  "set the distance to symmetry axis"
  [distance]
  [:div [:input {:value distance
                 :type "number"
                 :min 1
                 :on-change (fn [evt]
                              (re-frame/dispatch [::events/set-parabola-distance (-> evt .-target .-value)]))}]])

(defn concavity-selector
  "set the concavity of parabola "
  [concavity]
  [:div
   [:select {:style {:margin "unset"} :name "concavity" :value concavity :on-change (fn [evt] (re-frame/dispatch [::events/set-parabola-concavity (-> evt .-target .-value)]))}
    '([:option {:value -1 :key "down"} "-"]
      [:option {:value 1 :key "up"} "+"])]])


(defn display-parabola-equation
  "dynamic equation to display on UI"
  []
  (let  [{:keys [concavity distance]} @(re-frame/subscribe [::subs/get-equation-data :parabola])
         text-style {:padding "0 10px 0 10px" :font-weight "900"}]
    [:div {:class "row mx-auto"}
     [:div {:style text-style}  "y ="]
     (concavity-selector concavity)
     (symmetry-axis-distance-control distance)
     [:div {:style text-style}    "( x -"]
     (vertex-control "x")
     [:div {:style text-style} (str ")") [:sup 2] (str " +")]
     (vertex-control "y")]))




