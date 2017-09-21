(ns tic-tac-toe.player-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.player :refer :all]))

(def board [" " " " " " " " " " " " " " " " " "])
(def board-partial ["X" "X" "O" "X" "O" "X" " " "O" " "])
(def test-io (io/mock-value-output "4" ""))
(def test-ui (ui/create-ui test-io))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O"))

(deftest gets-move-from-player
  (testing "asks for move from human player and returns choice"
    (is (= "4" (pick-move test-human test-ui board))))
  (testing "computer picks move 1 on empty board"
      (is (= 0 (pick-move test-computer test-ui board))))
  (testing "computer picks spot 7 for partial board"
    (is (= 6 (pick-move test-computer test-ui board-partial)))))
