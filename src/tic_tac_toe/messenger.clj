(ns tic-tac-toe.messenger)

(defn move-request
  [board message]
  (conj message (format "Please pick a spot from 1-%d: " (count board))))

(defn gameover
  [message]
  (conj message "Game over.\n"))
