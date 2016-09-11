(ns city.voronoi.core
  (:require [city.voronoi.point :as point]
            [city.voronoi.delaunay-triangulation :as delaunay]))

(enable-console-print!)

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

(defn polygon-for-point [triangles point]
  (let [neighboring-triangles (filter (fn [[a b c]] (or (= point a) (= point b) (= point c))) triangles)]
    (map center-of-triangle (sort-triangles neighboring-triangles))))

(defn loopify [polygon]
  (println "loopifying polygon: " polygon)
  (let [polygon (mapcat (fn [x] [x x]) polygon)
        _ (println "duplicated point polygon " polygon)
        polygon (concat (rest polygon) [(first polygon)])
        _ (println "concat rested: " polygon)]
    polygon))

(defn diagram [points]
  (let [points (map
                (fn [p] [(float (:x p)) (float (:y p))]) points)
        _ (println points)
        cells (map
               (partial polygon-for-point (:triangles (delaunay/triangulate points))) points)]
    {:points points
     :cells cells
     :edges (->> cells
                 (map loopify)
                 (mapcat (partial partition 2))
                 (map (partial sort-by :x))
                 distinct)}))
