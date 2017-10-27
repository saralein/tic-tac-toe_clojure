(ns tic-tac-toe.read-write.pathname)

(def ext ".edn")

(defn generate
  [directory filename]
  (str directory "/" filename ext))
