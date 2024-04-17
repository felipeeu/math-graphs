(ns math-graphs.db)

(def default-db
  {:name "re-frame cljs"
   :shape "line"
   :canvas-color "red"
   :equation {:line {:start [0 0]
                     :end [60 60]
                     :ang 0}

              :parabola {:vertex [0 0]
                         :distance 100
                         :concavity 1}}
   :axis-values {:horizontal {:start [-400 0]
                              :end [400 0]}
                 :vertical    {:start [0 -300]
                               :end [0 300]}}})
