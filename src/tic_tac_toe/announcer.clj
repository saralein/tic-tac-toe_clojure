(ns tic-tac-toe.announcer)

(def human-move "Please pick a spot from 1-%d (enter \"?\" for help): ")
(def draw "Game over. It's a draw.\n")
(def win "Game over. %s wins!\n")

(defn announce-turn
  [player message]
  (->> (str (:name player) "'s turn.\n")
       (conj message)))

(defn move-request
  [board]
  (->> (count board)
       (format human-move)))

(defn winning-prompt
  [winner]
  (if (= 'draw winner)
    draw
    (format win winner)))

(defn gameover
  [winner message]
  (->> winner
       (winning-prompt)
       (conj message)))
