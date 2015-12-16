(defproject thheller/shadow-build-example "0.1.0-fork-SNAPSHOT"
  :description "example shadow-build setup"
  :url "https://github.com/thheller/shadow-build"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src"]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/clojure "1.7.0"]
                                  [thheller/shadow-build "1.0.190"]
                                  [thheller/shadow-devtools "0.1.34"]
                                  ]}})
