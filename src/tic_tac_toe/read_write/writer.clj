(ns tic-tac-toe.read-write.writer
  (:use [clojure.java.io :as fs]
        [tic-tac-toe.read-write.pathname :as path]
        [tic-tac-toe.read-write.timestamper :as timestamper]))

(defprotocol Writer
  (bundle-state [this game])
  (save-game [this directory id game])
  (delete-game [this directory id])
  (delete-quick-save [this directory]))

(defrecord FSWriter [timestamper]
  Writer
  (bundle-state
    [this game]
    (-> game
        (select-keys [:id :name :board :current :opponent])
        (assoc :updated (timestamper/current-date timestamper))))

  (save-game
    [this directory id game]
    (let [path (path/generate directory id)]
      (fs/make-parents path)
      (->> game
           (.bundle-state this)
           pr-str
           (spit path))))

  (delete-game
    [this directory id]
    (-> (path/generate directory id)
        fs/delete-file))

  (delete-quick-save
    [this directory]
    (.delete-game this directory 0)))

(defn create-writer [timestamper]
  (map->FSWriter {:timestamper timestamper}))
