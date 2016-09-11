(ns city.voronoi.core
  (:require [city.voronoi.point :as point]))

(defn center-of-triangle [[{x1 :x y1 :y} {x2 :x y2 :y} {x3 :x y3 :y}]]
  (point/Point. (/ (+ x1 x2 x3) 3)
                (/ (+ y1 y2 y3) 3)))
