(ns tic-tac-toe.read-write.reader
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.read-write.filenames :as filenames]
        [tic-tac-toe.read-write.pathname :as path]
        [tic-tac-toe.read-write.timestamper]))

(defprotocol Reader
  (add-time-differential [this directory id game])
  (load-game [this directory filename])
  (read-logs [this directory])
  (save-exists? [this directory filename])
  (quick-save-exists? [this directory]))

(defrecord FSReader [timestamper]
  Reader
  (add-time-differential
    [this directory id file]
    (if (nil? (:updated file))
      file
      (->> file
           (:updated)
           (date-difference timestamper)
           (assoc file :time-diff))))

  (load-game
    [this directory id]
    (->> (path/generate directory id)
         slurp
         read-string
         (.add-time-differential this directory id)))

  (read-logs
    [this directory]
    (->> (filenames/generate-filenames)
         (map #(load-game this directory %))))

  (save-exists?
    [this directory id]
    (-> (path/generate directory id)
        fs/file
        .exists))

  (quick-save-exists?
    [this directory]
    (.save-exists? this directory 0)))

(defn create-reader [timestamper]
  (map->FSReader {:timestamper timestamper}))
