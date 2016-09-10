(ns city.voronoi.delaunay-triangulation-test
  "Ported with many thanks from [trystan/delaunay-triangulation
  \"1.0.1\"] © 2015 Trystan under Eclipse Public License 1.0."
  (:require [cljs.test :refer-macros [deftest is testing]]
            [city.voronoi.delaunay-triangulation :as delaunay]
            [city.voronoi.point :as point]))

(deftest circumscribe-triangle-test
  (let [point-list [(point/Point. 0 0)
                    (point/Point. 8 0)
                    (point/Point. 0 4)]])
  (testing "returns x y and radius squared for valid triangles"
    (is (= {:x 4 :y 2 :radius-squared 20.0}
           (delaunay/circumscribe-triangle [[0 0] [8 0] [0 4]])))))
