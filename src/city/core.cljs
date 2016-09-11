(ns city.core
  (:require [cljsjs.d3]
            [city.voronoi.point :as point]
            [city.voronoi.core :as voronoi]
            [city.voronoi.delaunay-triangulation :as delaunay]))

(defn random-points [n width height]
  (map
   #(point/Point. (rand-int width) (rand-int height))
   (range n)))

(defn draw-line [viewport [p1 p2] color]
  (.. viewport
      (append "line")
      (attr "x1" (:x p1))
      (attr "y1" (:y p1))
      (attr "x2" (:x p2))
      (attr "y2" (:y p2))
      (attr "stroke-width" 1)
      (attr "stroke" color)))

(defn draw-point [viewport p]
  (.. viewport
      (append "circle")
      (attr "cx" (:x p))
      (attr "cy" (:y p))
      (attr "r" 2)
      (attr "fill" "black")))

(let [viewport (.selectAll js/d3 "#viewport")
      width (.attr viewport "width")
      height (.attr viewport "height")
      points (random-points 25 width height)
      diagram (voronoi/diagram points)
      tri (delaunay/triangulate points)
      points1 [(point/Point. 2.0 2.0)
               (point/Point. 1.0 4.0)
               (point/Point. 4.0 1.0)
               (point/Point. -10.0 -10.0)
               (point/Point. -10.0 10.0)
               (point/Point. 10.0 10.0)
               (point/Point. 10.0 -10.0)]
      edges1 [[(point/Point. -2.3333333333333335 -1.3333333333333333)
               (point/Point. 2.3333333333333335 2.3333333333333335)]
              [(point/Point. -2.3333333333333335 -1.3333333333333333)
               (point/Point. -1.3333333333333333 -2.3333333333333335)]
              [(point/Point. -1.3333333333333333 -2.3333333333333335)
               (point/Point. 2.3333333333333335 2.3333333333333335)]
              [(point/Point. 2.3333333333333335 2.3333333333333335)
               (point/Point. 5.0 5.0)]
              [(point/Point. -6.333333333333333 1.3333333333333333)
               (point/Point. -2.3333333333333335 -1.3333333333333333)]
              [(point/Point. -6.333333333333333 1.3333333333333333)
               (point/Point. 0.3333333333333333 8.0)]
              [(point/Point. 0.3333333333333333 8.0)
               (point/Point. 5.0 5.0)]
              [(point/Point. -1.3333333333333333 -2.3333333333333335)
               (point/Point. 1.3333333333333333 -6.333333333333333)]
              [(point/Point. 1.3333333333333333 -6.333333333333333)
               (point/Point. 8.0 0.3333333333333333)]
              [(point/Point. 5.0 5.0) (point/Point. 8.0 0.3333333333333333)]]
      edges2 [[(point/map->Point {:x -1.3333333333333333  :y -2.3333333333333335})
               (point/map->Point {:x 2.3333333333333335  :y 2.3333333333333335})]
              [(point/map->Point {:x -2.3333333333333335  :y -1.3333333333333333})
               (point/map->Point {:x 2.3333333333333335  :y 2.3333333333333335})]
              [(point/map->Point {:x -2.3333333333333335  :y -1.3333333333333333})
               (point/map->Point {:x -1.3333333333333333  :y -2.3333333333333335})]
              [(point/map->Point {:x -6.333333333333333  :y 1.3333333333333333})
               (point/map->Point {:x 0.3333333333333333  :y 8})]
              [(point/map->Point {:x 0.3333333333333333  :y 8}) (point/map->Point {:x 5  :y 5})]
              [(point/map->Point {:x 2.3333333333333335  :y 2.3333333333333335}) (point/map->Point {:x 5  :y 5})]
              [(point/map->Point {:x -6.333333333333333  :y 1.3333333333333333})
               (point/map->Point {:x -2.3333333333333335  :y -1.3333333333333333})]
              [(point/map->Point {:x 5  :y 5}) (point/map->Point {:x 8  :y 0.3333333333333333})]
              [(point/map->Point {:x 1.3333333333333333  :y -6.333333333333333})
               (point/map->Point {:x 8  :y 0.3333333333333333})]
              [(point/map->Point {:x -1.3333333333333333  :y -2.3333333333333335})
               (point/map->Point {:x 1.3333333333333333  :y -6.333333333333333})]
              [(point/map->Point {:x -6.333333333333333  :y 1.3333333333333333})
               (point/map->Point {:x 1.3333333333333333  :y -6.333333333333333})]
              [(point/map->Point {:x 0.3333333333333333  :y 8}) (point/map->Point {:x 8  :y 0.3333333333333333})]]]

  (doall (map
          #(draw-line viewport % "black")
          (:edges tri)))
  (doall (map
          #(draw-point viewport %)
          points))
  (doall (map
          #(draw-line viewport % "blue")
          (:edges diagram))))
