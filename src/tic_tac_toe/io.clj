(ns tic-tac-toe.io)

(defprotocol IO
  (user-input [this])
  (display [this message])
  (clears [this])
  (flushes [this]))

(defrecord ConsoleIO []
  IO
  (user-input [this] (.flushes this) (read-line))
  (display [this message] (print message) (.flushes this))
  (clears [this] (print (format "\033c\033[3J"))(.display this ""))
  (flushes [this] (flush)))

(defn create-console-io []
  (map->ConsoleIO {}))
