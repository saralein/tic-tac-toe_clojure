(ns tic-tac-toe.core
  (:gen-class)
  (:use [clojure.math.numeric-tower :only [expt]]
        [tic-tac-toe.board :as board]
        [tic-tac-toe.player :as player]
        [tic-tac-toe.user-interface :as ui]))

(def size 3)

(defn setup-player
  [type token]
  (hash-map :type type :token token))

(defn game-setup
  []
  (hash-map
    :size size
    :board (board/make size)
    :players (hash-map
      :current (setup-player :human "X")
      :other (setup-player :computer "O"))))

; (defn take-turn
;   [board size player]
;   (do (ui/display-board board size)
;       (ui/message (ui/request-move size))
;       (io/flushes)
;       (let [move (player/pick-move player board)]
;         (board/add-move player board move))))

(defn take-turn*
  [board player]
  (-> []
      (ui/clear)
      (ui/board-string board)
      (ui/request-move board)
      (ui/display-message))
  (let [move (player/pick-move player board)]
      (board/add-move player board move)))

(defn get-current-player
  [players]
  (get players :current))

(defn switch-current-player
  [players]
  (let [current (get players :current)
        other (get players :other)]
      (assoc players :current other :other current)))

; (defn play
;   [board]
;   (loop [board board]
;     (if (board/full? board)
;       (ui/game-over board)
;       (recur (take-turn* board)))))

(defn play
  [board players]
  (loop [board board
         players players]
    (if (board/full? board)
      (ui/game-over board)
      (let [player (get players :current)]
        (recur (take-turn* board player) (switch-current-player players))))))

(defn -main
  []
  (let [game-settings (game-setup)
        board (get game-settings :board)
        players (get game-settings :players)]
    (play board players)))
