(ns math-graphs.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::get-axes
 (fn [db [_ type]]
   (get-in db [:axis-values type])))

(re-frame/reg-sub
 ::get-equation-data
 (fn [db [_ equation-type]]
   (get-in db [:equation equation-type])))


(re-frame/reg-sub
 ::shape
 (fn [db]
   (:shape db)))


(re-frame/reg-sub
 ::equation
 (fn [db]
   (:equation db)))