(ns tic-tac-toe.read-write.reader-test
  (:use [clojure.java.io :as fs])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.mocks.mock-serializer :as mock-serializer]
            [tic-tac-toe.read-write.serializer :as serializer]
            [tic-tac-toe.read-write.reader :refer :all]))

(def unformatted ["X" "empty" "empty" "empty" "O" "empty" "empty" "empty" "empty"])
(def formatted ["X" 'empty 'empty 'empty "O" 'empty 'empty 'empty 'empty])
(def human-player {:name "Player 1" :type "human" :token "X"})
(def computer-player {:name "Player 2" :type "computer" :token "O" :opponent "X"})

(def test-serializer (mock-serializer/create-mock-serializer))
(def test-reader (create-reader test-serializer))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(def game-state {:opponent test-computer :current test-human :board formatted})

(deftest loads-formatted-game-state
  (testing "reads from file and returns formatted game state"
    (is (= game-state (load-game test-reader "test/saves" "test" (fn []) (fn []))))))

(deftest checks-if-file-exists
  (testing "returns false if file does not exists"
    (is (= false (save-exists? test-reader "test/saves" "nope"))))
  (testing "returns true if file exists"
    (is (= true (save-exists? test-reader "test/saves" "test")))))
