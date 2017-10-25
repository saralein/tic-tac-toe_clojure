(ns tic-tac-toe.setup.quick-save
  (:require [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.read-write.writer :as writer]))

(defn- setup-quick-save
  [{:keys [reader dir] :as utils}]
  (-> (reader/load-game reader dir 0)
      (merge utils)))

(defn- skip-quick-save
  [prompt-setup {:keys [writer dir load-type] :as utils}]
  (writer/delete-quick-save writer dir)
  (prompt-setup utils))

(defn quick-save-options
  [prompt-setup]
  (hash-map
    :y setup-quick-save
    :n (partial skip-quick-save prompt-setup)))
