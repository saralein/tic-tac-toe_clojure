(ns tic-tac-toe.core
  (:gen-class)
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.io :as io]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.setup :as setup]
            [tic-tac-toe.user-interface :as ui]))

(defn play
  [game-ui current opponent board]
  (loop [game-ui game-ui
         current current
         opponent opponent
         board board]
    (if (referee/game-over? board)
      (->> (referee/get-status board opponent)
           (ui/prompt-gameover game-ui board))
      (recur game-ui opponent current (game/take-turn* game-ui current board)))))

(defn -main
  []
  (let [game-io (io/create-console-io)
        game-ui (ui/create-ui game-io)
        [current opponent] (setup/setup-players)
        board (board/make 3)]
    (play game-ui current opponent board)))
