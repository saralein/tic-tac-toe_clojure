(ns tic-tac-toe.ui.colorizer-test
  (:use [clojure.test :refer :all]
        [tic-tac-toe.ui.colorizer :refer :all]))

(def test-colorizer (create-colorizer))
(def log-prompts (list "Log 1" "Log 2" "Log 3"))
(def colorized-prompts (list "Log 1" "\033[104mLog 2\033[49m" "Log 3"))

(deftest highlights-text
  (testing "returns given text with highlighting"
    (is (= "\033[104mHello!\033[49m" (highlight test-colorizer "Hello!")))))

(deftest formats-prompt-with-color
  (testing "colors prompt specified by location"
    (is (= colorized-prompts (process-color test-colorizer log-prompts 1 highlight)))))
