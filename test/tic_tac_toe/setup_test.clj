(ns tic-tac-toe.setup-test
  (:use [clojure.test :refer :all]
        [clojure.java.io :only [delete-file]]
        [tic-tac-toe.mocks.mock-serializer :as mock-serializer]
        [tic-tac-toe.player :as player]
        [tic-tac-toe.read-write.reader :as reader]
        [tic-tac-toe.setup :refer :all]))

(def board ["X", 'empty, 'empty, 'empty,"O", 'empty, 'empty, 'empty, 'empty])
(def test-serializer (mock-serializer/create-mock-serializer))
(def test-reader (create-reader test-serializer))

(deftest sets-up-game-pieces
  (testing "returns hash with board and players"
    (let [game (setup-game "test/saves" "test")]
      (is (= tic_tac_toe.user_interface.ConsoleUI (class (:ui game))))
      (is (= tic_tac_toe.read_write.reader.FSReader (class (:reader game))))
      (is (= tic_tac_toe.read_write.writer.FSWriter (class (:writer game))))
      (is (= tic_tac_toe.human.Human (class (:current game))))
      (is (= tic_tac_toe.computer.Computer (class (:opponent game))))
      (is (= (:board game) board)))))
