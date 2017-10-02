(ns tic-tac-toe.read-write.writer
  (:use [clojure.java.io :as fs])
  (:require [tic-tac-toe.read-write.serializer :as serializer]))

(defprotocol Writer
  (bundle-state [this game])
  (serialize-state [this game])
  (save-game [this directory filename game]))

(defrecord FSWriter [serializer]
  Writer
  (bundle-state
    [this game]
    (select-keys game [:board :current :opponent]))

  (serialize-state
    [this game]
    (->> (.bundle-state this game)
        (serializer/serialize serializer)))

  (save-game
    [this directory filename game]
    (let [path (str directory "/" filename ".json")]
      (fs/make-parents path)
      (->> (.serialize-state this game)
           (spit path)))))

(defn create-writer [serializer]
  (map->FSWriter {:serializer serializer}))
