(ns tic-tac-toe.referee
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.validation.board-validator :refer :all]
            [tic-tac-toe.validation.move-validator :refer :all]
            [tic-tac-toe.read-write.writer :as writer])
  (:use [clojure.core.match :refer [match]]))

(defn validate-move
  [retry-move {:keys [board current ui] :as game} move]
  (let [result (check-spot board move)]
    (if (:valid result)
      (:data result)
      (-> (:data result)
           (retry-move game)))))

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
