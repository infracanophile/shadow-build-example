(ns demo.app-test
  (:require [cljs.test :as ct :refer (deftest is)]
            [demo.app :as app]
            [demo.extras]
            ))

(deftest the-test
  (is (= 1 2)))

(deftest extras-test
  (is (= '(:a :b) (demo.extras/make-tuple :a :b)))
  )

(deftest clojure-file-extras-test
  (is (= '(:a :b) (demo.extras/clojure-file-make-tuple :a :b)))
  )
