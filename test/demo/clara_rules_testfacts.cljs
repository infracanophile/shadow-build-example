(ns demo.clara-rules-testfacts
  "This namespace exists primary for testing purposes, working around the fact that we cannot AOT compile test classes. This should be moved to the tests once a workaround for this is solved.")

;; Reflection against records requires them to be compiled AOT, so we temporarily
;; place them here as leiningen won't AOT compile test resources.
(defrecord Temperature [temperature location])
(defrecord WindSpeed [windspeed location])
(defrecord Cold [temperature])
(defrecord ColdAndWindy [temperature windspeed])
(defrecord LousyWeather [])

;; Test facts for chained rules.
(defrecord First [])
(defrecord Second [])
(defrecord Third [])
(defrecord Fourth [])
