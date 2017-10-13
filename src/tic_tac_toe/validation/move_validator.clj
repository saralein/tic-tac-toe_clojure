(ns tic-tac-toe.validation.move-validator
  (:require [tic-tac-toe.board :as board]))

(def invalid-spot "Not a valid spot.\n\n")
(def out-of-range "Not a number between 1-%d.\n\n")
(def spot-taken "Spot already taken.\n\n")

(defn- convert-to-index
  [move]
  (- (Integer/parseInt move) 1))

(defn- is-int?
  [move]
  (->> (re-matches #"\d+" move)
       (not= nil)))

(defn- in-range?
  [board move]
  (->> (count board)
       (range)
       (some #(= move %))
       (not= nil)))

(defn- available?
  [board move]
  (board/is-empty? (board move)))

(defn- get-error
  [board move]
  (cond
    (not (in-range? board move))
      (format out-of-range (count board))
    (not (available? board move))
      spot-taken))

(defn- valid-spot?
  [board move]
  (let [move (convert-to-index move)]
    (if (and (in-range? board move) (available? board move))
      {:valid true :data move}
      {:valid false :data (get-error board move)})))

(defn check-spot
  [board move]
  (cond
    (not (string? move))
      {:valid true :data move}
    (is-int? move)
      (valid-spot? board move)
    :else
      {:valid false :data invalid-spot}))
