(ns tic-tac-toe.player-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.player :refer :all]))

(def empty-board [" " " " " "
                  " " " " " "
                  " " " " " "])
(def partial-board ["X" "X" "O"
                    "X" "O" "X"
                    " " "O" " "])
(def human-player {:type :human})
(def computer-player {:type :computer})

(deftest gets-move-from-player
  (testing "asks for move from player and returns choice as vector index"
    (with-redefs [ui/get-input (constantly "2")]
      (is (= 1 (move-input))))))

(deftest gets-move-from-human-player
  (testing "calls human player pick move"
    (with-redefs [move-input (constantly "called")]
      (is (= "called" (pick-move human-player empty-board))))))

(deftest gets-move-from-computer-player
  (testing "calls computer player pick move"
      (is (= 0 (pick-move computer-player empty-board)))))

(deftest picks-index-zero-on-empty-board
  (testing "returns 0 as move for empty board"
    (is (= 0 (pick-next-open-spot empty-board)))))

(deftest picks-index-six-on-partial-board
  (testing "returns 6 as move for partial board"
    (is (= 6 (pick-next-open-spot partial-board)))))
