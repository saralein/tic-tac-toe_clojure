(ns tic-tac-toe.presenter-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.mocks.mock-io :as io]
        [tic-tac-toe.presenter :refer :all]))

(def divider "\n--- + --- + ---\n")
(def divider-4x4 "\n--- + --- + --- + ---\n")
(def row "    |     |    ")
(def board (vec (repeat 9 'empty)))
(def board-string (str "\n" row divider row divider row "\n\n"))

(deftest creates-divider-for-board-display
  (testing "returns divider of correct length"
    (is (= divider (add-divider 3)))
    (is (= divider-4x4 (add-divider 4)))))

(deftest appends-spot-correctly
  (testing "returns correct string for spot"
    (is (= " | " (append-spot 0 board)))
    (is (= divider (append-spot 2 board)))
    (is (= "\n\n" (append-spot 8 board)))))

(deftest returns-correct-token-for-spot
  (testing "returns ' ' if spot is empty"
    (is (= " " (determine-token 'empty))))
  (testing "returns actual token if spot is not empty"
    (is (= "X" (determine-token "X")))))

(deftest creates-board-string
  (testing "creates and returns correct board string"
    (is (= board-string (create-string board)))))
