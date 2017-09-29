(ns tic-tac-toe.referee-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.computer :as computer]
        [tic-tac-toe.human :as human]
        [tic-tac-toe.referee :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-draw ["X" "O" "X" "O" "X" "O" "O" "X" "O"])
(def board-won ["O" 'empty "X" "X" "O" 'empty "X" 'empty "O"])
(def patterns [["X" "X" "X"] ["X" "O" 'empty] ['empty 'empty 'empty]])
(def patterns-distinct [["X"] ["X" "O" 'empty] ['empty]])
(def patterns-single ["X"])
(def board-matches ["X" 'empty "O" "O" "O" "O" 'empty 'empty 'empty])
(def pattern-matches [["O" "O" "O"] ['empty 'empty 'empty]])

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(deftest converts-move-to-spot
  (testing "returns a decremented integer if given string"
    (is (= 1 (convert-to-spot "2"))))
  (testing "returns integer if given integer"
    (is (= 2 (convert-to-spot 2)))))

(deftest verifies-player-move
  (testing "translates human move to a spot"
    (is (= 1 (validate-move test-human "2"))))
  (testing "translates computer move to a spot"
    (is (= 2 (validate-move test-computer 2)))))

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

(deftest gets-game-winner
  (testing "returns draw if no winning pattern"
    (is (= 'draw (get-winner board-draw))))
  (testing "returns winning token if there is a winning pattern"
    (is (= "O") (get-winner board-won))))

(deftest returns-winner-status-boolean
  (testing "returns false when the game has no winner"
    (is (= false (winner? board)))
    (is (= false (winner? board-draw))))
  (testing "returns true when the game has a winner"
    (is (= true (winner? board-won)))))

(deftest verfifies-if-game-over
  (testing "returns false for ongoing game"
    (is (= false (game-over? board))))
  (testing "returns true for draw"
    (is (= true (game-over? board-draw))))
  (testing "returns true when won"
    (is (= true (game-over? board-won)))))
