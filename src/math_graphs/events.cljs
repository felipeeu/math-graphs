(ns math-graphs.events
  (:require
   [re-frame.core :as re-frame]
   [math-graphs.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(re-frame/reg-event-db
 ::set-variable-by-shape
 (fn [db [_ value shape variable]]

   (assoc-in db [:equation (keyword shape) (keyword variable)]  (js/parseInt value))))


(re-frame/reg-event-db
 ::set-point
 (fn [db [_ shape variable value]]
   (assoc-in db [:equation
                 (keyword shape)
                 (keyword variable)]  value)))



(re-frame/reg-event-db
 ::change-shape
 (fn [db [_ shape]]
   (assoc db :shape shape)))