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

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [container (.getElementById js/document "app")
        root (rdom-client/create-root container)]
    (rdom-client/render root [views/main-panel])))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
