(ns tic-tac-toe.setup
  (:use [clojure.java.io :as fs])
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.io :as io]
            [tic-tac-toe.read-write.serializer :as serializer]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.utils :refer :all]
            [tic-tac-toe.read-write.writer :as writer]))

(def options (hash-map :s save-and-close))

(defn- setup-board [] (board/make 3))

(defn- setup-players
  []
  (hash-map :current (human/create-human-player "X")
            :opponent (computer/create-computer-player "O" "X")))

(defn- setup-utils
  []
  (let [game-serializer (serializer/create-serializer)
        game-io (io/create-console-io)]
    (hash-map :reader (reader/create-reader game-serializer)
              :writer (writer/create-writer game-serializer)
              :ui (ui/create-ui game-io)
              :options options)))

(defn- setup-saved-game
  [directory filename utils]
  ( -> (reader/load-game (:reader utils) directory filename
                         human/create-human-player
                         computer/create-computer-player)
       (merge utils)))

(defn- setup-new-game
  [utils]
  (merge {:board (setup-board)} (setup-players) utils))

(defn- new-or-saved?
  [directory filename utils]
  (if (reader/save-exists? (:reader utils) directory filename)
    (setup-saved-game directory filename utils)
    (setup-new-game utils)))

(defn setup-game
  [directory filename]
  (->> (setup-utils)
       (new-or-saved? directory filename)))
