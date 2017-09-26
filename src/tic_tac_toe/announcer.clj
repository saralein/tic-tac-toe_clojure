(ns tic-tac-toe.announcer)

(def human-move "Please pick a spot from 1-%d: ")

(defn announce-turn
  [player message]
  (conj message (str "\n"(:name player) "'s turn.\n")))

  (defn move-request
    [board]
    (format human-move (count board)))

(defn gameover
  [message]
  (conj message "Game over.\n"))
