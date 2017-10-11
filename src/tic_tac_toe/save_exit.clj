(ns tic-tac-toe.save-exit
  (:use [tic-tac-toe.menu-selector :only [get-selection]])
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.menu-selector :refer :all]
            [tic-tac-toe.read-write.writer :as writer]))

(defn- save
  [{:keys [writer dir file] :as game}]
  (writer/save-game writer dir file game))

(defn- exit
  [{:keys [ui]}]
  (ui/exit ui))

(defn- save-and-exit
  [game]
  (save game)
  (exit game))

(def exit-options (hash-map :y exit :n (partial game/retry-move "")))

(defn- save-options
  [prompt]
  (hash-map :y save-and-exit
            :n (partial get-selection exit-options prompt)))

(defn save?
  [{:keys [reader dir file]
    {:keys [save-exists exit?]} :messages :as game}]
  (if (reader/save-exists? reader dir file)
    (-> (save-options exit?)
        (get-selection save-exists game))
    (save-and-exit game)))
