(ns tic-tac-toe.user-interface
  (:use [clojure.math.numeric-tower :only [sqrt]]
        [clojure.string :only [join]])
  (:require [tic-tac-toe.io :as io]
            [tic-tac-toe.announcer :as announcer]
            [tic-tac-toe.presenter :as presenter]))

(defprotocol UI
  (update-display [this message])
  (get-input [this])
  (clear-src [this])
  (pause [this])
  (prompt-move [this board player])
  (prompt-gameover [this board]))

(defrecord ConsoleUI [game-io]
  UI
  (update-display
    [this message]
    (io/display game-io (apply str message)))

  (get-input
    [this]
    (io/user-input game-io))

  (clear-src
    [this]
    (io/clears game-io))

  (pause
    [this]
    (Thread/sleep 1000))

  (prompt-move
    [this board player]
    (.clear-src this)
    (->> []
      (announcer/announce-turn player)
      (presenter/board-display board)
      (.update-display this)))

  (prompt-gameover
    [this board]
    (.clear-src this)
    (->> []
      (presenter/board-display board)
      (announcer/gameover)
      (.update-display this))))

(defn create-ui [game-io]
  (map->ConsoleUI {:game-io game-io}))
