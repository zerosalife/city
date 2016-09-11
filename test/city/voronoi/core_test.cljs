(ns city.voronoi.core-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [city.voronoi.core :as core]
            [city.voronoi.point :as point]))

(def p point/Point)

(deftest center-of-triangle-test
  (is (= (p. 10 10)
         (core/center-of-triangle [(p. 30 0) (p. 0 0) (p. 0 30)]))))

(deftest sort-triangles-test
  (is (= [[(p. 0 0) (p. 5 5) (p. 10 0)]
          [(p. 10 0) (p. 5 5) (p. 5 -5)]
          [(p. 5 -5) (p. 5 5) (p. 2 2)]]
         (core/sort-triangles [[(p. 0 0) (p. 5 5) (p. 10 0)]
                               [(p. 5 -5) (p. 5 5) (p. 2 2)]
                               [(p. 10 0) (p. 5 5) (p. 5 -5)]]))))
