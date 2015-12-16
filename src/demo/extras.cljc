(ns demo.extras
  #?(:cljs (:require-macros demo.extras
                            demo.macros
                            )))

#?(:clj
(defmacro list-macro [x y]
  `(list ~x ~y))
)

(defn make-tuple [x y]
  (demo.extras/list-macro x y))

(defn clojure-file-make-tuple [x y]
  (demo.macros/clojure-file-list-macro x y))
