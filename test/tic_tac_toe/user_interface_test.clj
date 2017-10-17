(ns tic-tac-toe.user-interface-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.human :as human]
        [tic-tac-toe.mocks.mock-io :as io]
        [tic-tac-toe.user-interface :refer :all]))

(def p1-turn "\nPlayer 1's turn.\n")
(def board (vec (repeat 9 'empty)))
(def divider "\n--- + --- + ---\n")
(def row "    |     |    ")
(def board-string (str "\n" row divider row divider row "\n\n"))
(def full-prompt (str p1-turn board-string))
(def gameover-prompt (str board-string "Game over. It's a draw.\n"))
(defn exit-called [] "exit requested")


(def test-io (io/mock-value-output "4" ""))
(def test-ui (create-ui test-io exit-called))
(def test-player (human/create-human-player "X"))

(deftest displays-a-message
  (update-display test-ui "Hello")
  (testing "receives a message and displays it"
    (is (= "Hello" @(:output test-io)))))

(deftest checks-input-for-esc
  (testing "returns input if not nil"
    (is (= "4" (quit? test-ui "4"))))
  (testing "returns input if nil string"
    (is (= "nil" (quit? test-ui "nil"))))
  (testing "calls exit when input is nil"
    (is (= "exit requested" (quit? test-ui nil)))))

(deftest calls-exit-methods
  (testing "exiting called exit method"
    (is (= "exit requested" (exit test-ui)))))

(deftest gets-input-from-user
  (testing "gets input from user"
    (is (= "4" (get-input test-ui)))))

(deftest clears-display
  (testing "calls clear in io"
    (is (= "clear called" (clear-src test-ui)))))

(deftest displays-full-move-prompt
  (testing "retrieves board and move prompt and displays"
    (prompt-move test-ui board test-player)
    (is (= full-prompt @(:output test-io)))))

(deftest displays-full-gameover-prompt
  (testing "retrieves board and game over prompt and displays"
    (is (= gameover-prompt (prompt-gameover test-ui board 'draw)))))
