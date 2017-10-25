(ns tic-tac-toe.setup.saved-game
  (:require [tic-tac-toe.logs.log-selector :as log-selector]
            [tic-tac-toe.read-write.reader :as reader]))

(defn- check-for-empty-log
  [game setup-new-game]
  (if (log-selector/log-has-name? game)
    game
    (setup-new-game game)))

(defn setup-saved-game
  [setup-new-game {:keys [reader dir no-save load-type] :as utils}]
  (let [logs (reader/read-logs reader dir)]
    (-> (log-selector/pick-log logs utils)
        (check-for-empty-log setup-new-game)
        (merge utils))))
