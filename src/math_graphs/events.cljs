(ns math-graphs.events
  (:require
   [re-frame.core :as re-frame]
   [math-graphs.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-angular
 (fn [db [_ angular]]
   (assoc-in db [:equation :line :ang] angular)))



