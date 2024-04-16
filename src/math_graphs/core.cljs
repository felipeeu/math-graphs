(ns math-graphs.core
  (:require
   [reagent.dom.client :as rdom-client]
   [re-frame.core :as re-frame]
   [math-graphs.events :as events]
   [math-graphs.views :as views]
   [math-graphs.config :as config]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defonce root-container
  (rdom-client/create-root (js/document.getElementById "app")))

(defn mount-ui
  []
  (rdom-client/render root-container [views/main-panel]))



(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (re-frame/clear-subscription-cache!)
  (mount-ui))


(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-ui))
