(ns fact.clara-test
  (:require #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t])
            fact.clara
            [clara.rules :as clara]
            fact.clara-test-rules)
  #?(:cljs (:require-macros [cljs.test :as t]
                            fact.clara-test
                            fact.clara-test-rules)))

; mk-fact-session tests

(t/deftest fact-session-test
  (let [output (atom #{})]
    (-> (fact.clara/mk-fact-session
          ['fact.clara-test-rules/vip-discount
           'fact.clara-test-rules/big-purchase]
          :cache false)
        (clara/insert {:ftype-name :customer, :status :vip}
                      {:ftype-name :purchase, :cost 501}
                      {:ftype-name :output, :value output})
        (clara/fire-rules))
    (t/is (= #{:vip-discount-fired :big-purchase-fired} @output))))
