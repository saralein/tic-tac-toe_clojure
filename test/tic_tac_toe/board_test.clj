(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]))

(def new-3x3 [" " " " " "
              " " " " " "
              " " " " " "])

(def full-3x3
  (vec (repeat 9 'X')))
(def move-added [" " " " " "
                 " " "X" " "
                 " " " " " "])
(def player {:type :human :token "X"})

(deftest making-a-new-board
  (testing "creates an empty board based on size"
    (is (= new-3x3 (make 3)))))

(deftest evaluates-an-empty-spot
  (testing "returns true if spot is empty"
    (is (= true (is-empty? " ")))))

(deftest evaluates-a-full-spot
  (testing "returns false if spot is not empty"
    (is (= false (is-empty? "X")))))

(deftest checks-if-empty-board-is-full
  (testing "returns false when board is not full"
    (is (= false (full? new-3x3)))))

(deftest checks-if-full-board-is-full
  (testing "returns true when board is full"
    (is (= true (full? full-3x3)))))

(deftest adds-move-to-board
  (testing "it correctly adds move to board"
    (is (= move-added (add-move player new-3x3 4)))))
