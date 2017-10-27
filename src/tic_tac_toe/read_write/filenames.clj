(ns tic-tac-toe.read-write.filenames)

(def number-saves 3)

(defn generate-filenames
  []
  (->> number-saves
       inc
       (range 1)
       (map #(str %))))
