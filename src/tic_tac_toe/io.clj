(ns tic-tac-toe.io)

(defprotocol IO
  (user-input [x])
  (display [x message])
  (clear-io [x]))

(defrecord ConsoleIO []
  IO
  (user-input [x] (flush)(read-line))
  (display [x message] (print message))
  (clear-io [x] (print (format "\033[2J"))))

(defn create-console-io []
  (map->ConsoleIO {}))
