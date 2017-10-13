(ns tic-tac-toe.read-write.writer-test
  (:use [clojure.java.io :as fs])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.players.computer :as computer]
            [tic-tac-toe.players.human :as human]
            [tic-tac-toe.read-write.reader :as reader]
            [tic-tac-toe.mocks.mock-timestamper :as timestamper]
            [tic-tac-toe.read-write.writer :refer :all]))

(def board ["X" 'empty 'empty 'empty "O" 'empty 'empty 'empty 'empty])
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def test-timestamper (timestamper/create-mock-timestamper))
(def test-writer (create-writer test-timestamper))
(def test-reader (reader/create-reader test-timestamper))
(def game {:id 1 :name "peaches" :board board
           :current test-human :opponent test-computer
           :updated "20001031"})
(def game-with-diff (assoc game :time-diff "3"))

(deftest adds-game-state-to-save
  (testing "combines game pieces into hashmap of game state"
    (is (= game (bundle-state test-writer game)))))

(deftest saves-game-data-to-file-system
  (save-game test-writer "test/saves" "1" game)
  (testing "creates needed directories if they do not exist"
    (is (.isDirectory (fs/file "test/saves"))))
  (testing "creates files in given directory"
    (is (.exists (fs/file "test/saves/1.edn"))))
  (testing "writes correct data to file specified"
    (is (= game-with-diff (reader/load-game test-reader "test/saves" "1")))))
