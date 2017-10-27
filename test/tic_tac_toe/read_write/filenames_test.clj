(ns tic-tac-toe.read-write.filenames-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.read-write.filenames :refer :all]))

(deftest creates-filename-list
  (testing "returns list of three possible filenames"
    (is (= (list "1" "2" "3") (generate-filenames)))))
