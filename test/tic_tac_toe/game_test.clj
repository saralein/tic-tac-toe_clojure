(ns tic-tac-toe.game-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.game :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-move (assoc board 4 "X"))

(def test-io (io/mock-value-output "5" ""))
(def test-ui (ui/create-ui test-io))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(deftest requests-move-and-get-spot-back
  (testing "asks for a move and returns a decremented integer"
    (is (= 4 (request-move test-ui test-human board)))))

(deftest takes-turn-on-board
  (testing "gets a move form the user and adds to board"
    (is (= board-move (take-turn* test-ui test-human board)))))
