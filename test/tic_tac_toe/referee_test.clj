(ns tic-tac-toe.referee-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.players.computer :as computer]
        [tic-tac-toe.players.human :as human]
        [tic-tac-toe.mocks.mock-io :as io]
        [tic-tac-toe.mocks.mock-ui :as ui]
        [tic-tac-toe.mocks.mock-timestamper :as timestamper]
        [tic-tac-toe.read-write.writer :as writer]
        [tic-tac-toe.referee :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-draw ["X" "O" "X" "O" "X" "O" "O" "X" "O"])
(def board-won ["O" 'empty "X" "X" "O" 'empty "X" 'empty "O"])

(def test-io (io/mock-value-output "4" ""))
(def test-ui (ui/create-mock-ui test-io #("exit")))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def test-timestamper (timestamper/create-mock-timestamper))
(def test-writer (create-writer test-timestamper))
(def game {:board board :current test-human :opponent test-computer})

(defn request-test-move
  [game prompt-func]
  "called")

(deftest verifies-player-move
  (testing "translates human move to a spot"
    (is (= 1 (validate-move request-test-move game "2"))))
  (testing "translates computer move to a spot"
    (is (= 2 (validate-move request-test-move game 2))))
  (testing "given function will be called if move is not valid"
    (is (= "called" (validate-move request-test-move game "100w")))))

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
