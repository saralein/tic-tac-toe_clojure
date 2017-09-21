(ns tic-tac-toe.mocks.mock-ui
  (:require [tic-tac-toe.io :as io]
            [tic-tac-toe.user-interface :as ui]))

(defrecord MockUI [game-io]
  ui/UI
  (update-display
    [this message]
    (io/display game-io (apply str message)))

  (get-input
    [this]
    (io/user-input game-io))

  (clear-src
    [this]
    (io/clears game-io))

  (pause [this] "pause called")

  (prompt-move [this board player] "prompt-move called")

  (prompt-gameover [this board status] "prompt-gameover called"))

(defn create-mock-ui [game-io]
  (map->MockUI {:game-io game-io}))
