(ns tic-tac-toe.game-flow.game-saver-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.players.computer :as computer]
            [tic-tac-toe.players.human :as human]
            [tic-tac-toe.mocks.mock-io :as io]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.read-write.timestamper :as timestamper]
            [tic-tac-toe.read-write.writer :as writer]
            [tic-tac-toe.game-flow.game-saver :refer :all]))

(def new-game {:save-called false :name-called false :overwrite-called false})
(def named-game {:name "Pete" :save-called false :name-called false :overwrite-called false})
(def empty-log {})
(def named-log {:name "Blob"})
(def same-log {:name "Pete"})

(defn test-save
  [game]
  (assoc game :save-called true))

(defn test-name-game
  [game]
  (assoc game :name-called true))

(defn test-overwrite
  [game]
  (assoc game :overwrite-called true))

(deftest handles-saves-conditions
  (testing "correctly handles saving new game to empty log"
    (let [updated-game (handle-save-flow test-save test-overwrite test-name-game empty-log new-game)]
      (is (= true (:save-called updated-game)))
      (is (= true (:name-called updated-game)))
      (is (= false (:overwrite-called updated-game)))))
  (testing "correctly handles named game to empty log"
    (let [updated-game (handle-save-flow test-save test-overwrite test-name-game empty-log named-game)]
      (is (= true (:save-called updated-game)))
      (is (= false (:name-called updated-game)))
      (is (= false (:overwrite-called updated-game)))))
  (testing "correctly handles differently named game to named log"
    (let [updated-game (handle-save-flow test-save test-overwrite test-name-game named-log named-game)]
      (is (= false (:save-called updated-game)))
      (is (= false (:name-called updated-game)))
      (is (= true (:overwrite-called updated-game)))))
  (testing "correctly handles same named game and log"
    (let [updated-game (handle-save-flow test-save test-overwrite test-name-game same-log named-game)]
      (is (= true (:save-called updated-game)))
      (is (= false (:name-called updated-game)))
      (is (= false (:overwrite-called updated-game))))))