(ns tic-tac-toe.validation.move-validator-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.computer :as computer]
        [tic-tac-toe.human :as human]
        [tic-tac-toe.read-write.writer :as writer]
        [tic-tac-toe.validation.move-validator :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-move ["O" 'empty 'empty "X" 'empty 'empty "X" 'empty "O"])
(def range-error "Not a number between 1-9.\n\n")

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def test-writer (create-writer))
(def game {:board board :current test-human :opponent test-computer})
(def game-move {:board board-move :current test-human :opponent test-computer})

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
