(ns build
  (:require [shadow.cljs.build :as cljs]
            [clojure.java.io :as io]))

(defn dev [& args]
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
      (cljs/step-configure-module :demo ['demo.app] #{})
      (cljs/step-finalize-config)

      (cljs/watch-and-repeat!
       (fn [state modified]
         (-> state
             (cljs/step-compile-modules)
             (cljs/flush-unoptimized))))))

(defn production [& args]
  (-> (cljs/init-state)
      (cljs/enable-emit-constants)
      (assoc :optimizations :advanced
             :pretty-print false
             :work-dir (io/file "target/cljs-prod-work")
             :cache-dir (io/file "target/cljs-prod-cache")
             :cache-level :jars
             :public-dir (io/file "demo/js")
             :public-path "demo/js")
      (cljs/step-find-resources-in-jars)
      (cljs/step-find-resources "src/cljs")
      (cljs/step-find-resources "test/cljs")
      (cljs/step-configure-module :demo ['demo.app] #{})
      (cljs/step-finalize-config)
      (cljs/step-compile-modules)
      (cljs/closure-optimize)
      (cljs/flush-modules-to-disk)
      ))

(defn autotest
  [& args]
  (-> (cljs/init-state)
      (cljs/enable-source-maps)
      (assoc :optimizations :none
             :pretty-print true
             :work-dir (io/file "target/cljs-test-work")
             :cache-dir (io/file "target/cljs-test-cache")
             :cache-level :jars
             :public-dir (io/file "target/cljs-test")
             :public-path "cljs-test")
      (cljs/step-find-resources-in-jars)
      (cljs/step-find-resources "src/cljs")
      (cljs/step-find-resources "test/cljs")
      (cljs/step-finalize-config)

      (cljs/watch-and-repeat!
        (fn [state modified]
          (-> state
              (cond->
                ;; first pass, run all tests
                (empty? modified)
                (cljs/execute-all-tests!)
                ;; only execute tests that might have been affected by the modified files
                (not (empty? modified))
                (cljs/execute-affected-tests! modified))
              ))) ))
