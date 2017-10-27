(ns tic-tac-toe.setup-test
  (:use [clojure.test :refer :all]
        [clojure.java.io :only [delete-file]]
        [tic-tac-toe.menu.menu-messages :only [messages]]
        [tic-tac-toe.players.computer :as computer]
        [tic-tac-toe.players.human :as human]
        [tic-tac-toe.ui.io :as io]
        [tic-tac-toe.mocks.mock-io :as mock-io]
        [tic-tac-toe.mocks.mock-ui :as mock-ui]
        [tic-tac-toe.players.player :as player]
        [tic-tac-toe.read-write.reader :as reader]
        [tic-tac-toe.read-write.timestamper :as timestamper]
        [tic-tac-toe.read-write.writer :as writer]
        [tic-tac-toe.ui.user-interface :as ui]
        [tic-tac-toe.setup :refer :all]))

(def board (vec (repeat 9 'empty)))
(def board-moves ["X", 'empty, 'empty, 'empty,"O", 'empty, 'empty, 'empty, 'empty])

(def game-io (io/create-console-io))
(def game-ui (ui/create-ui game-io #(System/exit 0)))
(def new-game-io (mock-io/mock-value-output "1" ""))
(def new-game-ui (mock-ui/create-mock-ui new-game-io #("exit")))
(def saved-game-io (mock-io/mock-value-output "2" ""))
(def saved-game-ui (mock-ui/create-mock-ui saved-game-io #("exit")))
(def qs-game-io (mock-io/mock-value-output "y" ""))
(def qs-game-ui (mock-ui/create-mock-ui qs-game-io #("exit")))
(def test-timestamper (timestamper/create-timestamper))
(def test-reader (create-reader test-timestamper))
(def test-writer (create-writer test-timestamper))
(def test-human (human/create-human-player "X"))
(def test-computer (computer/create-computer-player "O" "X"))

(def new-game-utils (merge (hash-map :ui new-game-ui :reader test-reader) messages))
(def saved-game-utils (merge (hash-map :ui saved-game-ui :reader test-reader) messages))
(def qs-utils (merge (hash-map :ui qs-game-ui :reader test-reader) messages))

(def qs-game {:id 0, :board board, :current test-human, :opponent test-computer})

(deftest sets-up-game-utils
  (testing "returns hash with correct game utils"
    (let [utils (setup-utils)]
      (is (= (class game-ui) (class (:ui utils))))
      (is (= test-reader (:reader utils)))
      (is (= test-writer (:writer utils))))))

(deftest sets-up-game
  (testing "returns hash with new game board and players"
    (let [game (setup-game new-game-utils "test/saves")]
      (is (= "test/saves" (:dir game)))
      (is (= test-human (:current game)))
      (is (= test-computer (:opponent game)))
      (is (= (:board game) board))))
  (testing "returns hash with saved game board and players"
    (let [game (setup-game saved-game-utils "test/saves")]
      (is (= "test/saves" (:dir game)))
      (is (= test-human (:current game)))
      (is (= test-computer (:opponent game)))
      (is (= (:board game) board-moves))))
  (testing "returns quick save game if exists and selected"
    (writer/save-game test-writer "test/saves" 0 qs-game)
    (let [game (setup-game qs-utils "test/saves")]
      (is (= 0 (:id game)))
      (is (= "test/saves" (:dir game)))
      (is (= test-human (:current game)))
      (is (= test-computer (:opponent game)))
      (is (= board (:board game))))
    (writer/delete-game test-writer "test/saves" 0)))
