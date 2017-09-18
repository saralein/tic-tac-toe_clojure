(ns tic-tac-toe.core
  (:gen-class)
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.io :as io]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.user-interface :as ui]))

(defn play
  [game-ui player1 board]
  (loop [game-ui game-ui
         player1 player1
         board board]
    (if (board/full? board)
      (ui/prompt-gameover game-ui board)
      (recur game-ui player1 (game/take-turn* game-ui player1 board)))))

(defn -main
  []
  (let [game-io (io/create-console-io)
        game-ui (ui/create-ui game-io)
        player1 (player/create-player)
        board (board/make 3)]
    (play game-ui player1 board)))
