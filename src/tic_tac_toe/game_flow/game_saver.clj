(ns tic-tac-toe.game-flow.game-saver
  (:use [tic-tac-toe.menu.menu-selector :only [get-menu-selection]])
  (:require [tic-tac-toe.game-flow.game-exiter :as exiter]
            [tic-tac-toe.game-flow.game-loop :as loop]
            [tic-tac-toe.logs.game-to-log :as game-to-log]
            [tic-tac-toe.logs.log-selector :as log-selector]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]))

(defn- save
  [{:keys [writer dir id] :as game}]
  (writer/save-game writer dir id game)
  game)

(defn quick-save
  [{:keys [id] :as game}]
  (if (nil? id)
    (->> game
      (game-to-log/associate-id {:id 0})
      (recur))
    (-> game
        (save)
        (exiter/exit))))

(defn allow-overwrite
  [game]
  (-> game
      (assoc :overwrite true)))

(def overwrite-options
  (hash-map
    :y allow-overwrite
    :n (partial (fn [game] game))))

(defn handle-overwrite
  [{:keys [exit? save-exists] :as game}]
  (get-menu-selection overwrite-options save-exists game))

(defn- verify-overwriting
  [game log handle-overwrite]
  (cond
    (or (nil? (:name log))
        (game-to-log/named-same game log))
      (allow-overwrite game)
    (not (nil? (:name log)))
      (handle-overwrite game)))

(defn- verify-naming
  [{:keys [name overwrite] :as game} name-game]
  (if (and (nil? name) (= true overwrite))
    (name-game game)
    game))

(defn- verify-saving
  [{:keys [overwrite] :as game} save]
  (if (= true overwrite)
    (save game)
    game))

(defn handle-save-flow
  [save handle-overwrite name-game log {:keys [reader writer dir] :as game}]
  (if (reader/quick-save-exists? reader dir)
    (writer/delete-quick-save writer dir))
  (-> game
      (verify-overwriting log handle-overwrite)
      (verify-naming name-game)
      (verify-saving save)))

(defn save?
  [{:keys [reader dir exit? save-exists name] :as game}]
  (let [logs (reader/read-logs reader dir)
        log (log-selector/pick-log logs game)]
      (->> game
           (game-to-log/associate-id log)
           (handle-save-flow save handle-overwrite game-to-log/name-game log)
           (exiter/handle-exit))))
