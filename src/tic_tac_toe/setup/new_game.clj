(ns tic-tac-toe.setup.new-game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.players.computer :as computer]
            [tic-tac-toe.players.human :as human]))

(defn- setup-board [] {:board (board/make 3)})

(defn- setup-players
  []
  (hash-map
    :current (human/create-human-player "X")
    :opponent (computer/create-computer-player "O" "X")))

(defn setup-new-game
  [utils]
  (->> utils
       (merge (setup-board))
       (merge (setup-players))))
