(defproject thheller/shadow-build-example "0.1.0-SNAPSHOT"
  :description "example shadow-build setup"
  :url "https://github.com/thheller/shadow-build"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/clojure "1.6.0"]
                                  [org.clojure/clojurescript "0.0-2322"]
                                  [thheller/shadow-build "1.0.0-alpha2"]
                                  ]}} 
  
  :aliases {"cljs-dev" ["run" "-m" "shadow.cljs.api/build-dev" :project/cljs]
            "cljs-prod" ["run" "-m" "shadow.cljs.api/build-prod" :project/cljs]}
  
  :cljs {:modules [{:name :demo
                    :main 'demo.app}]
         
         :live-reload {:before-load 'demo.app/stop
                       :after-load 'demo.app/start}

         :source-paths ["src/cljs"]
         :public-dir "demo/js"
         :public-path "demo/js"}
  )
