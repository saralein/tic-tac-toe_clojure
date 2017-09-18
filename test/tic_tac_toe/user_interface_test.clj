(ns tic-tac-toe.user-interface-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.io :as io]
        [tic-tac-toe.user-interface :refer :all]))

(def move-string "Please pick a spot from 1-9: ")
(def user-input "message")
(def divider-3x3 "\n--- + --- + ---\n")
(def divider-4x4 "\n--- + --- + --- + ---\n")
(def row-3x3 "    |     |    ")
(def board-3x3 [" " " " " "
                " " " " " "
                " " " " " "])
(def board-string-3x3 (str row-3x3 divider-3x3 row-3x3 divider-3x3 row-3x3 "\n\n"))
(def clear-string (format "\033[2J"))

(deftest requesting-move
  (testing "it requests move range based on size"
    (is (= [move-string] (request-move [] board-3x3)))))

(deftest passes-message-to-io
  (testing "it passes same message to io"
    (is (= move-string (with-out-str (display-message [move-string]))))))

(deftest input-from-user
  (testing "returns input form the user"
    (with-in-str user-input
      (is (= user-input (get-input))))))

(deftest creates-divider-for-board-display
  (testing "returns divider of correct length"
    (is (= divider-3x3 (row-divider 3)))
    (is (= divider-4x4 (row-divider 4)))))

(deftest appends-spot-correctly
  (testing "returns correct string for spot"
    (is (= " | " (append-spot 0 board-3x3)))
    (is (= divider-3x3 (append-spot 2 board-3x3)))
    (is (= "\n\n" (append-spot 8 board-3x3)))))

(deftest creates-board-string
  (testing "creates and returns correct board string"
    (is (= [board-string-3x3] (board-string [] board-3x3)))))

(deftest clears-display
  (testing "adds string code to message which clears display"
    (is (= [clear-string] (clear [])))))

(deftest announces-game-over-with-last-board
  (testing "displays boards and game over message"
    (is (= (str clear-string board-string-3x3 "Game over.\n") (with-out-str (game-over board-3x3))))))
