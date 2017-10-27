(ns tic-tac-toe.game-flow.game-exiter
  (:use [tic-tac-toe.menu.menu-selector :only [get-menu-selection]])
  (:require [tic-tac-toe.game-flow.game-loop :as loop]
            [tic-tac-toe.ui.user-interface :as ui]))

(defn exit
  [{:keys [ui]}]
  (ui/exit ui))

(def exit-options
  (hash-map
    :y exit
    :n (partial loop/retry-move "")))

(defn handle-exit
  [{:keys [exit?] :as game}]
  (get-menu-selection exit-options exit? game))
