(ns city.core
  (:require [cljsjs.d3]
            [city.voronoi.point :as point]
            [city.voronoi.core :as voronoi]
            [city.voronoi.delaunay-triangulation :as delaunay]))

(defn random-points [n width height]
  (map
   (fn [] [(rand-int width) (rand-int height)])
   (range n)))

(defn draw-line [viewport [p1 p2] color]
  (.. viewport
      (append "line")
      (attr "x1" (first p1))
      (attr "y1" (second p1))
      (attr "x2" (first p2))
      (attr "y2" (second p2))
      (attr "stroke-width" 1)
      (attr "stroke" color)))

(defn draw-point [viewport p]
  (.. viewport
      (append "circle")
      (attr "cx" (first p))
      (attr "cy" (second p))
      (attr "r" 2)
      (attr "fill" "black")))

(let [viewport (.selectAll js/d3 "#viewport")
      width (.attr viewport "width")
      height (.attr viewport "height")
      points (random-points 25 width height)
      diagram (voronoi/diagram points)
      tri (delaunay/triangulate points)]

  (doall (map
          #(draw-line viewport % "black")
          (:edges tri)))
  (doall (map
          #(draw-point viewport %)
          points))
  (doall (map
          #(draw-line viewport % "blue")
          (:edges diagram))))
