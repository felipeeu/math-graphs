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
   (assoc-in db [:equation :line :ang] (js/parseInt angular))))

(re-frame/reg-event-db
 ::set-parabola-concavity
 (fn [db [_ concavity]]
   (assoc-in db [:equation :parabola :concavity] (js/parseInt concavity))))

(re-frame/reg-event-db
 ::set-parabola-distance
 (fn [db [_ distance]]
   (assoc-in db [:equation :parabola :distance]  distance)))

(re-frame/reg-event-db
 ::set-parabola-vertex
 (fn [db [_ vertex]]
   (assoc-in db [:equation :parabola :vertex]  vertex)))


(re-frame/reg-event-db
 ::change-shape
 (fn [db [_ shape]]
   (assoc db :shape shape)))