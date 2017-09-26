(ns tic-tac-toe.announcer-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.announcer :refer :all]))

(def p1-turn "\nPlayer 1's turn.\n")
(def p2-turn "\nPlayer 2's turn.\n")
(def move-prompt "Please pick a spot from 1-9: ")
(def board [" " " " " " " " " " " " " " " " " "])

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O"))

(deftest announce-player-turns
  (testing "announces current player for turns"
    (is (= [p1-turn] (announce-turn test-human [])))
    (is (= [p2-turn] (announce-turn test-computer [])))))

(deftest returns-move-prompt
  (testing "correctly formats and returns move prompt"
    (is (= move-prompt (move-request board)))))

(deftest returns-gameover-prompt
  (testing "correctly formats and returns game over prompt"
    (is (= ["Game over.\n"] (gameover [])))))
