(ns tic-tac-toe.board)

(defn make
  [size]
  (vec (repeat (* size size) " ")))

(defn is-empty?
  [spot]
  (= spot " "))

(defn full?
  [board]
  (not-any? is-empty? board))

(defn add-move
  [move board]
  (assoc board move "X"))
