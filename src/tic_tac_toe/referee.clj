(ns tic-tac-toe.referee
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.validation.board-validator :refer :all]
            [tic-tac-toe.validation.move-validator :refer :all])
  (:use [clojure.core.match :refer [match]]))

(defn validate-move
  [request-move game-ui player board move]
  (let [result (check-spot board move)]
    (if (:valid result)
      (:data result)
      (->> (:data result)
           (partial ui/prompt-move game-ui board player)
           (request-move game-ui player board)))))

(defn draw?
  [board]
  (board/full? board))

(defn winner?
  [board]
  (evaluate-board board single-token?))

(defn get-winner
  [board]
  (evaluate-board board type-win))

(defn game-over?
  [board]
  (or (winner? board) (draw? board)))
