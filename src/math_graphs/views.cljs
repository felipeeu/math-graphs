(ns math-graphs.views
  (:require [components.axis :as axis]
            [components.equations :refer [display-parabola-equation display-circle-equation display-line-equation get-generic-equation]]
            [components.chart :refer [shape-to-chart]]
            [components.shapes :refer [shape-selector]]
            [math-graphs.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :refer [create-class]]
            ["react" :as react]))


(defn canvas-inner
  [_ _]
  (let [ref (react/createRef)]
    (create-class
     {:reagent-render       (fn []  [:canvas {:ref   ref
                                              :height "1000"
                                              :width "1000"}])
      :component-did-mount  (fn []
                              (let [cv  (.-current ref)
                                    ctx (.getContext cv "2d")
                                    width (.-width cv)
                                    height (.-height cv)]
                                (axis/init-graph ctx width height)))
      :component-did-update (fn []
                              (let [cv  (.-current ref)
                                    ctx (.getContext cv "2d")
                                    width (.-width cv)
                                    height (.-height cv)]
                                (.reset ctx)
                                (axis/init-graph ctx width height)
                                (shape-to-chart ctx)))})))



(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        shape @(re-frame/subscribe [::subs/shape])
        equation  @(re-frame/subscribe [::subs/equation])]
    [:div
     [:h1
      "Chart of " shape]
     [:h2 @name]

     [:div {:class "col"}
      [:div {:class "row mx-auto"}
       (shape-selector)
       (get-generic-equation shape)]
      (case shape
        "line" (display-line-equation)
        "parabola" (display-parabola-equation)
        "circle"  (display-circle-equation)
        nil)]

     [canvas-inner equation shape]]))
