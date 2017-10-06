(ns tic-tac-toe.mocks.mock-serializer
  (:require [clojure.data.json :as json]
            [tic-tac-toe.read-write.serializer :as serializer]))

(defrecord MockSerializer []
  serializer/Serializer
  (serialize [this data] "{\"board\":[\"X\",\"empty\",\"empty\",\"empty\",\"O\",\"empty\",\"empty\",\"empty\",\"empty\"],\"opponent\":{\"name\":\"Player 2\",\"type\":\"computer\",\"token\":\"O\",\"opponent\":\"X\"},\"current\":{\"name\":\"Player 1\",\"type\":\"human\",\"token\":\"X\"}}")

  (deserialize [this data create-human create-comp] {:opponent #tic_tac_toe.computer.Computer{:name "Player 2" :type :computer :token "O" :opponent "X"} :current #tic_tac_toe.human.Human{:name "Player 1" :type :human :token "X"} :board ["X" 'empty 'empty 'empty "O" 'empty 'empty 'empty 'empty]}))

(defn create-mock-serializer []
  (map->MockSerializer {}))
