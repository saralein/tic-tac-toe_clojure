(ns tic-tac-toe.validation.move-validator-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.validation.move-validator
         :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-move ["O" 'empty 'empty "X" 'empty 'empty "X" 'empty "O"])
(def range-error "Not a number between 1-9.\n\n")

(deftest checks-validity-of-spot
  (testing "returns correct hash for integer"
    (is (= {:valid true :data 4} (check-spot board 4))))
  (testing "returns correct hash for string number when in range and open spot"
    (is (= {:valid true :data 3} (check-spot board "4"))))
  (testing "returns correct hash for string number when out of range spot"
    (is (= {:valid false :data range-error} (check-spot board-move "10"))))
  (testing "returns correct hash for string number when unavailable spot"
    (is (= {:valid false :data spot-taken} (check-spot board-move "4"))))
  (testing "returns correct hash for string when invalid spot"
    (is (= {:valid false :data invalid-spot} (check-spot board-move "w")))))

