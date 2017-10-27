(ns tic-tac-toe.menu.menu-selector
  (:require [tic-tac-toe.ui.user-interface :as ui]))

(defn pull-from-options
  [input options func error message]
  (get options (keyword input) (partial func options error message)))

(defn get-menu-selection
  ([options message obj]
    (get-menu-selection options "" message obj))

  ([options error message {:keys [ui invalid-selection] :as obj}]
    (-> (ui/prompt-selection ui (str error message))
        (pull-from-options options get-menu-selection invalid-selection message)
        (#(% obj)))))
