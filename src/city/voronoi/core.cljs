(ns city.voronoi.core
  (:require [city.voronoi.point :as point]))

(defn center-of-triangle [[{x1 :x y1 :y} {x2 :x y2 :y} {x3 :x y3 :y}]]
  (point/Point. (/ (+ x1 x2 x3) 3)
                (/ (+ y1 y2 y3) 3)))

(defn sort-triangles
  ([triangles] (sort-triangles [] triangles))
  ([sorted triangles]
   (cond
     (empty? triangles) sorted
     (empty? sorted) (sort-triangles [(first triangles)] (rest triangles))
     :else (let [previous-corners (set (last sorted))
                 adjacent? (fn [triangle] (= (count (clojure.set/intersection previous-corners (set triangle))) 2))
                 next-triangle (first (filter adjacent? triangles))]
             (if next-triangle
               (sort-triangles (conj sorted next-triangle) (remove #{next-triangle} triangles))
               sorted)))))
