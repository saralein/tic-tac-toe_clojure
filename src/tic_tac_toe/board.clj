(ns tic-tac-toe.board
  (:use [clojure.math.numeric-tower :only [expt]]))

(defn make
  [size]
  (vec (repeat (expt size 2) " ")))

(defn is-empty?
  [spot]
  (= spot " "))

(defn full?
  [board]
  (not-any? is-empty? board))

(defn add-move
  [move board]
  (assoc board move "X"))
