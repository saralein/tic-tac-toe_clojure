(ns tic-tac-toe.core
  (:gen-class)
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.setup :as setup]
            [tic-tac-toe.user-interface :as ui]))

(defn play
  [game]
  (loop [{:keys [board ui] :as game} game]
    (if (referee/game-over? board)
      (->> (referee/get-winner board)
           (ui/prompt-gameover ui board))
      (recur (game/take-turn* game)))))

(defn -main
  []
  (-> (setup/setup-utils)
      (setup/setup-game "saves" "game")
      (play)))
