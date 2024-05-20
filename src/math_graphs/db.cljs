(ns math-graphs.db)

(def default-db
  {:name "Charts by Felipe Domingues"
   :shape "line"
   :canvas-color "red"
   :equation {:line {:start [-300 -300]
                     :end [300 300]
                     :ang 0
                     :generic-equation "y=mx+c"}

              :parabola {:vertex [0 0]
                         :distance 100
                         :concavity 1
                         :generic-equation  "y=a(x-h)²+k"}
              :circle {:center [0 0]
                       :radius 20
                       :generic-equation "(x - h)² + (y - k)² = r²"}}
   :axis-values {:horizontal {:start [-300 0]
                              :end [300 0]}
                 :vertical    {:start [0 -300]
                               :end [0 300]}}})
