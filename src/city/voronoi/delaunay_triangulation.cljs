(ns city.voronoi.delaunay-triangulation
  "Ported with many thanks from [trystan/delaunay-triangulation
  \"1.0.1\"] © 2015 Trystan under Eclipse Public License 1.0."
  (:require [clojure.set]
            [city.voronoi.point :as p]))

(def very-small-float 0.000001)

(defrecord Circle [x y radius-squared])

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
        (Circle. cx cy r)))))

(defn edges [[p1 p2 p3]] [[p1 p2] [p2 p3] [p3 p1]])

(defn contains-point? [{:keys [x y radius-squared]} {px :x py :y}]
  (let [distance-squared (+ (Math/pow (- x px) 2) (Math/pow (- y py) 2))]
    (< distance-squared radius-squared)))

(defn outer-edges [triangles]
  (let [all-edges (mapcat edges triangles)
        matches (fn [edge] (filter #{edge (reverse edge)} all-edges))
        appears-once (fn [edge] (= (count (matches edge)) 1))]
    (filter appears-once all-edges)))

(defn make-new-triangles [containers point]
  (->> containers
       outer-edges
       (map (fn [[p1 p2]] [p1 p2 point]))
       set))

(defn add-point-to-triangles [triangles point]
  (let [containers (filter #(contains-point? (circumscribe-triangle %) point) triangles)
        new-triangles (make-new-triangles containers point)]
    (clojure.set/union (clojure.set/difference triangles containers) new-triangles)))

(defn bounds [points]
  (let [minx (->> points (map :x) (apply min) (+ -1000))
        maxx (->> points (map :x) (apply max) (+ 1000))
        miny (->> points (map :y) (apply min) (+ -1000))
        maxy (->> points (map :y) (apply max) (+ 1000))]
    [(p/Point. minx maxy) (p/Point. maxx maxy) (p/Point. minx miny) (p/Point. maxx miny)]))
