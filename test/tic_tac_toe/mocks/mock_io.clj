(ns tic-tac-toe.mocks.mock-io
  (:require [tic-tac-toe.io :as io]))

(defrecord MockIO [value]
  io/IO
  (user-input [x] value)
  (display [x message] message)
  (clear-io [x] "clear called"))

(defn create-mock-io [value]
  (map->MockIO {:value value}))
