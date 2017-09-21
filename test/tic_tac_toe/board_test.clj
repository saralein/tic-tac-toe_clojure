(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.board :refer :all]))

(def board [" " " " " " " " " " " " " " " " " "])
(def board-full (vec (repeat 9 "X")))
(def board-move [" " " " " " " " "X" " " " " " " " "])

(def player (human/create-human-player "X"))

(deftest making-a-new-board
  (testing "creates an empty board based on size"
    (is (= board (make 3)))))

(deftest evaluates-if-spot-empty
  (testing "returns true if spot is empty"
    (is (= true (is-empty? " "))))
  (testing "returns false if spot is not empty"
    (is (= false (is-empty? "X")))))

(deftest checks-if-board-is-full
  (testing "returns false when board is not full"
    (is (= false (full? board)))))
  (testing "returns true when board is full"
    (is (= true (full? board-full))))

(deftest adds-move-to-board
  (testing "it correctly adds move to board"
    (is (= board-move (add-move 4 board player)))))
