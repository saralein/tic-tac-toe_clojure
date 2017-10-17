(ns tic-tac-toe.user-interface
  (:use [clojure.math.numeric-tower :only [sqrt]]
        [clojure.string :only [join]])
  (:require [tic-tac-toe.io :as io]
            [tic-tac-toe.announcer :as announcer]
            [tic-tac-toe.presenter :as presenter]))

(defprotocol UI
  (update-display [this message])
  (exit [this])
  (quit? [this input])
  (get-input [this])
  (clear-src [this])
  (pause [this])
  (prompt-move [this board player][this board player optional])
  (prompt-selection [this message])
  (optional-prompt [this prompt message])
  (prompt-gameover [this board winner]))

(defrecord ConsoleUI [game-io exit-method]
  UI
  (update-display
    [this message]
    (io/display game-io (apply str message)))

  (quit?
    [this input]
    (if (= nil input)
      (exit this)
      input))

  (exit
    [this]
    (exit-method))

  (get-input
    [this]
    (->> (io/user-input game-io)
         (.quit? this)))

  (clear-src
    [this]
    (io/clears game-io))

  (pause
    [this]
    (Thread/sleep 1000))

  (prompt-move
    [this board player]
    (prompt-move this board player ""))

  (prompt-move
    [this board player prompt]
    (.clear-src this)
    (->> []
      (announcer/announce-turn player)
      (presenter/board-display board)
      (.optional-prompt this prompt)
      (.update-display this)))

  (prompt-selection
    [this prompt]
    (.clear-src this)
    (.update-display this prompt)
    (.get-input this))

  (optional-prompt
    [this prompt message]
    (conj message prompt))

  (prompt-gameover
    [this board winner]
    (.clear-src this)
    (->> []
      (presenter/board-display board)
      (announcer/gameover winner)
      (.update-display this))))

(defn create-ui [game-io exit-method]
  (map->ConsoleUI {:game-io game-io :exit-method exit-method}))
