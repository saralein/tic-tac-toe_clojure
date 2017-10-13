(ns tic-tac-toe.validation.log-validator-test
  (:use [tic-tac-toe.menu.menu-messages :only [messages]])
  (:require [clojure.test :refer :all]
        [tic-tac-toe.validation.log-validator :refer :all]))

(def empty-log {:id 1})
(def log {:id 2, :name "peaches", :save-time 5,
          :board ["O" 'empty 'empty 'empty "X" 'empty 'empty 'empty 'empty],
          :current #tic_tac_toe.players.human.Human{:name "Player 1", :type :human, :token "X"},
          :opponent #tic_tac_toe.players.computer.Computer{:name "Player 2", :type :computer, :token "O", :opponent "X"}})
(def logs [empty-log log])
(defn test-func [logs game error] "test func called")
(def game {:invalid-selection (:invalid-selection messages)})

(deftest checks-if-name-is-valid
  (testing "returns true if name contains at least one non-space character"
    (is (= true (valid-name? "n")))
    (is (= true (valid-name? "name"))))
  (testing "returns false if name contains only spaces"
    (is (= false (valid-name? " ")))
    (is (= false (valid-name? "  ")))))

(deftest lists-all-log-ids-in-list
  (testing "returns list of all log ids of logs in list"
    (is (= (list "1" "2") (get-log-ids logs)))))

(deftest checks-if-input-is-valid-log-id
  (testing "returns true if user input is valid"
    (is (= true (valid-log? logs "2"))))
  (testing "returns false if user input is non-numeric"
    (is (= false (valid-log? logs "W"))))
  (testing "returns false if user input is numeric but not valid log"
    (is (= false (valid-log? logs "3")))))

(deftest validates-user-input
  (testing "returns integer if selection is valid"
    (is (= 2 (check-selection test-func logs game "2"))))
  (testing "calls supplied func if selection is invalid"
    (is (= "test func called" (check-selection test-func logs game "4")))))
