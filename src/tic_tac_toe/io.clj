(ns tic-tac-toe.io)

(defprotocol IO
  (user-input [this])
  (display [this message])
  (clear-io [this])
  (flush-io [this])
  (pause-io [this]))

(defrecord ConsoleIO []
  IO
  (user-input [this] (read-line))
  (display [this message] (print message))
  (clear-io [this] (print (format "\033[2J")))
  (flush-io [this] (flush))
  (pause-io [this] (Thread/sleep 1000)))

(defn create-console-io []
  (map->ConsoleIO {}))
