(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.core :refer :all]))

(def board-full ["X" "X" "X" "X" "X" "X" "X" "X" "X"])
(def divider "\n--- + --- + ---\n")
(def row " X  |  X  |  X ")
(def board-string (str row divider row divider row "\n\n"))
(def game-over "Game over.\n")
(def gameover-prompt (str board-string game-over))

(def test-io (io/create-mock-io "4"))
(def test-ui (ui/create-ui test-io))
(def test-player (player/create-player))

(deftest handles-game-over
  (testing "ends game if board is full"
    (is (= gameover-prompt (play test-ui test-player board-full)))))
