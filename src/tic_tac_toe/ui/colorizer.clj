(ns tic-tac-toe.ui.colorizer
  (:use [tic-tac-toe.ui.colors :refer :all]))

(defprotocol Colorizer
  (highlight [this text])
  (process-color [this color-method location prompts]))

(defrecord ConsoleColorizer []
  Colorizer
  (highlight [this text]
    (str blue-background text clear-background))

  (process-color [this color-method location prompts]
    (->> prompts
         (keep-indexed #(if (= location %1) (color-method this %2) %2)))))

(defn create-colorizer []
  (map->ConsoleColorizer {}))
