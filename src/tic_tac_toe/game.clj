(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.user-interface :as ui]))

(defn request-move
  [game-ui player board prompt-func]
  (prompt-func)
  (->> (player/pick-move player game-ui board)
       (referee/validate-move request-move game-ui player board)))

(defn take-turn*
  [game-ui player board]
  (let [prompt-func (partial ui/prompt-move game-ui board player)]
    (-> (request-move game-ui player board prompt-func)
        (board/add-move board (:token player)))))
