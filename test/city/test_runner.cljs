(ns city.test-runner
  (:require [doo.runner :as doo :include-macros true]
            [city.fortune.bst-test]))

(enable-console-print!)

(doo/doo-tests 'city.fortune.bst-test)
