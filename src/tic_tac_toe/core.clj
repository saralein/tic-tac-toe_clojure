(ns tic-tac-toe.core
  (:gen-class)
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.setup :as setup]
            [tic-tac-toe.user-interface :as ui]))

(defn play
  [game]
  (loop [game game]
    (if (referee/game-over? (:board game))
      (->> (referee/get-winner (:board game))
           (ui/prompt-gameover (:ui game) (:board game)))
      (recur (game/take-turn* game)))))

(defn -main
  []
  (let [game (setup/setup-game "saves" "game")]
    (play game)))
