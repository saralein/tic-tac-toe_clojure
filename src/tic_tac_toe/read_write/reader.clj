(ns tic-tac-toe.read-write.reader
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.read-write.filenames :as filenames]
        [tic-tac-toe.read-write.pathname :as path]
        [tic-tac-toe.read-write.timestamper :as timestamper]))

(defprotocol Reader
  (add-time-differential [this directory id game])
  (load-game [this directory filename])
  (read-logs [this directory])
  (save-exists? [this directory filename]))

(defrecord FSReader [timestamper]
  Reader
  (add-time-differential
    [this directory id file]
    (if (nil? (:updated file))
      file
      (->> file
           (:updated)
           (timestamper/date-difference timestamper)
           (assoc file :time-diff))))

  (load-game
    [this directory id]
    (->> (path/generate directory id)
         (slurp)
         (read-string)
         (.add-time-differential this directory id)))

  (read-logs
    [this directory]
    (->> (filenames/generate-filenames)
         (map #(load-game this directory %))))

  (save-exists?
    [this directory id]
    (-> (path/generate directory id)
        (fs/file)
        (.exists))))

(defn create-reader [timestamper]
  (map->FSReader {:timestamper timestamper}))
