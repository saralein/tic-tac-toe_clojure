(ns tic-tac-toe.referee
  (:require [tic-tac-toe.board :as board])
  (:use [clojure.core.match :refer [match]]))

(defn convert-to-spot
  [move]
  (if (string? move)
    (- (Integer/parseInt move) 1)
    move))

(defn validate-move
  [player move]
  (convert-to-spot move))

(defn draw?
  [board]
  (board/full? board))

(defn matching-pattern?
  [token pattern]
  (loop [token token
         pattern pattern]
    (match pattern
      [token] true
      [token & r] (recur token (vec (rest pattern)))
      [_ & r] false)))

(defn get-matching-patterns
  [board]
  (->> (board/give-patterns board)
       (filter #(matching-pattern? (first %) %))))

(defn remove-empty-patterns
  [patterns]
  (->> (filter #(not (board/is-empty? (first %))) patterns)
       (flatten)))

(defn evaluate-board
  [board func]
  (->> (get-matching-patterns board)
       (remove-empty-patterns)
       (func)))

(defn single-token?
  [pattern]
  (not (empty? pattern)))

(defn type-win
  [pattern]
  (if (empty? pattern)
    'draw
    (first pattern)))

(defn winner?
  [board]
  (evaluate-board board single-token?))

(defn get-winner
  [board]
  (evaluate-board board type-win))

(defn game-over?
  [board]
  (or (winner? board) (draw? board)))
