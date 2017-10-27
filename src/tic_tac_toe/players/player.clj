(ns tic-tac-toe.players.player)

(defprotocol Player
  (pick-move [this game-ui board]))
