(ns tic-tac-toe.read-write.reader
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.read-write.pathname :as path]))

(defprotocol Reader
  (load-game [this directory filename])
  (save-exists? [this directory filename]))

(defrecord FSReader []
  Reader
  (load-game
    [this directory filename]
    (-> (path/generate directory filename ".edn")
        (slurp)
        (read-string)))

  (save-exists?
    [this directory filename]
    (-> (path/generate directory filename ".edn")
        (fs/file)
        (.exists))))

(defn create-reader []
  (map->FSReader {}))
