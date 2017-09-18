(ns tic-tac-toe.player
  (:use [tic-tac-toe.user-interface :as ui]))

(defn pick-move
  []
  (- (ui/parse-int (ui/get-input)) 1))
