(ns tic-tac-toe.game-flow.game-loop
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.players.player :as player]
            [tic-tac-toe.referee :as referee]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]))

(declare request-move)

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

(defn retry-move
  [prompt {:keys [board current ui] :as game}]
  (->> (partial ui/prompt-move ui board current prompt)
       (request-move game)))

(defn request-move
  [{:keys [current ui board] :as game} prompt-func]
  (prompt-func)
  (let [move (player/pick-move current ui board)]
    (if (game-option? game move)
      (perform-option game move)
      (referee/validate-move retry-move game move))))

(defn take-turn*
  [{:keys [current board ui] :as game}]
  (let [prompt-func (partial ui/prompt-move ui board current)]
    (-> (request-move game prompt-func)
        (#(update game :board board/add-move % (:token current)))
        (switch-players))))
