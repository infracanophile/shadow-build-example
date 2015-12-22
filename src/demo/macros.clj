(ns demo.macros
  (:require cljs.env))

(defmacro clojure-file-list-macro [x y]
  `(list ~x ~y))

(defmacro clara-productions []
  (let [ps (get-in @cljs.env/*compiler* [:clara.macros/productions])
        s (str ps)]
    `[:productions ~s]))
