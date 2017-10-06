(ns tic-tac-toe.utils
  (:require [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]))

(defn save-and-close
  [game]
  (writer/save-game (:writer game) "saves" "game" game)
  (ui/close (:ui game)))
