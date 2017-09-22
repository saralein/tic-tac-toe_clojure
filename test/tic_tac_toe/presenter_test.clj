(ns tic-tac-toe.user-interface-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.mocks.mock-io :as io]
        [tic-tac-toe.user-interface :refer :all]))

(def divider "\n--- + --- + ---\n")
(def divider-4x4 "\n--- + --- + --- + ---\n")
(def row "    |     |    ")
(def board [" " " " " " " " " " " " " " " " " "])
(def board-string (str row divider row divider row "\n\n"))

(deftest creates-divider-for-board-display
(testing "returns divider of correct length"
  (is (= divider (row-divider 3)))
  (is (= divider-4x4 (row-divider 4)))))

(deftest appends-spot-correctly
(testing "returns correct string for spot"
  (is (= " | " (append-spot 0 board)))
  (is (= divider (append-spot 2 board)))
  (is (= "\n\n" (append-spot 8 board)))))

(deftest creates-board-string
(testing "creates and returns correct board string"
  (is (= board-string (create-string [] board)))))
