(ns build
  (:require [shadow.cljs.build :as cljs]
            [clojure.java.io :as io]))

(defn dev
  [& args]
  (-> (cljs/init-state)
      (cljs/enable-source-maps)
      (assoc :optimizations :none
             :pretty-print true
             :work-dir (io/file "target/cljs-work")
             :cache-dir (io/file "target/cljs-cache")
             :cache-level :jars
             :public-dir (io/file "demo/js")
             :public-path "demo/js")
      (cljs/step-find-resources-in-jars)
      (cljs/step-find-resources "src/cljs")
      (cljs/step-find-resources "test/cljs")

      (cljs/step-finalize-config)

      (cljs/step-configure-module :demo ['demo.app] #{})

      (cljs/watch-and-repeat!
        (fn [state modified]
          (-> state
              (cljs/step-compile-modules)
              (cljs/flush-unoptimized)
              (cond->
                ;; first pass, run all tests
                (empty? modified)
                (cljs/execute-all-tests!)
                ;; only execute tests that might have been affected by the modified files
                (not (empty? modified))
                (cljs/execute-affected-tests! modified))
              ))) ))
