(ns tic-tac-toe.read-write.pathname)

(defn generate
  [directory filename ext]
  (str directory "/" filename ext))
