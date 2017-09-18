(ns tic-tac-toe.io-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.io :refer :all]))

(def message "message")

(deftest handles-output-to-user
  (testing "receives and handles output to user"
    (is (= message (with-out-str (output message))))))

(deftest handles-input-from-user
  (testing "receives and handles input from user"
    (with-in-str message
      (is (= message (input))))))

(deftest flushes-terminal
  (testing "calls flush"
    (with-redefs [flush (constantly "called")]
      (is (= "called" (flushes))))))
