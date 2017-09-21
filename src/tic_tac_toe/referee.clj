(ns tic-tac-toe.referee
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.player :as player]))

(defn convert-to-spot
  [move]
  (if (string? move)
    (- (Integer/parseInt move) 1)
    move))

(defn validate-move
  [player move]
  (convert-to-spot move))
