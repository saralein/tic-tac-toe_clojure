(ns tic-tac-toe.setup-test
  (:use [clojure.test :refer :all]
        [clojure.java.io :only [delete-file]]
        [tic-tac-toe.computer :as computer]
        [tic-tac-toe.human :as human]
        [tic-tac-toe.io :as io]
        [tic-tac-toe.mocks.mock-io :as mock-io]
        [tic-tac-toe.mocks.mock-ui :as mock-ui]
        [tic-tac-toe.player :as player]
        [tic-tac-toe.read-write.reader :as reader]
        [tic-tac-toe.read-write.serializer :as serializer]
        [tic-tac-toe.read-write.writer :as writer]
        [tic-tac-toe.user-interface :as ui]
        [tic-tac-toe.setup :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-moves ["X", 'empty, 'empty, 'empty,"O", 'empty, 'empty, 'empty, 'empty])

(def game-io (io/create-console-io))
(def game-ui (ui/create-ui game-io))
(def new-game-io (mock-io/mock-value-output "1" ""))
(def new-game-ui (mock-ui/create-mock-ui new-game-io))
(def saved-game-io (mock-io/mock-value-output "2" ""))
(def saved-game-ui (mock-ui/create-mock-ui saved-game-io))
(def test-serializer (serializer/create-serializer))
(def test-reader (create-reader test-serializer))
(def test-writer (create-writer test-serializer))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(def new-game-utils (hash-map :ui new-game-ui :reader test-reader))
(def saved-game-utils (hash-map :ui saved-game-ui :reader test-reader))

(deftest sets-up-game-utils
  (testing "returns hash with correct game utils"
    (let [utils (setup-utils)]
      (is (= game-ui (:ui utils)))
      (is (= test-reader (:reader utils)))
      (is (= test-writer (:writer utils))))))

(deftest sets-up-game
  (testing "returns hash with new game board and players"
    (let [game (setup-game new-game-utils "test/saves" "test")]
      (is (= "test/saves" (:dir game)))
      (is (= "test" (:file game)))
      (is (= test-human (:current game)))
      (is (= test-computer (:opponent game)))
      (is (= (:board game) board))))
  (testing "returns hash with saved game board and players"
    (let [game (setup-game saved-game-utils "test/saves" "test")]
      (is (= "test/saves" (:dir game)))
      (is (= "test" (:file game)))
      (is (= test-human (:current game)))
      (is (= test-computer (:opponent game)))
      (is (= (:board game) board-moves)))))
