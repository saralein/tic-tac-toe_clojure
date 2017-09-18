(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.user-interface :as ui]))

(defn request-move
  [game-ui player1]
  (-> (player/pick-move player1 game-ui)
      (referee/validate-move)))

(defn take-turn*
  [game-ui player1 board]
  (ui/prompt-move game-ui board)
  (-> (request-move game-ui player1)
      (board/add-move board)))
