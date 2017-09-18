(ns tic-tac-toe.referee)

(defn convert-to-int
  [move]
  (Integer/parseInt move))

(defn convert-to-spot
  [move]
  (- move 1))

(defn validate-move
  [move]
  (-> move
    (convert-to-int)
    (convert-to-spot)))
