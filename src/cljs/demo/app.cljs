(ns demo.app)

(defn ^:export hello-world []
  (.log js/console "Hello World!"))

(hello-world)
