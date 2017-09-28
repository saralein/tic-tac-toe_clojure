(ns tic-tac-toe.announcer)

(def human-move "Please pick a spot from 1-%d: ")
(def draw "Game over. It's a draw.\n")
(def win "Game over. %s wins!\n")

(defn announce-turn
  [player message]
  (conj message (str "\n"(:name player) "'s turn.\n")))

(defn move-request
  [board]
  (format human-move (count board)))

(defn winning-prompt
  [winner]
  (if (= 'draw winner)
    draw
    (format win winner)))

(defn gameover
  [winner message]
  (conj message (winning-prompt winner)))
