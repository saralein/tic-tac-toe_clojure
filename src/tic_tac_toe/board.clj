(ns tic-tac-toe.board
  (:use [clojure.math.numeric-tower :only [sqrt]]))

(defn make
  [size]
  (vec (repeat (* size size) 'empty)))

(defn is-empty?
  [spot]
  (= spot 'empty))

(defn get-empty-spots
  [board]
  (keep-indexed #(if (is-empty? %2) %1) board))

(defn full?
  [board]
  (not-any? is-empty? board))

(defn add-move
  [move board token]
  (assoc board move token))

(defn get-rows
  [board size]
  (mapv vec (partition size board)))

(defn get-columns
  [rows]
  (apply mapv vector rows))

(defn modify-cells
  [grid direction]
    (if (= :up direction)
        (reverse grid)
        grid))

(defn get-diagonal
  [rows size direction]
  (let [grid (range size)
        cells (modify-cells grid direction)]
    (vec (map #((rows %1) %2) grid cells))))

(defn give-patterns
  [board]
  (let [size (sqrt (count board))
        rows (get-rows board size)]
    (-> (concat rows (get-columns rows))
        (conj (get-diagonal rows size :up))
        (conj (get-diagonal rows size :down)))))

(def give-patterns (memoize give-patterns))
