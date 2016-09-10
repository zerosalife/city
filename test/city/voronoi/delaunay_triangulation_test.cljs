(ns city.voronoi.delaunay-triangulation-test
  "Ported with many thanks from [trystan/delaunay-triangulation
  \"1.0.1\"] © 2015 Trystan under Eclipse Public License 1.0."
  (:require [cljs.test :refer-macros [deftest is testing]]
            [city.voronoi.delaunay-triangulation :as delaunay]
            [city.voronoi.point :as point]))

(def p point/Point)
(def c delaunay/Circle)

(deftest circumscribe-triangle-test
  (let [p1 (p. 0 0)
        p2 (p. 8 0)
        p3 (p. 0 4)
        p4 (p. 12 0)]
    (testing "returns x y and radius squared for valid triangles"
      (is (= (delaunay/map->Circle {:x 4 :y 2 :radius-squared 20.0})
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
  (testing "contains-point? inside circle is true"
    (is (= true
           (delaunay/contains-point? (delaunay/map->Circle {:x 4 :y 2 :radius-squared 20.0})
                                     (p. 3 3)))))
  (testing "contains-point? outside circle is false"
    (is (= false
           (delaunay/contains-point? (c. 4 2 20.0)
                                     (p. 30 30)))))
    (testing "contains-point? on circle is false"
    (is (= false
           (delaunay/contains-point? (c. 4 2 20.0)
                                     (p. 24 0))))))

(deftest outer-edges-test
  (let [triangles [[(p. 0 0) (p. 1 0) (p. 0 -1)]
                   [(p. 0 0) (p. 0 -1) (p. -1 -1)]
                   [(p. 0 0) (p. -1 -1) (p. -1 0)]
                   [(p. 0 0) (p. -1 0) (p. 1 0)]]]
    (is (= [[(p. 1 0) (p. 0 -1)] [(p. 0 -1) (p. -1 -1)] [(p. -1 -1) (p. -1 0)] [(p. -1 0) (p. 1 0)]]
           (delaunay/outer-edges triangles)))))

(deftest make-new-triangles-test
  (is (= #{[(p. -5 5) (p. -5 -5) (p. 0 1)]
           [(p. 5 5) (p. 5 -5) (p. 0 1)]
           [(p. -5 -5) (p. 5 -5) (p. 0 1)]
           [(p. -5 5) (p. 5 5) (p. 0 1)]}
         (delaunay/make-new-triangles [[(p. -5 -5) (p. 5 -5) (p. -5 5)] [(p. 5 -5) (p. -5 5) (p. 5 5)]]
                                      (p. 0 1)))))

(deftest add-point-to-triangles-test
  (is (= #{[(p. -50 -50) (p. -55 -50) (p. -50 -55)]
           [(p. -5 5) (p. -5 -5) (p. 0 1)]
           [(p. 5 5) (p. 5 -5) (p. 0 1)]
           [(p. -5 -5) (p. 5 -5) (p. 0 1)]
           [(p. -5 5) (p. 5 5) (p. 0 1)]}
         (delaunay/add-point-to-triangles #{[(p. -50 -50) (p. -55 -50) (p. -50 -55)]
                                            [(p. -5 -5) (p. 5 -5) (p. -5 5)]
                                            [(p. 5 -5) (p. -5 5) (p. 5 5)]}
                                          (p. 0 1)))))
