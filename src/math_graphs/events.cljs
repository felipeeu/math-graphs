(ns math-graphs.events
  (:require
   [re-frame.core :as re-frame]
   [math-graphs.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

