(ns tic-tac-toe.user-interface
  (:use [clojure.math.numeric-tower :only [sqrt]]
        [clojure.string :only [join]])
  (:require [tic-tac-toe.io :as io]
            [tic-tac-toe.announcer :as announcer]
            [tic-tac-toe.presenter :as presenter]))

(defprotocol UI
  (update-display [this message])
  (get-input [this])
  (clear-ui [this])
  (flush-ui [this])
  (pause-ui [this])
  (prompt-move [this board player])
  (prompt-gameover [this board]))

(defrecord ConsoleUI [game-io]
  UI
  (update-display
    [this message]
    (io/display game-io (apply str message)))

  (get-input
    [this]
    (.flush-ui this)
    (io/user-input game-io))

  (clear-ui
    [this]
    (io/clear-io game-io))

  (flush-ui
    [this]
    (io/flush-io game-io))

  (pause-ui
    [this]
    (io/pause-io game-io))

  (prompt-move
    [this board player]
    (.clear-ui this)
    (->> []
      (announcer/announce-turn player)
      (presenter/board-display board)
      (.update-display this))
    (.flush-ui this))

  (prompt-gameover
    [this board]
    (.clear-ui this)
    (->> []
      (presenter/board-display board)
      (announcer/gameover)
      (.update-display this))))

(defn create-ui [game-io]
  (map->ConsoleUI {:game-io game-io}))
