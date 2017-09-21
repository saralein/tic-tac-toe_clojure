(ns tic-tac-toe.mocks.mock-io
  (:require [tic-tac-toe.io :as io]))

(defrecord MockIO [value output]
  io/IO
  (user-input [x] @value)
  (display [x message] (reset! output message))
  (clear-io [x] "clear called")
  (flush-io [x] "flush called")
  (pause-io [x] "pause called"))

(defn mock-value-output
  [value output]
  (->MockIO (atom value) (atom output)))

(defn create-mock-io [value output]
  (map->MockIO {:value value :output output}))
