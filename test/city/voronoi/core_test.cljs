(ns city.voronoi.core-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [city.voronoi.core :as core]))

(deftest center-of-triangle-test
  (is (= [10 10]
         (core/center-of-triangle [[30 0] [0 0] [0 30]]))))
