(ns tic-tac-toe.messenger-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.messenger :refer :all]))

(def move-prompt ["Please pick a spot from 1-9: "])
(def board [" " " " " " " " " " " " " " " " " "])

(deftest returns-formatted-move-prompt
  (testing "correctly formats and returns move prompt"
    (is (= move-prompt (move-request board [])))))

(deftest returns-gameover-prompt
  (testing "correctly formats and returns game over prompt"
    (is (= ["Game over.\n"] (gameover [])))))
