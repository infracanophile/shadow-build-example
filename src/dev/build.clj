(ns build
  (:require [shadow.cljs.build :as cljs]
            [shadow.cljs.node :as node]
            [shadow.devtools.server :as devtools]
            [clojure.java.io :as io]))

(defn project-setup [opts]
  (-> (cljs/init-state)
      (cljs/set-build-options
        {:public-dir (io/file "demo/js")
         :public-path "demo/js"})
      (cljs/set-build-options
        opts)
      (cljs/find-resources-in-classpath)
      (cljs/find-resources "src/cljs")
      (cljs/configure-module :demo ['demo.app] #{})
      ))

(defn dev [& args]
  (-> (project-setup {})
      (cljs/enable-source-maps)
      (cljs/compile-modules)
      (cljs/flush-unoptimized)))

(defn dev-repl [& args]
  (-> (project-setup {})
      (cljs/enable-source-maps)
      (devtools/start-loop
        {:before-load 'demo.app/stop
         :after-load 'demo.app/start
         })))

(defn dev-reload [& args]
  (-> (project-setup {})
      (cljs/enable-source-maps)
      (cljs/watch-and-repeat!
        (fn [state modified]
          (-> state
              (cljs/compile-modules)
              (cljs/flush-unoptimized)
              )))))

(defn production [& args]
  (-> (project-setup {:optimizations :advanced})
      (cljs/enable-emit-constants)
      (cljs/compile-modules)
      (cljs/closure-optimize)
      (cljs/flush-modules-to-disk)
      ))

(defn test-setup []
  (-> (cljs/init-state)
      (cljs/enable-source-maps)
      (cljs/set-build-options
        {:public-dir (io/file "target/cljs-test")
         :public-path "target/cljs-test"})
      (cljs/find-resources-in-classpath)
      (cljs/find-resources "src/cljs")
      (cljs/find-resources "test/cljs")
      ))

(defn autotest
  [& args]
  (-> (test-setup)
      (cljs/watch-and-repeat!
        (fn [state modified]
          (-> state
              (cond->
                ;; first pass, run all tests
                (empty? modified)
                (node/execute-all-tests!)
                ;; only execute tests that might have been affected by the modified files
                (not (empty? modified))
                (node/execute-affected-tests! modified))
              )))))

(defn test-all []
  (-> (test-setup)
      (node/execute-all-tests!)
      ))

(defn test-affected [test-ns]
  (-> (test-setup)
      (node/execute-affected-tests! [(cljs/ns->cljs-file test-ns)])
      ))

(defn -main [& args]
  (dev-repl))