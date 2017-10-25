(ns tic-tac-toe.logs.game-to-log-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.logs.game-to-log :refer :all]))

(def test-io (io/mock-value-output "Billy" ""))
(def test-ui (ui/create-ui test-io #("exit")))

(def game {:ui test-ui :name-save "message"})

(deftest checks-if-game-and-log-have-same-name
  (testing "returns true if both are named the same"
    (is (= true (named-same {:name "name"} {:name "name"}))))
  (testing "returns false if names are different or nil"
    (is (= false (named-same {} {})))
    (is (= false (named-same {:name "Pete"} {})))
    (is (= false (named-same {:name "Pete"} {:name "Blob"})))))

(deftest adds-name-to-game
  (testing "adds name as key on game hash"
    (is (= {:id 1 :name "Pete"} (associate-name "Pete" {:id 1})))))

(deftest associates-given-id-to-game
  (testing "adds id to games without ids"
    (is (= {:id 0 :board []} (associate-id {:id 0} {:board []}))))
  (testing "replaces game id with new id for games with ids"
    (is (= {:id 1 :board []} (associate-id {:id 1} {:id 0 :board []})))))

(deftest names-game-from-user-input
  (testing "gets name from user and associates with game"
    (is (= (assoc game :name "Billy") (name-game game)))))
