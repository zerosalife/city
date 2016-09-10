(ns city.voronoi.delaunay-triangulation
  "Ported with many thanks from [trystan/delaunay-triangulation
  \"1.0.1\"] © 2015 Trystan under Eclipse Public License 1.0."
  (:require [clojure.set]))

(def very-small-float 0.000001)

(defn circumscribe-triangle [[{ax :x ay :y} {bx :x by :y} {cx :x cy :y}]]
  (let [A (- bx ax)
        B (- by ay)
        C (- cx ax)
        D (- cy ay)
        E (+ (* A (+ ax bx)) (* B (+ ay by)))
        F (+ (* C (+ ax cx)) (* D (+ ay cy)))
        G (* 2 (- (* A (- cy by)) (* B (- cx bx))))]
    (when (> (Math/abs G) very-small-float)
      (let [cx (/ (- (* D E) (* B F)) G)
            cy (/ (- (* A F) (* C E)) G)
            dx (- cx ax)
            dy (- cy ay)
            r (+ (Math/pow dx 2) (Math/pow dy 2))]
        {:x cx :y cy :radius-squared r}))))

(defn edges [[p1 p2 p3]] [[p1 p2] [p2 p3] [p3 p1]])
