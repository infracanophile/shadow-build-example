(defproject thheller/shadow-build-example "0.1.0-SNAPSHOT"
  :description "example shadow-build setup"
  :url "https://github.com/thheller/shadow-build"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :profiles {:dev {:source-paths ["src/dev"]
                   :dependencies [[org.clojure/clojure "1.7.0"]
                                  [org.clojure/clojurescript "1.7.48"]
                                  [thheller/shadow-build "1.0.0-alpha16"]
                                  ]}})
