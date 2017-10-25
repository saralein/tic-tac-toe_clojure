(ns tic-tac-toe.setup
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.menu.menu-selector :only [get-menu-selection]]
        [tic-tac-toe.menu.menu-messages :only [messages]]
        [tic-tac-toe.game-flow.game-saver :only [save? quick-save]]
        [tic-tac-toe.setup.quick-save :only [quick-save-options]])
  (:require [tic-tac-toe.setup.new-game :as new-game]
            [tic-tac-toe.setup.saved-game :as saved-game]
            [tic-tac-toe.game-flow.game-loop :as loop]
            [tic-tac-toe.ui.io :as io]
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

(defn- setup-directory
  [directory utils]
  (assoc utils :dir directory))

(def load-options
  (hash-map
    :1 new-game/setup-new-game
    :2 (partial saved-game/setup-saved-game
          new-game/setup-new-game)))

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

(defn- prompt-setup
  [{:keys [reader ui dir load-type quick-save] :as utils}]
  (if (reader/quick-save-exists? reader dir)
    (-> prompt-setup
        (quick-save-options)
        (get-menu-selection quick-save utils))
    (get-menu-selection load-options load-type utils)))

(defn setup-game
  [{:keys [ui welcome load-type] :as utils} directory]
  (ui/prompt-selection ui welcome)
  (->> (setup-directory directory utils)
       (prompt-setup)))
