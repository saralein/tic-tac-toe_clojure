(ns tic-tac-toe.referee-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.computer :as computer]
        [tic-tac-toe.human :as human]
        [tic-tac-toe.referee :refer :all]))

(def board [" " " " " " " " " " " " " " " " " "])
(def board-draw ["X" "O" "X" "O" "X" "O" "O" "X" "O"])
(def board-won ["O" " " "X" "X" "O" " " "X" " " "O"])
(def patterns [["X" "X" "X"] ["X" "O" " "] [" " " " " "]])
(def patterns-distinct [["X"] ["X" "O" " "] [" "]])
(def patterns-single ["X"])

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O"))

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
    (is (= false (matching-pattern? "X" [" " " " " "])))
    (is (= false (matching-pattern? "X" ["O" "O" "O"])))
    (is (= false (matching-pattern? "X" ["X" " " "O"])))
    (is (= true (matching-pattern? "X" ["X" "X" "X"])))))

(deftest returns-winner-status-boolean
  (testing "returns false when the game has no winner"
    (is (= false (winner? board)))
    (is (= false (winner? board-draw))))
  (testing "returns true when the game has a winner"
    (is (= true (winner? board-won)))))

(deftest gets-status-of-win
  (testing "returns win info if game has winner"
    (is (= {:winner test-computer}) (get-status board-won test-computer)))
  (testing "returns draw info otherwise"
    (is (= {:winner :none} (get-status board-draw test-computer)))))

(deftest verfifies-if-game-over
  (testing "returns false for ongoing game"
    (is (= false (game-over? board))))
  (testing "returns true for draw"
    (is (= true (game-over? board-draw))))
  (testing "returns true when won"
    (is (= true (game-over? board-won)))))
