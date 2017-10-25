(ns tic-tac-toe.logs.log-presenter-test
  (:use [tic-tac-toe.menu.menu-messages :only [messages]])
  (:require [clojure.test :refer :all]
            [tic-tac-toe.logs.log-presenter :refer :all]))

(def game
  {:select-log (:select-log messages)
   :empty-log (:empty-log messages)
   :save-log (:save-log messages)})

(def empty-log {:id 1})
(def log {:id 2, :name "peaches", :time-diff "5",
          :board ["O" 'empty 'empty 'empty "X" 'empty 'empty 'empty 'empty],
          :current #tic_tac_toe.players.human.Human{:name "Player 1", :type :human, :token "X"},
          :opponent #tic_tac_toe.players.computer.Computer{:name "Player 2", :type :computer, :token "O", :opponent "X"}})
(def logs [empty-log log])
(def prompt "Enter the log number where you'd like to save your game.\n\n[1] ---  \n[2] peaches, last played 5 day(s) ago  \n\nSelection: ")

(deftest formats-log-list-for-display
  (testing "returns a string with log prompts"
    (is (= prompt (format-log-prompts game logs)))))
