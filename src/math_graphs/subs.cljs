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