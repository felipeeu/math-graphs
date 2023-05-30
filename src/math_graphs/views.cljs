(ns math-graphs.views
  (:require [components.axis :as axis]
            [components.equations :refer [line-equation line-equation-control]]
            [math-graphs.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :refer [create-class]]
            ["react" :as react]))


(defn canvas-inner
  [_ _]
  (let [ref (react/createRef)]
    (create-class
     {:reagent-render       (fn []  [:canvas {:ref   ref
                                              :height "300"
                                              :width "500"
                                              :style {:background-color "gray"}}])
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
                                (line-equation ctx)))})))



(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        data @(re-frame/subscribe [::subs/get-equation-data :line])]
    [:div
     [:h1
      "Hello from " @name]
     (line-equation-control)
     [canvas-inner data]]))
