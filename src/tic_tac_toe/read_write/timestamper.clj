(ns tic-tac-toe.read-write.timestamper
  (:use [clj-time.core :only [now interval in-days]]
        [clj-time.format :as f]))

(def date-formatter
  (f/formatter "yyyyMMdd"))

(defprotocol Timestamper
  (current-date [this])
  (date-difference [this updated]))

(defrecord FSTimestamper []
  Timestamper
  (current-date
    [this]
    (->> (now)
         (f/unparse (f/formatters :basic-date))))

    (date-difference
      [this updated]
      (-> (f/parse date-formatter updated)
          (interval (now))
          (in-days))))

(defn create-timestamper []
  (map->FSTimestamper {}))
