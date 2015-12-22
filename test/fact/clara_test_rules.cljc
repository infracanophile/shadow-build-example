(ns fact.clara-test-rules
  (:require [clara.rules :as clara]))

(clara/defrule vip-discount
  [:customer [{status :status}] (= status :vip) (== ?customer-status status)]
  [:purchase [{cost :cost}] (> cost 100)]
  [:output [{output :value}] (== ?output output)]
  =>
  (swap! ?output #(conj % :vip-discount-fired)))

(clara/defrule big-purchase
  [:purchase [{cost :cost}] (> cost 500)]
  [:output [{output :value}] (== ?output output)]
  =>
  (swap! ?output #(conj % :big-purchase-fired)))
