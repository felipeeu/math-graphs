(ns components.shapes 
  (:require [clojure.string :as str]
            [math-graphs.events :as events]
            [math-graphs.subs :as subs]
            [re-frame.core :refer [dispatch subscribe]]))

(def shape-list '("line", "parabola", "circle", "ellipse"))

(defn shape-options
  [listed-shape]
  (let [value (clojure.string/lower-case listed-shape)]
    [:option {:key listed-shape
              :value value} listed-shape]))

(defn shape-selector
  []
  (let [selected-shape @(subscribe [::subs/shape])]
    [:div
     [:label {:for "shape"} " shape:"]
     [:select {:name "shape" :value selected-shape :on-change (fn [evt]
                                                                (dispatch [::events/change-shape (-> evt .-target .-value)]))}
      (map  #(shape-options %) shape-list)]]))