# Advanced-TicTacToe
Advanced TicTacToe is a surprisingly complex strategy game that puts a spin on the classic Tic Tac Toe game. 

Description:
Advanced Tic Tac Toe: There are 9 small tic tac toe
boards within one large tic tac toe board. Just like 
regular tic tac toe, you get three in a row, column,
or diagonal and you claim the board. The goal of this
game is to claim three small tic tac toe boards that
will let you claim the large tic tac toe board. There's
a twist: after one player goes, the spot on the small 
board that they marked corresponds to a small board 
on the large tic tac toe. The second player can only
place a mark somewhere on that corresponding board.
This is represented by the board being yellow.
If a corresponding board has been claimed 
already, then this player can put a mark anywhere.

An overview of the methods: Game.java is where the actual game is being run. It lays out all the panels and 
  buttons, such as rematch, start new game, and instant play. Rematch resets the board 
  and the two players play again, with the result of the last game recorded. Start new
  game completely resets everything. Instant play instantly skips ahead to a result.
  GameCourt.java is where the magic happens. It is what paints the court of the game, and
  it updates the court everytime a Spot is clicked. It also has a bunch of methods like
  the rematch method and the new game method.
  Spot.java is the representation of every small square in the advanced tic tac toe grid.
  It contains information like the state it is in (0 = empty, 1 = player 1's, 2 = player 2's)
  whether or not the Spot is valid to be clicked on, whether the grid it is in has been taken 
  by a player, and whether the grid is a draw.
  TicTacToe.java is the class responsible for containing the instructions, drawing the grids,
  and reading and writing the high scores. 
  Mark.java is the general class for a mark (like X or O). It has assignments like which player
  uses that mark, whether or not that mark has won a board or the game, and it has the drawWinner
  method that displays its player has won if the mark has won.
  X.java is a subtype of Mark.java that overrides the drawTakeBoard method. Mark's drawTakeBoard
  is just a default covering the Spot with an orange square, but X overrides it to draw an X.
  O.java is the subtype of Mark.java that overrides the drawTakeBoard method to display
  an O instead of an orange square.
  highscores.txt is the text file that is read and written to by TicTacToe.java to store the results
  of the games.
