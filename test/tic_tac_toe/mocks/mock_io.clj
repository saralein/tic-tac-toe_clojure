(ns tic-tac-toe.mocks.mock-io
  (:require [tic-tac-toe.ui.io :as io]))

(defrecord MockIO [value output]
  io/IO
  (user-input [x] @value)
  (display [x message] (reset! output message))
  (clears [x] "clear called")
  (flushes [x] "flush called"))

(defn mock-value-output
  [value output]
  (->MockIO (atom value) (atom output)))

(defn create-mock-io [value output]
  (map->MockIO {:value value :output output}))
