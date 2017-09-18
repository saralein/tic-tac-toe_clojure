(ns tic-tac-toe.referee-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.referee :refer :all]))

(deftest converts-move-to-integer
  (testing "returns an integer if valid move"
    (is (= 2 (convert-to-int "2")))))

(deftest converts-move-to-spot-index
  (testing "returns a decremented integer"
    (is (= 1 (convert-to-spot 2)))))

(deftest verifies-move-is-integer
  (testing "translates a move to a spot"
    (is (= 1 (validate-move "2")))))
