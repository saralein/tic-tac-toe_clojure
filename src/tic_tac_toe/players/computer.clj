(ns tic-tac-toe.players.computer
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.players.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.ui.user-interface :as ui]))

(declare negamax)

(defn score-board
  [winner current opponent depth]
  (cond
    (= winner current) (- 100 depth)
    (= winner opponent) (- depth 100)
    :else 0))

(defn project-boards
  [board empty-spots current]
  (map #(board/add-move board % current) empty-spots))

(defn generate-scores
  [board current opponent depth]
  (let [empty-spots (board/get-empty-spots board)
        future-boards (project-boards board empty-spots current)]
    (map #(- (negamax % opponent current (inc depth))) future-boards)))

(defn negamax
  [board current opponent depth]
  (if (referee/game-over? board)
    (-> (referee/get-winner board)
        (score-board current opponent depth))
    (apply max (generate-scores board current opponent depth))))

(def negamax (memoize negamax))

(defrecord Computer []
  player/Player
  (pick-move
    [this game-ui board]
    (ui/pause game-ui)
    (let [empty-spots (board/get-empty-spots board)
          scores (generate-scores board (:token this) (:opponent this) 0)
          max-value (apply max scores)
          move (.indexOf scores max-value)]
      (nth empty-spots move))))

(defn create-computer-player [token opponent]
  (map->Computer {:name "Player 2", :type :computer, :token token :opponent opponent}))
