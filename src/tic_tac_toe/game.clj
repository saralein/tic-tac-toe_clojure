(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]))

(defn- game-option?
  [game move]
  (contains? (:options game) (keyword move)))

(defn- perform-option
  [game move]
  (->> (keyword move)
       (get (:options game))
       (#(% game))))

(defn- switch-players
  [game]
  (assoc game :current (:opponent game) :opponent (:current game)))

(defn request-move
  [game prompt-func]
  (prompt-func)
  (let [move (player/pick-move (:current game) (:ui game) (:board game))]
    (if (game-option? game move)
      (perform-option game move)
      (referee/validate-move request-move game move))))

(defn take-turn*
  [game]
  (let [prompt-func (partial ui/prompt-move (:ui game) (:board game) (:current game))
        token (get-in game [:current :token])]
    (-> (request-move game prompt-func)
        (#(update game :board board/add-move % token))
        (switch-players))))
