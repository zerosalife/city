(ns city.voronoi.delaunay-triangulation-test
  "Ported with many thanks from [trystan/delaunay-triangulation
  \"1.0.1\"] © 2015 Trystan under Eclipse Public License 1.0."
  (:require [cljs.test :refer-macros [deftest is testing]]
            [city.voronoi.delaunay-triangulation :as delaunay]
            [city.voronoi.point :as point]))

(def p point/Point)

(deftest circumscribe-triangle-test
  (let [p1 (p. 0 0)
        p2 (p. 8 0)
        p3 (p. 0 4)
        p4 (p. 12 0)]
    (testing "returns x y and radius squared for valid triangles"
      (is (= {:x 4 :y 2 :radius-squared 20.0}
             (delaunay/circumscribe-triangle [p1 p2 p3]))))
    (testing "returns nil for invalid triangles"
      (is (= nil
             (delaunay/circumscribe-triangle [p1 p2 p4]))))))

(deftest edges-test
  (let [p1 (p. 0 0)
        p2 (p. 1 0)
        p3 (p. 0 1)]
    (is (= [[p1 p2] [p2 p3] [p3 p1]]
           (delaunay/edges [p1 p2 p3])))))


(deftest contains-point-test
  (testing "contains-point? inside circle"
    (is (= true
           (delaunay/contains-point? {:x 4 :y 2 :radius-squared 20.0}
                                     (p. 3 3))))))
