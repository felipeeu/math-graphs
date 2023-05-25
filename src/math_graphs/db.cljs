(ns math-graphs.db)

(def default-db
  {:name "re-frame"
   :equation {:line {:x 1
                     :y 2
                     :ang 4
                     :lin 6}}
   :axis-values {:horizontal {:x1 0
                              :x2 300
                              :y1 300
                              :y2 300}
                 :vertical    {:x1 0
                               :x2 0
                               :y1 0
                               :y2 300}}})
