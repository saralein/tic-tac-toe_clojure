(ns tic-tac-toe.read-write.writer-test
  (:use [clojure.java.io :as fs])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.read-write.writer :refer :all]))

(def board ["X" 'empty 'empty 'empty "O" 'empty 'empty 'empty 'empty])
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def test-writer (create-writer))
(def test-reader (reader/create-reader))
(def game {:board board :current test-human :opponent test-computer})

(deftest adds-game-state-to-json
  (testing "combines game pieces into hashmap of game state"
    (is (= game (bundle-state test-writer game)))))

(deftest saves-game-data-to-file-system
  (save-game test-writer "test/saves" "test" game)
  (testing "creates needed directories if they do not exist"
    (is (.isDirectory (fs/file "test/saves"))))
  (testing "creates files in given directory"
    (is (.exists (fs/file "test/saves/test.edn"))))
  (testing "writes correct data to file specified"
    (is (= game (reader/load-game test-reader "test/saves" "test")))))
