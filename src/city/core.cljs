(ns city.core
  (:require cljsjs.d3))

(defrecord Point [x y])

(defn random-points [n width height]
  (map
   #(Point. (rand-int width) (rand-int height))
   (range n)))

(defn drawline [viewport [p1 p2]]
  (.log js/console p1)
  (.. viewport
      (append "line")
      (attr "x1" (:x p1))
      (attr "y1" (:y p1))
      (attr "x2" (:x p2))
      (attr "y2" (:y p2))
      (attr "stroke-width" 2)
      (attr "stroke" "black")))

(let [viewport (.selectAll js/d3 "#viewport")
      width (.attr viewport "width")
      height (.attr viewport "height")
      points (random-points 10 width height)]
  (doall
      (map
       #(drawline viewport %)
       (partition 2 points))))
