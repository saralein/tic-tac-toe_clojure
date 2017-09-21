(ns tic-tac-toe.human
  (:require [tic-tac-toe.announcer :as announcer]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.user-interface :as ui]))

(defrecord Human []
  player/Player
  (pick-move
    [this game-ui board]
    (->> (announcer/move-request board)
        (ui/update-display game-ui))
    (ui/get-input game-ui)))

(defn create-human-player [token]
  (map->Human {:name "Player 1", :type :human, :token token}))
