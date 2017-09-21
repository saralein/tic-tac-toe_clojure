(ns tic-tac-toe.setup-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.player :as player]
        [tic-tac-toe.setup :refer :all]))

(deftest creates-correct-players
  (testing "correct player types are created"
    (let [[player1 player2] (setup-players)]
      (is (= tic_tac_toe.human.Human (class player1)))
      (is (= tic_tac_toe.computer.Computer (class player2))))))
