(ns tic-tac-toe.core
  (:gen-class)
  (:use [clojure.math.numeric-tower :only [expt]]
        [tic-tac-toe.board :as board]
        [tic-tac-toe.player :as player]
        [tic-tac-toe.user-interface :as ui]))

(def size 3)

(defn game-setup
  []
  (hash-map :board (board/make size)))

(defn take-turn*
  [board]
  (-> []
      (ui/clear)
      (ui/board-string board)
      (ui/request-move board)
      (ui/display-message))
  (let [move (player/pick-move)]
      (board/add-move move board)))

(defn play
  [board]
  (loop [board board]
    (if (board/full? board)
      (ui/game-over board)
      (recur (take-turn* board)))))

(defn -main
  []
  (let [game-settings (game-setup)
        board (get game-settings :board)]
    (play board)))
