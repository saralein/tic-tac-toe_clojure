(ns tic-tac-toe.computer
  (:require [tic-tac-toe.player :as player]
            [tic-tac-toe.user-interface :as ui]))

(defn- first-available
  [board]
  (first (keep-indexed #(if (= " " %2) %1) board)))

(defrecord Computer []
  player/Player
  (pick-move
    [this game-ui board]
    (ui/pause game-ui)
    (first-available board)))

(defn create-computer-player [token]
  (map->Computer {:name "Player 2", :type :computer, :token token}))
