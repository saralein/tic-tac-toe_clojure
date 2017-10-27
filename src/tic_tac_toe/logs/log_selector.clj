(ns tic-tac-toe.logs.log-selector
  (require [tic-tac-toe.logs.log-presenter :as log-presenter]
           [tic-tac-toe.validation.log-validator :as log-validator]
           [tic-tac-toe.ui.user-interface :as ui]))

(defn input-matches-log-id?
  [input {:keys [id]}]
  (= input id))

(defn log-has-name?
  [{:keys [name] :as log}]
  (not (nil? name)))

(defn filter-logs
  [logs func]
  (->> logs
       (filter #(func %))))

(defn- get-log-selection
  ([logs game] (get-log-selection logs game ""))

  ([logs {:keys [ui dir] :as game} error]
    (->> (log-presenter/format-log-prompts game logs)
         (str error)
         (ui/prompt-selection ui)
         (log-validator/check-selection get-log-selection logs game))))

(defn pick-log
  [logs game]
  (->> (get-log-selection logs game)
       (partial input-matches-log-id?)
       (filter-logs logs)
       first))
