(ns tic-tac-toe.player-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.player :refer :all]))

(deftest gets-move-from-player
  (testing "asks for move from player and returns choice as vector index"
    (with-redefs [ui/get-input (constantly "2")]
      (is (= 1 (pick-move))))))
