(ns tic-tac-toe.announcer)

(def human-move "Please pick a spot from 1-%d: ")

(defn announce-turn
  [player message]
  (conj message (str "\n"(:name player) "'s turn.\n")))

  (defn move-request
    [board]
    (format human-move (count board)))

(defn player-win
  [player]
  (str (:name player) " wins!\n"))

(defn winning-prompt
  [status]
  (-> "Game over. "
      (cond->
        (= :none (:winner status))
          (str "It's a draw.\n")
        (not= :none (:winner status))
          (str (player-win (:winner status))))))

(defn gameover
  [status message]
  (conj message (winning-prompt status)))
