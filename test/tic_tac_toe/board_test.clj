(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]))

(def board [" " " " " " " " " " " " " " " " " "])
(def board-full (vec (repeat 9 "X")))
(def board-move [" " " " " " " " "X" " " " " " " " "])

(deftest making-a-new-board
  (testing "creates an empty board based on size"
    (is (= board (make 3)))))

(deftest evaluates-an-empty-spot
  (testing "returns true if spot is empty"
    (is (= true (is-empty? " ")))))

(deftest evaluates-a-full-spot
  (testing "returns false if spot is not empty"
    (is (= false (is-empty? "X")))))

(deftest checks-if-empty-board-is-full
  (testing "returns false when board is not full"
    (is (= false (full? board)))))

(deftest checks-if-full-board-is-full
  (testing "returns true when board is full"
    (is (= true (full? board-full)))))

(deftest adds-move-to-board
  (testing "it correctly adds move to board"
    (is (= board-move (add-move 4 board)))))
