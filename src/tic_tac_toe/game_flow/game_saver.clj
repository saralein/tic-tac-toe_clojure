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

(def overwrite-options
  (hash-map
    :y save
    :n game-to-log/disassociate-name))

(defn handle-overwrite
  [{:keys [exit? save-exists] :as game}]
  (get-menu-selection overwrite-options save-exists game))

(defn handle-save-flow
  [save handle-overwrite name-game log game]
  (cond
    (game-to-log/both-unnamed game log)
      (-> game
          (name-game)
          (save))
    (game-to-log/only-one-named game log)
      (-> game
          (save))
    (game-to-log/only-one-named log game)
      (-> game
          (name-game)
          (handle-overwrite))
    (game-to-log/named-same game log)
      (-> game
          (save))
    (not (game-to-log/named-same game log))
      (-> game
          (handle-overwrite))))

(defn save?
  [{:keys [reader dir exit? save-exists name] :as game}]
  (let [logs (reader/read-logs reader dir)
        log (log-selector/pick-log logs game)]
      (->> game
           (game-to-log/associate-id log)
           (handle-save-flow save handle-overwrite game-to-log/name-game log)
           (exiter/handle-exit))))
