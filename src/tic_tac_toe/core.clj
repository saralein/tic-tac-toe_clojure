(ns tic-tac-toe.core
  (:gen-class)
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.setup :as setup]
            [tic-tac-toe.user-interface :as ui]))

(defn- shutdown-listener
  [{:keys [ui]}]
  (.addShutdownHook (Runtime/getRuntime)
    (Thread. #(ui/clear-src ui))))

(defn play
  [game]
  (loop [{:keys [board ui] :as game} game]
    (if (referee/game-over? board)
      (->> (referee/get-winner board)
           (ui/prompt-gameover ui board))
      (recur (game/take-turn* game)))))

(defn -main
  []
  (let [utils (setup/setup-utils)]
    (shutdown-listener utils)
    (-> (setup/setup-game utils "saves" "game")
        (play))))
