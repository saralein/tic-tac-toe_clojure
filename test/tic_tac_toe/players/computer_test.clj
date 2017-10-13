(ns tic-tac-toe.players.computer-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.players.human :as human]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.players.player :as player]
            [tic-tac-toe.mocks.mock-ui :as ui]
            [tic-tac-toe.players.computer :refer :all]))

(def board (vec (repeat 9 'empty)))
(def corner-taken (assoc board 0 "X"))
(def board-draw ["X" "O" "X" "O" "X" "O" "O" "X" "O"])
(def board-computer ["O" 'empty "X" "X" "O" 'empty "X" 'empty "O"])
(def board-human ["X" 'empty "O" "O" "X" 'empty "O" 'empty "X"])
(def board-split ['empty 'empty "X" 'empty "O" 'empty "X" 'empty 'empty])
(def future-1st-moves [["X" 'empty 'empty] ['empty "X" 'empty] ['empty 'empty "X"]])
(def future-2nd-moves [["X" "O" 'empty] ["X" 'empty "O"]])
(def future-draw ["X" "O" "X" "O" "X" "O" "O" "X" 'empty])
(def possible-win ["X" "X" 'empty "O" "O" 'empty "X" "X" "O"])
(def possible-block ["X" "O" 'empty "O" "X" 'empty 'empty 'empty 'empty])

(def test-io (io/mock-value-output "4" ""))
(def test-ui (ui/create-mock-ui test-io #("exit")))
(def test-human (human/create-human-player "X"))
(def test-computer (create-computer-player "O" "X"))

(deftest retrieves-future-boards
  (testing "returns vector with all possible next moves on board"
    (is (= future-1st-moves (project-boards ['empty 'empty 'empty] [0 1 2] "X")))
    (is (= future-2nd-moves (project-boards ["X" 'empty 'empty] [1 2] "O")))))

(deftest correctly-scores-final-board
  (testing "returns correct score for draw"
    (is (= 0 (score-board :draw "X" "O" 10))))
  (testing "returns correct score for current player at depth 0"
      (is (= 100 (score-board "O" "O" "X" 0))))
  (testing "returns correct score for opponent at depth zero"
      (is (= -100 (score-board "X" "O" "X" 0))))
  (testing "returns correct score for current player"
    (is (= 94 (score-board "O" "O" "X" 6))))
  (testing "returns correct score for opponent"
    (is (= -94 (score-board "X" "O" "X" 6)))))

(deftest generates-possible-scores
  (testing "returns list of possible scores"
    (is (= '(0) (generate-scores future-draw "O" "X" 9)))
    (is (= '(92 0) (generate-scores possible-win "X" "O" 7)))
    (is (= '(0 92) (generate-scores possible-win "O" "X" 7)))))

(deftest gets-max-from-possible-scores
  (testing "returns zero for a draw"
    (is (= 0 (negamax board-draw "X" "O" 10))))
  (testing "returns max score for projected boards"
    (is (= 92 (negamax possible-win "X" "O" 7)))))

(deftest picks-best-moves
  (testing "picks corner when available"
    (is (= 0 (player/pick-move test-computer test-ui board))))
  (testing "picks middle if corner is taken"
    (is (= 4 (player/pick-move test-computer test-ui corner-taken))))
  (testing "prevents a split board"
    (is (contains? [1 3 5 7] (player/pick-move test-computer test-ui board-split))))
  (testing "picks winning move"
    (is (= 5 (player/pick-move test-computer test-ui possible-win))))
  (testing "picks blocking move"
    (is (= 8 (player/pick-move test-computer test-ui possible-block)))))
