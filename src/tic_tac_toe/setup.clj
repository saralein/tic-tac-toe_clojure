(ns tic-tac-toe.setup
  (:require [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]))

(defn setup-players
  []
  (vector (human/create-human-player "X")
          (computer/create-computer-player "O")))
