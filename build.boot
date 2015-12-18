(set-env!
  :source-paths #{"src" "dev" "test"}
  :resource-paths #{"src" "dev" "test"}
  :dependencies '[[org.clojure/clojure "1.7.0"]
                  [thheller/shadow-build "1.0.190"]
                  [org.toomuchcode/clara-rules "0.9.2"]
                  [thheller/shadow-devtools "0.1.34"]
                  [adzerk/boot-cljs "1.7.170-3"]
                  [infracanophile/boot-cljs-test "0.3.8"]])

(require 'infracanophile.boot-cljs-test 'adzerk.boot-cljs)

(deftask cljs-test
  [n namespaces NAMESPACES #{sym}]
  (comp
    (infracanophile.boot-cljs-test/cljs-test-runner
      :namespaces namespaces)
    (adzerk.boot-cljs/cljs :optimizations :none)
    (infracanophile.boot-cljs-test/run-cljs-test
      :cmd "phantomjs")))
