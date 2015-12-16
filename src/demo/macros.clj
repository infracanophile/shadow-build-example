(ns demo.macros)

(defmacro clojure-file-list-macro [x y]
  `(list ~x ~y))
