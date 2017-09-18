(ns tic-tac-toe.user-interface
  (:use [clojure.math.numeric-tower :only [sqrt]]
        [clojure.string :only [join]]
        [tic-tac-toe.io :as io]))

(defn request-move
  [message board]
  (conj message (format "Please pick a spot from 1-%d: " (count board))))

(defn display-message
  [message]
  (io/output (apply str message)))

(defn parse-int
  [string]
  (Integer. (re-find  #"\d+" string)))

(defn get-input
  []
  (io/flushes)
  (io/input))

(defn row-divider
  [size]
  (str "\n" (join " + " (vec (repeat size "---"))) "\n"))

(defn append-spot
  [spot board]
  (let [spot (+ spot 1)
        length (count board)
        size (sqrt length)]
    (cond
      (= spot length) "\n\n"
      (= (mod spot size) 0) (row-divider size)
      :else " | "
      )))

(defn board-string
  [message board]
  (conj message (str "\n" (apply str (map #(str " " (board %) " " (append-spot % board)) (range 9))))))

(defn clear
  [message]
  (conj message (format "\033[2J")))

(defn game-over
  [board]
  (-> []
    (clear)
    (board-string board)
    (conj "Game over.\n")
    (display-message)))
