(ns tic-tac-toe.user-interface-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.mocks.mock-io :as io]
        [tic-tac-toe.user-interface :refer :all]))

(def move-prompt "Please pick a spot from 1-9: ")
(def board [" " " " " " " " " " " " " " " " " "])
(def divider "\n--- + --- + ---\n")
(def row "    |     |    ")
(def board-string (str row divider row divider row "\n\n"))
(def full-prompt (str board-string move-prompt))
(def gameover-prompt (str board-string "Game over.\n"))

(def test-io (io/create-mock-io "4"))
(def test-ui (create-ui test-io))

(deftest displays-a-message
  (testing "receives a message and displays it"
    (is (= "Hello" (update-display test-ui "Hello")))))

(deftest gets-input-from-user
  (testing "gets input from user"
    (is (= "4" (get-input test-ui)))))

(deftest clears-display
  (testing "calls clear in io"
    (is (= "clear called" (clear-ui test-ui)))))

(deftest displays-full-move-prompt
  (testing "retrieves board and move prompt and displays"
    (is (= full-prompt (prompt-move test-ui board)))))

(deftest displays-full-gameover-prompt
  (testing "retrieves board and game over prompt and displays"
    (is (= gameover-prompt (prompt-gameover test-ui board)))))
