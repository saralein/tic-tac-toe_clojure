(ns tic-tac-toe.game-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.game :refer :all]))

(def board [" " " " " " " " " " " " " " " " " "])
(def board-move [" " " " " " " " "X" " " " " " " " "])

(def test-io (io/create-mock-io "5"))
(def test-ui (ui/create-ui test-io))
(def test-player (player/create-player))

(deftest requests-move-and-get-spot-back
  (testing "asks for a move and returns a decremented integer"
    (is (= 4 (request-move test-ui test-player)))))

(deftest takes-turn-on-board
  (testing "gets a move form the user and adds to board"
    (is (= board-move (take-turn* test-ui test-player board)))))
