(ns tic-tac-toe.player)

(defprotocol Player
  (pick-move [this game-ui board]))
