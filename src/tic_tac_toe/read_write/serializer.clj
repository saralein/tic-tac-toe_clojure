(ns tic-tac-toe.read-write.serializer
  (:use [clojure.data.json :only [write-str read-str]]))

(defn- format-board
  [board]
  (mapv #(if (= % "empty") 'empty %) board))

(defn- format-player
  [player create-human create-comp]
  (condp = (:type player)
    "human" (create-human (:token player))
    "computer" (create-comp (:token player) (:opponent player))))

(defprotocol Serializer
  (serialize [this data])
  (deserialize [this data create-human create-comp]))

(defrecord JSONSerializer []
  Serializer
  (serialize [this data] (write-str data))

  (deserialize
    [this data create-human create-comp]
    (-> (read-str data :key-fn keyword)
        (#(update % :current format-player create-human create-comp))
        (#(update % :opponent format-player create-human create-comp))
        (#(update % :board format-board)))))

(defn create-serializer []
  (map->JSONSerializer {}))
