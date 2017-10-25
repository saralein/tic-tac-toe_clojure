(ns tic-tac-toe.read-write.reader-test
  (:use [clojure.java.io :as fs])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.players.computer :as computer]
            [tic-tac-toe.players.human :as human]
            [tic-tac-toe.mocks.mock-timestamper :as timestamper]
            [tic-tac-toe.read-write.reader :refer :all]))

(def unformatted ["X" "empty" "empty" "empty" "O" "empty" "empty" "empty" "empty"])
(def formatted ["X" 'empty 'empty 'empty "O" 'empty 'empty 'empty 'empty])
(def human-player {:name "Player 1" :type "human" :token "X"})
(def computer-player {:name "Player 2" :type "computer" :token "O" :opponent "X"})

(def test-timestamper (timestamper/create-mock-timestamper))
(def test-reader (create-reader test-timestamper))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(def game-state {:id 1 :name "peaches" :opponent test-computer
                 :current test-human :board formatted})
(def game-state-stamped (assoc game-state :updated "20001031"))
(def log-1 (assoc game-state :updated "20001031" :time-diff "3"))
(def log-2 {:id 2 :name "green" :opponent test-computer
            :current test-human :board formatted
            :updated "20001031" :time-diff "3"})
(def log-3 {:id 3})

(def logs (list log-1 log-2 log-3))

(deftest adds-time-differential-to-game-load
  (testing "adds time differential (no. days) between last date saved and current date"
    (is (= log-1 (add-time-differential test-reader "test/saves" "1" game-state-stamped)))))

(deftest loads-formatted-game-state
  (testing "reads from file and returns formatted game state"
    (is (= log-1 (load-game test-reader "test/saves" "1")))))

(deftest checks-if-file-exists
  (testing "returns false if file does not exists"
    (is (= false (save-exists? test-reader "test/saves" "nope"))))
  (testing "returns true if file exists"
    (is (= true (save-exists? test-reader "test/saves" "1")))))

(deftest reads-logs-for-all-save-slots
  (testing "returns list of all save logs"
    (is (= logs (read-logs test-reader "test/saves")))))
