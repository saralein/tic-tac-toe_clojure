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
      [" " & r] false
      [(_ :guard #(not= token %)) & r] false
      [token & r] (recur token (vec (rest pattern))))))

(defn winner?
  [board]
  (let [patterns (board/give-patterns board)]
    (loop [patterns patterns]
      (cond
        (empty? patterns) false
        (matching-pattern? ((first patterns) 0) (vec (first patterns))) true
        :else (recur (vec (rest patterns)))))))

(defn get-status
  [board opponent]
  (if (winner? board)
    {:winner opponent}
    {:winner :none}))

(defn game-over?
  [board]
  (or (winner? board) (draw? board)))
