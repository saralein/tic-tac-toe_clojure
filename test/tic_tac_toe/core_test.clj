(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.mocks.mock-serializer :as serializer]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]
            [tic-tac-toe.core :refer :all]))

(def board-full (vec (repeat 9 "X")))
(def divider "\n--- + --- + ---\n")
(def row " X  |  X  |  X ")
(def board-string (str "\n" row divider row divider row "\n\n"))
(def game-over "Game over. X wins!\n")
(def gameover-prompt (str board-string game-over))

(def test-io (io/mock-value-output "4" ""))
(def test-ui (ui/create-ui test-io))
(def test-serializer (serializer/create-mock-serializer))
(def test-writer (writer/create-writer test-serializer))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def game {:ui test-ui :writer test-writer :board board-full
           :current test-computer :opponent test-human})

(deftest handles-game-over
  (testing "ends game if board is full"
    (is (= gameover-prompt (play game)))))
