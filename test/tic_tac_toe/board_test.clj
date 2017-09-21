(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.board :refer :all]))

(def board ['empty 'empty 'empty 'empty 'empty 'empty 'empty 'empty 'empty])
(def board-full (vec (repeat 9 "X")))
(def board-move ['empty 'empty 'empty 'empty "X" 'empty 'empty 'empty 'empty])
(def board-nums [0 1 2 3 4 5 6 7 8])
(def rows [[0 1 2] [3 4 5] [6 7 8]])
(def columns [[0 3 6] [1 4 7] [2 5 8]])
(def down-indices [0 1 2])
(def down-diagonal [0 4 8])
(def up-indices [2 1 0])
(def up-diagonal [2 4 6])
(def patterns [down-diagonal up-diagonal
                  [0 1 2] [3 4 5] [6 7 8]
                  [0 3 6] [1 4 7] [2 5 8]])

(def player (human/create-human-player "X"))

(def player (human/create-human-player "X"))

(def player (human/create-human-player "X"))

(deftest making-a-new-board
  (testing "creates an empty board based on size"
    (is (= board (make 3)))))

(deftest evaluates-if-spot-empty
  (testing "returns true if spot is empty"
    (is (= true (is-empty? 'empty))))
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

(deftest divides-board-into-rows
  (testing "divides the board into rows of correct size"
    (is (= rows (get-rows board-nums 3)))))

(deftest tranposes-rows-into-columns
  (testing "converts rows into columns correctly"
    (is (= columns (get-columns rows)))))

(deftest returns-cell-locations-for-diagonals
  (testing "returns same cells as given for downward diagonal"
    (is (= down-indices (modify-cells [0 1 2] :down))))
  (testing "returns same cells as given for downward diagonal"
    (is (= up-indices (modify-cells [0 1 2] :up)))))

(deftest gets-diagonals
  (testing "gets left diagonal from rows"
    (is (= down-diagonal (get-diagonal rows 3 :down))))
  (testing "gets right diagonal from rows"
    (is (= up-diagonal (get-diagonal rows 3 :up)))))

(deftest gets-board-patterns
  (testing "gets patterns for all current board rows, columns, diagonals"
    (is (= patterns (give-patterns board-nums)))))
