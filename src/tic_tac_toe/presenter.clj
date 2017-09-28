(ns tic-tac-toe.presenter
  (:use [clojure.math.numeric-tower :only [sqrt]]
        [clojure.string :only [join]]))

(defn add-divider
  [size]
  (str "\n" (join " + " (vec (repeat size "---"))) "\n"))

(defn append-spot
  [spot board]
  (let [spot (+ spot 1)
        length (count board)
        size (sqrt length)]
    (cond
      (= spot length) "\n\n"
      (= (mod spot size) 0) (add-divider size)
      :else " | ")))

(defn determine-token
  [token]
  (if (= token 'empty)
    " "
    token))

(defn create-string
  [board]
  (->> (map #(str " " (determine-token (board %)) " " (append-spot % board)) (range 9))
       (apply str)
       (str "\n")))

(defn board-display
  [board message]
  (-> message
    (conj (create-string board))))
