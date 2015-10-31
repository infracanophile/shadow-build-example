(ns demo.app)

(defonce state (atom {:started 0}))

(defn ^:export start []
  (swap! state update-in [:started] inc)
  (.log js/console "starting app" (:started @state)))

(defn ^:export stop []
  (.log js/console "stopping app"))

(comment
  ;; swich to this ns (ctrl+shift+n in cursive)

  ;; put cursor on state and eval (ctrl+shift+t in cursive)
  @state

  (swap! state update :started inc)

  )