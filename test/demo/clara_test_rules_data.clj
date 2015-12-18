(ns demo.clara-test-rules-data)

(def the-rules
  [{:doc "Rule to determine whether it is indeed cold and windy."
    :name "clara.test-rules-data/is-cold-and-windy-data"
    :lhs '[{:type demo.clara-rules-testfacts/Temperature
            :constraints [(< temperature 20)
                          (== ?t temperature)]}
           {:type demo.clara-rules-testfacts/WindSpeed
            :constraints [(> windspeed 30)
                          (== ?w windspeed)]}]
    :rhs '(clara.rules/insert! (demo.clara-rules-testfacts/->ColdAndWindy ?t ?w))}

   {:name "clara.test-rules-data/find-cold-and-windy-data"
    :lhs '[{:fact-binding :?fact
            :type demo.clara-rules-testfacts/ColdAndWindy
            :constraints []}]
    :params #{}}])

(defn weather-rules
  "Return some weather rules"
  []
  the-rules)
