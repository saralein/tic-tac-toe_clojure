(ns tic-tac-toe.read-write.reader
  (:use [clojure.java.io :as fs])
  (:require [tic-tac-toe.read-write.serializer :as serializer]))

(defprotocol Reader
  (load-game [this directory filename create-human create-comp])
  (save-exists? [this directory filename]))

(defrecord FSReader [serializer]
  Reader
  (load-game
    [this directory filename create-human create-comp]
    (->> (slurp (str directory "/" filename ".json"))
         (#(serializer/deserialize serializer % create-human create-comp))))

  (save-exists?
    [this directory filename]
    (.exists (fs/file (str directory "/" filename ".json")))))

(defn create-reader [serializer]
  (map->FSReader {:serializer serializer}))
