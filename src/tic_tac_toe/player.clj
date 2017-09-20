(ns tic-tac-toe.player
  (:use [tic-tac-toe.user-interface :as ui]))

(defn move-input
  []
  (- (ui/parse-int (ui/get-input)) 1))

(defn pick-next-open-spot
  [board]
  (loop [i 0
         board board]
  (if (= (board i) " ")
    i
    (recur (inc i) board))))

(defmulti pick-move (fn [type & args] (:type type)))
(defmethod pick-move :human [player board] (move-input))
(defmethod pick-move :computer [player board] (pick-next-open-spot board))
