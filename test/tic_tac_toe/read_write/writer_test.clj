(ns tic-tac-toe.read-write.writer-test
  (:use [clojure.java.io :as fs])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.mocks.mock-serializer :as test-serializer]
            [tic-tac-toe.read-write.serializer :as serializer]
            [tic-tac-toe.read-write.writer :refer :all]))

(def board (vec (repeat 9 'empty)))

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def test-serializer (test-serializer/create-mock-serializer))
(def test-writer (create-writer test-serializer))
(def game {:board board :current test-human :opponent test-computer})

(deftest adds-game-state-to-json
  (testing "combines game pieces into hashmap of game state"
    (is (= game (bundle-state test-writer game)))))

(deftest saves-game-data-to-file-system
  (save-game test-writer "test/saves" "test" game)
  (testing "creates needed directories if they do not exist"
    (is (.isDirectory (fs/file "test/saves"))))
  (testing "creates files in given directory"
    (is (.exists (fs/file "test/saves/test.json"))))
  (testing "writes correct data to file specified"
    (is (= (serializer/serialize test-serializer {}) (slurp "test/saves/test.json")))))
