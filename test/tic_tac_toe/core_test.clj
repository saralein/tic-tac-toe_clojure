(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.io :as io]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.core :refer :all]))

(def board-3x3 [" " " " " "
                " " " " " "
                " " " " " "])
(def move-added [" " " " " "
                " " "X" " "
                " " " " " "])
(def board-full ["X" "X" "X"
                 "X" "X" "X"
                 "X" "X" "X"])
(def game-over "Game over.")
(def game-settings {:board board-3x3})
(def clear-string (format "\033[2J"))
(def divider-3x3 "\n--- + --- + ---\n")
(def row-3x3 " X  |  X  |  X ")
(def board-string (str row-3x3 divider-3x3 row-3x3 divider-3x3 row-3x3 "\n\n"))

(deftest sets-up-game
  (testing "returns a hashmap of game settings"
    (is (= game-settings (game-setup)))))

(deftest takes-turn-on-board
  (testing "gets a move form the user and adds to board"
    (with-redefs [ui/clear (constantly [])
                  ui/board-string (constantly [])
                  ui/display-message (constantly [])
                  player/pick-move (constantly 4)]
      (is (= move-added (take-turn* board-3x3))))))

(deftest handles-game-over
  (testing "ends game if board is full"
    (with-redefs [ui/clear (constantly [])
                  ui/board-string (constantly [])]
      (is (= "Game over.\n" (with-out-str (play board-full)))))))
