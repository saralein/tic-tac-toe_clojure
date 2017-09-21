(ns tic-tac-toe.referee-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.computer :as computer]
        [tic-tac-toe.human :as human]
        [tic-tac-toe.referee :refer :all]))

(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O"))

(deftest converts-move-to-spot
  (testing "returns a decremented integer if given string"
    (is (= 1 (convert-to-spot "2"))))
  (testing "returns integer if given integer"
    (is (= 2 (convert-to-spot 2)))))

(deftest verifies-player-move
  (testing "translates human move to a spot"
    (is (= 1 (validate-move test-human "2"))))
  (testing "translates computer move to a spot"
    (is (= 2 (validate-move test-computer 2)))))
