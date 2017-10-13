(ns tic-tac-toe.menu.menu-selector-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.mocks.mock-io :as io]
        [tic-tac-toe.mocks.mock-ui :as ui]
        [tic-tac-toe.players.player :as player]
        [tic-tac-toe.read-write.reader :as reader]
        [tic-tac-toe.read-write.timestamper :as timestamper]
        [tic-tac-toe.read-write.writer :as writer]
        [tic-tac-toe.menu.menu-selector :refer :all]))

(def test-io (io/mock-value-output "y" ""))
(def test-ui (ui/create-mock-ui test-io #("exit")))
(def test-timestamper (timestamper/create-timestamper))
(def test-reader (create-reader test-timestamper))
(def utils (hash-map :ui test-ui :reader test-reader))

(defn valid-func [utils] "option pulled")
(defn error-func [options error message utils] (str error message))
(def test-options (hash-map :y valid-func))

(deftest uses-user-input-to-pull-options
  (testing "takes user input and gets user selection from options hash"
    (is (= valid-func
      (pull-from-options "y" test-options error-func
        "error " "message"))))
  (testing "takes user input and return partial which evaluates to expected output"
    (is (= "error message"
      ((pull-from-options "w" test-options error-func
        "error " "message") utils)))))

(deftest calls-functions-based-on-input
  (testing "calls correct function based on valid input"
    (is (= "option pulled" (get-menu-selection test-options "message" utils)))))
