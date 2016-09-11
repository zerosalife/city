(ns city.voronoi.delaunay-triangulation-test
  "Ported with many thanks from [trystan/delaunay-triangulation
  \"1.0.1\"] © 2015 Trystan under Eclipse Public License 1.0."
  (:require [cljs.test :refer-macros [deftest is testing]]
            [city.voronoi.delaunay-triangulation :as delaunay]
            [city.voronoi.point :as point]))

(deftest circumscribe-triangle-test
  (testing "valid triangles"
    (is (= {:x 4 :y 2 :radius-squared 20.0}
           (delaunay/circumscribe-triangle [[0 0] [8 0] [0 4]]))))

  (testing "invalid triangles"
    (is (= nil
           (delaunay/circumscribe-triangle [[0 0] [8 0] [12 0]])))))


(deftest edges-test
  (is (= [[[0 0] [1 0]] [[1 0] [0 1]] [[0 1] [0 0]]]
         (delaunay/edges [[0 0] [1 0] [0 1]]))))


(deftest contains-point?-test
  (testing "contains-point? inside circle"
    (is (= true (delaunay/contains-point? {:x 4 :y 2 :radius-squared 20.0} [3 3]))))

  (testing "contains-point? outside circle"
    (is (= false (delaunay/contains-point? {:x 4 :y 2 :radius-squared 20.0} [30 30]))))

  (testing "contains-point? on circle"
    (is (= false (delaunay/contains-point? {:x 4 :y 2 :radius-squared 20.0} [24 0])))))


(deftest outer-edges-test
  (is (= [[[1 0] [0 -1]] [[0 -1] [-1 -1]] [[-1 -1] [-1 0]] [[-1 0] [1 0]]]
         (delaunay/outer-edges [[[0 0] [1 0] [0 -1]]
                       [[0 0] [0 -1] [-1 -1]]
                       [[0 0] [-1 -1] [-1 0]]
                       [[0 0] [-1 0] [1 0]]]))))


(deftest make-new-triangles-test
  (is (= #{[[-5 5] [-5 -5] [0 1]]
           [[5 5] [5 -5] [0 1]]
           [[-5 -5] [5 -5] [0 1]]
           [[-5 5] [5 5] [0 1]]}
         (delaunay/make-new-triangles [[[-5 -5] [5 -5] [-5 5]] [[5 -5] [-5 5] [5 5]]]
                             [0 1]))))


(deftest add-point-to-triangles-test
  (is (= #{[[-50 -50] [-55 -50] [-50 -55]]
           [[-5 5] [-5 -5] [0 1]]
           [[5 5] [5 -5] [0 1]]
           [[-5 -5] [5 -5] [0 1]]
           [[-5 5] [5 5] [0 1]]}
         (delaunay/add-point-to-triangles #{[[-50 -50] [-55 -50] [-50 -55]] [[-5 -5] [5 -5] [-5 5]] [[5 -5] [-5 5] [5 5]]}
                                 [0 1]))))


(deftest bounds-test
  (is (= [[-1010 1010] [1400 1010] [-1010 -1020] [1400 -1020]]
         (delaunay/bounds [[-10 10] [0 0] [400 -20]]))))


(deftest triangulate-test
  (testing "with valid points"
    (is (= {:points [[-10.0 10.0] [0.0 5.0] [10.0 20.0]]
            :triangles [[[0.0 5.0] [-10.0 10.0] [10.0 20.0]]]
            :edges [[[0.0 5.0] [-10.0 10.0]] [[-10.0 10.0] [10.0 20.0]] [[10.0 20.0] [0.0 5.0]]]}
           (delaunay/triangulate [[-10 10] [0 5] [10 20]]))))

  (testing "with colinier points"
    (is (= {:points [[-10.0 10.0] [0.0 10.0] [10.0 10.0]],
            :triangles [],
            :edges []}
           (delaunay/triangulate [[-10 10] [0 10] [10 10]])))))
