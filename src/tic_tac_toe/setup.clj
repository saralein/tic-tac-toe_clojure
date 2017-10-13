(ns tic-tac-toe.setup
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.menu.menu-selector :only [get-menu-selection]]
        [tic-tac-toe.menu.menu-messages :only [messages]]
        [tic-tac-toe.game-flow.game-saver :only [save? quick-save]])
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.players.computer :as computer]
            [tic-tac-toe.game-flow.game-loop :as loop]
            [tic-tac-toe.players.human :as human]
            [tic-tac-toe.ui.io :as io]
            [tic-tac-toe.logs.log-selector :as log-selector]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.read-write.timestamper :as timestamper]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]))

(declare load-options)
(declare prompt-setup)

(defn- game-options
  [prompt]
  (hash-map
    :? (partial loop/retry-move prompt)
    :s save?
    :s! quick-save))

(defn- setup-board [] {:board (board/make 3)})

(defn- setup-players
  []
  (hash-map
    :current (human/create-human-player "X")
    :opponent (computer/create-computer-player "O" "X")))

(defn- setup-directory
  [directory utils]
  (assoc utils :dir directory))

(defn- setup-new-game
  [utils]
  (->> utils
       (merge (setup-board))
       (merge (setup-players))))

(defn- check-for-empty-log
  [game]
  (if (log-selector/log-has-name? game)
    game
    (setup-new-game game)))

(defn- setup-saved-game
  [{:keys [reader dir no-save load-type] :as utils}]
  (let [logs (reader/read-logs reader dir)]
    (-> (log-selector/pick-log logs utils)
        (check-for-empty-log)
        (merge utils))))

(defn- setup-quick-save
  [{:keys [reader dir] :as utils}]
  (-> (reader/load-game reader dir 0)
      (merge utils)))

(def load-options
  (hash-map
    :1 setup-new-game
    :2 setup-saved-game))

(defn setup-utils
  []
  (let [game-io (io/create-console-io)
        timestamper (timestamper/create-timestamper)]
    (-> (hash-map
         :reader (reader/create-reader timestamper)
         :writer (writer/create-writer timestamper)
         :ui (ui/create-ui game-io #(System/exit 0))
         :options (game-options (:help messages)))
        (merge messages))))

(defn- skip-quick-save
  [{:keys [writer dir load-type] :as utils}]
  (writer/delete-game writer dir 0)
  (prompt-setup utils))

(def quick-save-options
  (hash-map
    :y setup-quick-save
    :n skip-quick-save))

(defn- quick-save-exists?
  [{:keys [reader dir] :as utils}]
  (reader/save-exists? reader dir 0))

(defn- prompt-setup
  [{:keys [reader ui dir load-type quick-save] :as utils}]
  (if (quick-save-exists? utils)
    (get-menu-selection quick-save-options quick-save utils)
    (get-menu-selection load-options load-type utils)))

(defn setup-game
  [{:keys [ui welcome load-type] :as utils} directory]
  (ui/prompt-selection ui welcome)
  (->> (setup-directory directory utils)
       (prompt-setup)))
