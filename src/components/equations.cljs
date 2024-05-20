(ns components.equations
  (:require
   [re-frame.core :as re-frame]
   [math-graphs.subs :as subs]
   [math-graphs.events :as events]))


(defn contains-item? [coll item]
  (boolean (first (filter #(= % item) coll))))



(defn variable-control
  "set control of a variable by shape"
  [value variable shape & args]
  [:input {:value  value
           :style {:max-width "4.5rem"}
           :type "number"
           :min  (if (contains-item? args "allow-negative") "unset" 1)
           :on-change (fn [evt]
                        (re-frame/dispatch [::events/set-variable-by-shape (-> evt .-target .-value) shape variable]))}])

(defn point-onchange
  "change according the point parameters"
  [shape variable point-1 point-2]
  ((re-frame/dispatch [::events/set-point shape variable [point-1 point-2]])))


(defn point-control
  "set x and y points"
  [{:keys [point shape variable]}]
  (let [equation-data @(re-frame/subscribe [::subs/get-equation-data (keyword shape)])

        variable-value (get equation-data  (keyword variable))

        [point-x point-y] variable-value

        input-att-x {:value point-x
                     :on-change (fn [evt]
                                  (point-onchange shape variable (js/parseInt  (-> evt .-target .-value)) point-y))}

        input-att-y {:value point-y
                     :on-change (fn [evt]
                                  (point-onchange shape variable point-x (js/parseInt (-> evt .-target .-value))))}

        current-input-att (if (= point (str "x"))
                            input-att-x
                            input-att-y)]

    [:div {:style
           {:max-width "4.5rem"}}
     [:input  (assoc current-input-att :type "number")]]))


(defn display-line-equation
  "dynamic equation to display line on UI"
  []
  (let  [{:keys [ang]} @(re-frame/subscribe [::subs/get-equation-data :line])
         text-style {:padding "0 10px 0 10px" :font-weight "900" :font-size "25px" :min-width "3.5rem"}]
    [:div {:class "row mx-auto"}
     [:div {:style text-style}  "y ="]
     (variable-control ang :ang :line "allow-negative")
     [:div {:style text-style}    "x"]
     [:div {:style text-style}    "+"]
     [:div {:style text-style}  "c"]]))


;-----------Parabola functions ---------

(defn concavity-selector
  "set the concavity of parabola "
  [concavity variable]
  [:div
   [:select {:style {:margin "unset"} :name "concavity" :value concavity :on-change (fn [evt]
                                                                                      (re-frame/dispatch [::events/set-variable-by-shape (-> evt .-target .-value) :parabola variable]))}
    '([:option {:value -1 :key "down"} "-"]
      [:option {:value 1 :key "up"} "+"])]])


(defn display-parabola-equation
  "dynamic equation to display parabola on UI"
  []
  (let  [{:keys [concavity distance]} @(re-frame/subscribe [::subs/get-equation-data :parabola])
         text-style {:padding "0 10px 0 10px" :font-weight "900" :font-size "25px" :min-width "3.5rem"}]
    [:div {:class "row mx-auto"}
     [:div {:style text-style}  "y ="]
     (concavity-selector concavity :concavity)
     (variable-control distance :distance :parabola)
     [:div {:style text-style}    "( x -"]
     (point-control {:point "x"
                     :shape "parabola"
                     :variable "vertex"})
     [:div {:style text-style} (str ")") [:sup 2] (str " +")]
     (point-control {:point "y"
                     :shape "parabola"
                     :variable "vertex"})]))

; ------------ Circle Functions -------------------

(defn display-circle-equation
  "dynamic equation to display circle on UI"
  []
  (let [{:keys [radius]} @(re-frame/subscribe [::subs/get-equation-data :circle])
        text-style {:padding "0 10px 0 10px" :font-weight "900" :font-size "25px" :min-width "3.5rem"}]
    [:div {:class "row mx-auto"}
     [:span {:style text-style} "(x-"]
     (point-control {:point "x"
                     :shape "circle"
                     :variable "center"})
     [:span  {:style text-style} ")" [:sup 2]]
     [:span {:style text-style} "(y-"]
     (point-control {:point "y"
                     :shape "circle"
                     :variable "center"})
     [:span  {:style text-style} ")" [:sup 2]]
     [:span  {:style text-style} "="]
     (variable-control radius :radius :circle)
     [:span  {:style text-style} [:sup 2]]]))

;--------------------------------------------------

(defn get-generic-equation
  [shape]

  (let [{:keys [generic-equation]} @(re-frame/subscribe [::subs/get-equation-data (keyword shape)])]
    [:div {:class "row mx-2"}
     (str "equation: ")
     [:h4  {:style {:display "contents"}} generic-equation]]))