(ns tic-tac-toe.game-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.computer :as computer]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.read-write.writer :as writer]
            [tic-tac-toe.game :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-move (assoc board 4 "X"))

(def test-io-move (io/mock-value-output "5" ""))
(def test-ui-move (ui/create-ui test-io-move #("exit")))
(def test-io-option (io/mock-value-output "?" ""))
(def test-ui-option (ui/create-ui test-io-option #("exit")))
(def test-writer (writer/create-writer))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))
(def prompt-func-move (partial ui/prompt-move test-ui-move board test-human))
(def prompt-func-option (partial ui/prompt-move test-ui-option board test-human))
(def options (hash-map :? (fn [game] "called")))

(def game {:options options :ui test-ui-move :writer test-writer
           :current test-human :opponent test-computer :board board})
(def updated-game {:options options :ui test-ui-move :writer test-writer
                   :current test-computer :opponent test-human :board board-move})
(def game-option {:options options :ui test-ui-option :writer test-writer
                  :current test-human :opponent test-computer :board board})

(deftest requests-move-and-gets-response
  (testing "asks for a move and returns a decremented integer"
    (is (= 4 (request-move game prompt-func-move))))
  (testing "calls options function if input is game option"
    (is (= "called" (request-move game-option prompt-func-option)))))

(deftest takes-turn-on-board
  (testing "gets a move form the user and adds to board"
    (is (= updated-game (take-turn* game)))))
