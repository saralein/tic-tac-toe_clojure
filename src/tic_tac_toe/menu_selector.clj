(ns tic-tac-toe.menu-selector
  (:require [tic-tac-toe.user-interface :as ui]))

(defn pull-from-options
  [input options func error message]
  (get options (keyword input) (partial func options error message)))

(defn get-selection
  ([options message obj]
    (get-selection options "" message obj))

  ([options error message {:keys [ui]
      {:keys [invalid-selection]} :messages :as obj}]
    (-> (ui/prompt-selection ui (str error message))
        (pull-from-options options get-selection invalid-selection message)
        (#(% obj)))))
