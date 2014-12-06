(ns demo.app)

(defonce state (atom {:started 0}))

(defn ^:export start []
  (swap! state update-in [:started] inc)
  (.log js/console "starting app" (:started @state)))

(defn ^:export stop []
  (.log js/console "stopping app"))
