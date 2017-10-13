(ns tic-tac-toe.logs.log-selector-test
  (:use [tic-tac-toe.menu.menu-messages :only [messages]])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.mocks.mock-io :as mock-io]
            [tic-tac-toe.mocks.mock-ui :as mock-ui]
            [tic-tac-toe.logs.log-selector :refer :all]))

(def test-io (mock-io/mock-value-output "1" ""))
(def test-ui (mock-ui/create-mock-ui test-io #("exit")))

(def game
  {:ui test-ui :dir "test/saves"
   :select-log (:select-log messages)
   :empty-log (:empty-log messages)
   :save-log (:save-log messages)})

(def log-1 {:id 1 :name "peaches"
            :updated "Wed Oct 18 19:16:39 CDT 2017"})
(def log-2 {:id 2 :updated "Wed Oct 18 13:59:33 CDT 2017"})
(def log-3 {:id 3 :updated "Wed Oct 18 11:22:42 CDT 2017"})
(def logs (list log-1 log-2 log-3))

(deftest checks-if-user-input-matches-log-id
  (testing "returns true if input matches log id"
    (is (= true (input-matches-log-id? 1 {:id 1}))))
  (testing "returns false if input does not match log id"
    (is (= false (input-matches-log-id? 2 {:id 1})))))

(deftest checks-if-log-has-name
  (testing "returns true if log is named (previously saved)"
    (is (= true (log-has-name? {:id 1 :name "Pete"}))))
  (testing "returns false if log is not named (empty log)"
    (is (= false (log-has-name? {:id 0})))))

(deftest filters-log-with-given-function
  (testing "returns only named logs"
    (is (= (list log-1) (filter-logs logs log-has-name?))))
  (testing "returns only logs matching user input for id"
    (is (= (list log-2) (filter-logs logs (partial input-matches-log-id? 2))))))

(deftest returns-log-based-on-user-input
  (testing "returns correct log for user input"
    (is (= log-1 (pick-log logs game)))))
