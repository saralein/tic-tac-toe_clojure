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
(def clear-string (format "\033[2J"))
(def divider-3x3 "\n--- + --- + ---\n")
(def row-3x3 " X  |  X  |  X ")
(def board-string (str row-3x3 divider-3x3 row-3x3 divider-3x3 row-3x3 "\n\n"))
(def human-player {:type :human :token "X"})
(def computer-player {:type :computer :token "O"})
(def players {:current human-player :other computer-player})
(def game-settings {:size 3 :board board-3x3 :players players})
(def alt-players {:current computer-player :other human-player})

(def sets-up-human-player
  (testing "returns a hashmap with info for human player"
    (is (= human-player (setup-player :human "X")))))

(def sets-up-computer-player
  (testing "retrusn a hasnmap with info for computer player"
    (is (= computer-player (setup-player :computer "O")))))

(deftest sets-up-game
  (testing "returns a hashmap of game settings"
    (is (= game-settings (game-setup)))))

(deftest takes-turn-on-board
  (testing "gets a move form the user and adds to board"
    (with-redefs [ui/clear (constantly [])
      ui/board-string (constantly [])
      ui/display-message (constantly [])
      player/pick-move (constantly 4)]
      (is (= move-added (take-turn* board-3x3 human-player))))))

(deftest return-current-player
  (testing "returns hash of current player"
    (is (= human-player (get-current-player players)))))

(deftest switches-current-player
  (testing "changes current player to other player"
    (is (= alt-players (switch-current-player players)))))

(deftest handles-game-over
  (testing "ends game if board is full"
    (with-redefs [ui/clear (constantly [])
                  ui/board-string (constantly [])]
      (is (= "Game over.\n" (with-out-str (play board-full human-player)))))))

