(ns tic-tac-toe.mocks.mock-timestamper
  (:require [tic-tac-toe.read-write.timestamper :as timestamper]))

(defrecord MockTimestamper []
  timestamper/Timestamper
  (current-date
    [this]
    "20001031")

  (date-difference
    [this updated]
    "3"))

(defn create-mock-timestamper
  []
  (map->MockTimestamper {}))
