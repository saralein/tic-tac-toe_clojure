(ns tic-tac-toe.setup
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.menu-selector :only [get-selection]]
        [tic-tac-toe.menu-messages :only [messages]]
        [tic-tac-toe.save-exit :only [save? save-and-exit]])
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.io :as io]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]))

(declare load-options)

(defn- game-options
  [prompt]
  (hash-map
    :? (partial game/retry-move prompt)
    :s save?))

(defn- setup-board [] {:board (board/make 3)})

(defn- setup-players
  []
  (hash-map
    :current (human/create-human-player "X")
    :opponent (computer/create-computer-player "O" "X")))

(defn- setup-location
  [directory filename utils]
  (assoc utils :dir directory :file filename))

(defn- setup-saved-game
  [{:keys [reader dir file]
    {:keys [no-save load-type]} :messages :as utils}]
  (if (reader/save-exists? reader dir file)
    (-> (reader/load-game reader dir file)
        (merge utils))
    (get-selection load-options no-save load-type utils)))

(defn- setup-new-game
  [utils]
  (->> utils
       (merge (setup-board))
       (merge (setup-players))))

(def load-options
  (hash-map
    :1 setup-new-game
    :2 setup-saved-game))

(defn setup-utils
  []
  (let [game-io (io/create-console-io)]
    (hash-map
      :reader (reader/create-reader)
      :writer (writer/create-writer)
      :ui (ui/create-ui game-io #(System/exit 0))
      :messages messages
      :options (game-options (:help messages)))))

(defn setup-game
  [{:keys [ui]
    {:keys [welcome load-type]} :messages :as utils}
    directory filename]
  (ui/prompt-selection ui welcome)
  (->> (setup-location directory filename utils)
       (get-selection load-options load-type)))
