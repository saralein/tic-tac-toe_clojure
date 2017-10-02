(ns tic-tac-toe.menu-messages)

(def messages (hash-map
  :welcome "Welcome to Tic Tac Toe.\n\nFill a row, column, or diagonal with your token to win,\nbut make sure your opponent doesn't do the same!\n\nPress enter to continue.\n\n"
  :help "\nBelow are available game commands:\n\n   ?\thelp menu, displays list of available commands\n   s\tsave game, confirms save file overwrite (if applicable) and exits\n   s!\tquick save, automatically overwrites save file (if any) and exits\n\n"
  :load-type "Select [1] for a new game, or\n       [2] to load a saved game.\n\nSelection: "
  :no-error ""
  :invalid-selection "Your selection is not valid. Please select again.\n\n"
  :no-save "There are no saved games. Please select again.\n\n"
  :save-exists "This save file already exists. Overwrite this file (y/n)? "
  :exit? "Would you like to exit (y/n)? "))
