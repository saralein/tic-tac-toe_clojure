(ns tic-tac-toe.player-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.player :refer :all]))

(def test-io (io/create-mock-io "4"))
(def test-ui (ui/create-ui test-io))
(def test-player (create-player))

(deftest gets-move-from-player
  (testing "asks for move from player and returns choice"
    (is (= "4" (pick-move test-player test-ui)))))
