(ns tic-tac-toe.announcer-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.players.computer :as computer]
            [tic-tac-toe.players.human :as human]
            [tic-tac-toe.announcer :refer :all]))

(def p1-turn "Player 1's turn.\n")
(def p2-turn "Player 2's turn.\n")
(def move-prompt "Please pick a spot from 1-9 (enter \"?\" for help): ")
(def player-win-prompt "X wins!\n")
(def board (vec (repeat 9 'empty)))

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(deftest announce-player-turns
  (testing "announces current player for turns"
    (is (= [p1-turn] (announce-turn test-human [])))
    (is (= [p2-turn] (announce-turn test-computer [])))))

(deftest returns-move-prompt
  (testing "correctly formats and returns move prompt"
    (is (= move-prompt (move-request board)))))

(deftest returns-correct-gameover-prompt
  (testing "correctly formats and returns draw game over prompt"
    (is (= ["Game over. It's a draw.\n"] (gameover 'draw []))))
  (testing "correctly formats and returns winner game over prompt"
    (is (= ["Game over. X wins!\n"] (gameover "X" [])))))
