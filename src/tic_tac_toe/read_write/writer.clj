(ns tic-tac-toe.read-write.writer
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.read-write.pathname :as path]))

(defprotocol Writer
  (bundle-state [this game])
  (save-game [this directory filename game]))

(defrecord FSWriter []
  Writer
  (bundle-state
    [this game]
    (select-keys game [:board :current :opponent]))

  (save-game
    [this directory filename game]
    (let [path (path/generate directory filename ".edn")]
      (fs/make-parents path)
      (->> (.bundle-state this game)
           (pr-str)
           (spit path)))))

(defn create-writer []
  (map->FSWriter {}))
