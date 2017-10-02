(ns tic-tac-toe.validation.board-validator-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.validation.board-validator :refer :all]))

(def board-matches ["X" 'empty "O" "O" "O" "O" 'empty 'empty 'empty])
(def pattern-matches [["O" "O" "O"] ['empty 'empty 'empty]])

(deftest matches-single-token-patterns
  (testing "returns correct bool for single token pattern"
    (is (= false (matching-pattern? "X" [" "])))
    (is (= false (matching-pattern? "X" ["O"])))
    (is (= true (matching-pattern? "X" ["X"])))))

(deftest matches-three-token-patterns
  (testing "returns correct bool for three token pattern"
    (is (= true (matching-pattern? 'empty ['empty 'empty 'empty])))
    (is (= false (matching-pattern? "X" ["O" "O" "O"])))
    (is (= false (matching-pattern? "X" ["X" 'empty "O"])))
    (is (= true (matching-pattern? "X" ["X" "X" "X"])))))

(deftest gets-all-matching-patterns
  (testing "creates list of all matching patterns on board"
    (is (= pattern-matches (get-matching-patterns board-matches)))))

(deftest removes-empty-patterns
  (testing "returns list of only non-empty patterns"
    (is (= ["O" "O" "O"] (remove-empty-patterns pattern-matches)))))
