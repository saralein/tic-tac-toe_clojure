(ns tic-tac-toe.player
  (:require [tic-tac-toe.user-interface :as ui]))

(defprotocol Player
  (pick-move [this ui]))

(defrecord Human []
  Player
  (pick-move [this ui] (ui/get-input ui)))

(defn create-player []
  (map->Human {}))
