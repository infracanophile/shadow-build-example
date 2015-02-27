(ns demo.app-test
  (:require [cljs.test :as ct :refer (deftest is)]
            [demo.app :as app]))

(deftest the-test
  (is (= 1 2)))
