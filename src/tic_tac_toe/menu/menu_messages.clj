(ns tic-tac-toe.menu.menu-messages)

(def messages (hash-map
  :welcome "Welcome to Tic Tac Toe.\n\nFill a row, column, or diagonal with your token to win,\nbut make sure your opponent doesn't do the same!\n\nPress enter to continue.\n\n"
  :help "\nBelow are available game commands:\n\n   ?\thelp menu, displays list of available commands\n   s\tsave game, confirms save overwrite (if applicable)\n   s!\tquick save, automatically overwrites save (if any) and exits\n   q\tquit, exits game without saving\n\n"
  :quick-save "Unsaved quick save exists. Quick save will be deleted if not played. Continue playing (y/n)? "
  :load-type "Select [1] for a new game, or\n       [2] to load a saved game.\n\nSelection: "
  :name-save "Please enter a name for your save: "
  :invalid-selection "Your selection is not valid. Please select again.\n\n"
  :no-save "There are no saved games. Please select again.\n\n"
  :select-log "Enter the log number where you'd like to save your game.\n\n%s\n\nSelection: "
  :empty-log "[%d] ---"
  :save-log "[%d] %s, last played %s day(s) ago"
  :save-exists "This save already exists. Overwrite this save (y/n)? "
  :exit? "Would you like to exit (y/n)? "))
