/*
 * Author: William J. Horn
 * Written: 10/11/2024
 * 
 * Tic Tac Toe game
 */

// imports
import java.util.*;

/*
 * TIC TACT TOE CLASS:
 * 
 * Contains utility methods for running the game of tic tac toe, and
 * executes the game within the main method.
 */
public class TicTacToe {
  public static class Player 
  {
    public String name = null;
    public String character = null;

    public void setName(String newName) {
      name = newName;
    }

    public String getName() {
      return name;
    }

    public void setCharacter(String newCharacter) {
      character = newCharacter;
    }

    public String getCharacter() {
      return character;
    }
  }

  public static enum GameState 
  {
    TIE, 
    WINNER
  }

  public static enum MoveValidation 
  {
    OUT_OF_BOUNDS,
    SLOT_TAKEN,
    IS_VALID,
    GAME_OVER
  }

  public static enum NameValidation 
  {
    INVALID_LENGTH,
    INVALID_CHARACTERS,
    IS_VALID
  }

  public static enum CharacterValidation 
  {
    INVALID_LENGTH,
    INVALID_CHARACTER,
    IS_VALID
  }

  public static enum MoveInputValidation 
  {
    INVALID_LENGTH,
    IS_VALID,
  }

  public static enum WinType
  {
    ROW,
    COLUMN,
    DIAGONAL
  }

  // tic-tac-toe game board
  public static String[][] gameGrid = 
  {
    {null, null, null},
    {null, null, null},
    {null, null, null},
  };

  // keep record of available grid slots
  public static ArrayList<Integer> availableRows = new ArrayList<>();
  public static HashMap<Integer, ArrayList<Integer>> availableCols = new HashMap<>();

  public static Player Human = new Player();
  public static Player Computer = new Player();

  public static Player firstPlayer = null;

  public static void main(String[] args) 
  {
    try (Scanner input = new Scanner(System.in)) 
    {

      // display intro message to console
      display__introMessage();

      /*
       * MAIN GAME LOOP:
       * 
       * Collect game settings from user input & responsible
       * for restarting the game cycle.
       */
      while (true) {
        /*
         * INPUT VALIDATION:
         * 
         * Run input validation on initial game settings. All invalid
         * data entries will trigger a prompt to the user to 
         * re-enter the information
         */
        // validate playerName
        if (Human.getName() == null) 
        {
          String playerName = prompt(input, "Enter username: ");
          NameValidation playerNameValidation = validatePlayerName(playerName);

          // player name length is invalid
          if (playerNameValidation == NameValidation.INVALID_LENGTH)
            print("Invalid player name - too long");

          // player character is invalid
          if (playerNameValidation == NameValidation.INVALID_CHARACTERS)
            print("Invalid player name - illegal characters");

          // if player name validation fails, re-prompt the user
          if (playerNameValidation != NameValidation.IS_VALID) 
            continue;

          // ! beyond this point, player name is valid
          Human.setName(playerName);
        }

        // validate chosen player character
        if (Human.getCharacter() == null) 
        {
          String playerCharacter = prompt(input, "Choose your character (x or o): ");
          CharacterValidation playerCharacterValidation = validateCharacter(playerCharacter);

          // player character is invalid
          if (playerCharacterValidation == CharacterValidation.INVALID_CHARACTER)
            print("Invalid character entry - must be character 'o' or 'x'");
          
          // player character is the incorrect length
          if (playerCharacterValidation == CharacterValidation.INVALID_LENGTH)
            print("Invalid character length - must be one character");

          // if player character is invalid, re-prompt user
          if (playerCharacterValidation != CharacterValidation.IS_VALID) 
              continue;

          // ! beyond this point, player character is valid
          Human.setCharacter(playerCharacter);
          Computer.setCharacter(getInverseCharacter(playerCharacter));
        }

        // decide who goes first
        // if (firstPlayer == null) {
        //   String goingFirst = prompt(input, "Who goes first? (1 = you, 2 = computer): ");

        //   if (goingFirst.equalsIgnoreCase("1")) {
        //     firstPlayer = Human;
        //   } else if (goingFirst.equalsIgnoreCase("2")) {
        //     firstPlayer = Computer;
        //   } else {
        //     continue;
        //   }
        // }

        // draw the initial game board
        initializeAvailableGameMoves();
        draw__gameBoard(gameGrid);

        /*
         * TURN-BASED LOOP:
         * 
         * Responsible for running the turn-based functionality of the game. Continue
         * prompting the user for their next game move, and compare it against
         * the computer's simulated game move.
         */
        while (true) 
        {
          // handle input validation
          String userMove = prompt(input, "Enter your move (ex: a2): ");
          MoveInputValidation moveInputValidation = validateMoveInput(userMove);

          // validate move entry
          if (moveInputValidation == MoveInputValidation.INVALID_LENGTH)
            print("Invalid move input - must enter only 2 characters for row|column");

          if (moveInputValidation != MoveInputValidation.IS_VALID)
            continue;

          // ! beyond this point, move entry is valid
          // TODO: find a way to generalize user and computer moves to easily reverse the order
          // of who goes first

          /*
           * USER MOVE
           */
          int[] gridCoords = parseGridCoords(userMove);
          MoveValidation moveValidation = validateMove(gridCoords);

          // check parsed move entry for valid coordinates
          if (moveValidation == MoveValidation.OUT_OF_BOUNDS)
            print("Invalid move - entry is out of grid bounds");

          // validate parsed move entry for valid slot availability
          if (moveValidation == MoveValidation.SLOT_TAKEN) 
            print("Invalid move - slot has already been played");

          // if grid coordinate are invalid, re-prompt the user
          if (moveValidation != MoveValidation.IS_VALID)
            continue;

          // if the game state could not be changed, then re-prompt the user 
          updateGameGrid(Human, gridCoords);
          ArrayList<WinType> winningMoves = getWinningMoves(Human, gridCoords);

          // detect if user made a winning move
          if (!winningMoves.isEmpty()) {
            draw__gameBoard(gameGrid);
            print("YOU WON!");
            System.exit(0);
          }

          /*
           * COMPUTER MOVE
           */

           // if the grid has no more available moves, then end the game with a tie
          if (gameIsOver()) {
            draw__gameBoard(gameGrid);
            print("It was a TIE!");
            System.exit(0);
          }

          // generate computer grid coordinates
          gridCoords = getComputerMove();
          updateGameGrid(Computer, gridCoords);
          winningMoves = getWinningMoves(Computer, gridCoords);

          // check computer win case
          if (!winningMoves.isEmpty()) {
            draw__gameBoard(gameGrid);
            print("YOU LOST!");
            System.exit(0);
          }

          draw__gameBoard(gameGrid);
        }
      }

    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  /*
   * UTILITY METHODS:
   * 
   * Short-hand methods that call or augment native methods
   * for ease of use
   */

  /*
   * print(<String> message): 
   * short-hand for: System.out.print(message);
   */
  public static void print(Object message) {
    System.out.println(message);
  }

  public static void write(Object message) {
    System.out.print(message);
  }

  /*
   * printf(<String> message, <Object> ...): 
   * short-hand for: System.out.printf(template, ...);
   */
  public static void printf(String template, Object ...args) {
    System.out.printf(template, args);
  }

  /*
   * isValidCharacter(<String> characterInput):
   */
  public static CharacterValidation validateCharacter(String characterInput) 
  {
    if (characterInput.length() > 1) 
      return CharacterValidation.INVALID_LENGTH;

    char chosenCharacter = Character.toLowerCase(characterInput.charAt(0));

    if (!(chosenCharacter == 'o' || chosenCharacter == 'x')) 
      return CharacterValidation.INVALID_CHARACTER;

    return CharacterValidation.IS_VALID;
  }

  /*
   * validateMove(<int[]> gridCoords):
   */
  public static MoveValidation validateMove(int[] gridCoords) 
  {
    // check for out-of-bounds case
    if (
      gridCoords[0] > gameGrid.length - 1 || gridCoords[0] < 0 || 
      gridCoords[1] > gameGrid.length - 1 || gridCoords[1] < 0
    ) {
      return MoveValidation.OUT_OF_BOUNDS;
    }

    if (gameIsOver()) 
      return MoveValidation.GAME_OVER;

    // check if the slot is vecant
    if (getGridElement(gridCoords) != null) {
      return MoveValidation.SLOT_TAKEN;
    }

    return MoveValidation.IS_VALID;
  }

  /*
   * validatePlayerName(<String> playerName):
   * 
   * Returns whether or not the playerName the user entered
   * follows the validation rules
   */
  public static NameValidation validatePlayerName(String playerName) 
  {
    if (playerName.contains(" ")) {
      return NameValidation.INVALID_CHARACTERS;
    }

    if (playerName.length() > 25) {
      return NameValidation.INVALID_LENGTH;
    }

    return NameValidation.IS_VALID;
  } 

  public static MoveInputValidation validateMoveInput(String moveInput) {
    return moveInput.length() == 2 ? MoveInputValidation.IS_VALID : MoveInputValidation.INVALID_LENGTH;
  }

  /*
   * prompt(<Scanner> input, <String> message):
   * 
   * Display inline-text in the console and prompt the user
   * for some input
   * 
   * @param <Scanner> input: the scanner object to call next()
   * @param <String> message: the message that displays alongside the prompt
   */
  public static String prompt(Scanner input, String message) {
    write(message);
    return input.nextLine();
  }

  /*
   * DISPLAY METHODS:
   * 
   * Methods for displaying text in the output
   */
  // display the game intro text
  public static void display__introMessage() 
  {
    print("\n--------------------------------------------------------------------");
    print("---------------------- WELCOME TO TIC TAC TOE ----------------------");
    print("--------------------------------------------------------------------\n");
  }

  /*
   * parseGridCoords(<String> userMove):
   * 
   * Get the grid coordinates based on the user input of
   * the form "RC", where:
   *  R = [a, c] (representing [0, 2])
   *  C = [0, 2]
   * 
   * @param <String> userMove: the unparsed coordinate string of the user's move
   * @returns int[] gridCoordinates: the parsed row-column pair
   */
  public static int[] parseGridCoords(String userMove) 
  {
    int row = Character.toLowerCase(userMove.charAt(0)) - 'a';
    int col = Character.getNumericValue(userMove.charAt(1)) - 1;

    // return an array of grid coordinates
    return new int[] {row, col};
  }

  /*
   * getInverseCharacter(<String> character):
   * 
   * Get the opposite game piece to the character passed
   * as the argument.
   * 
   * @param <String> character: the character the user entered
   * @returns <String> oppositeCharacter: the character opposite to the one provided
   */
  public static String getInverseCharacter(String character) {
    return character.equalsIgnoreCase("x") ? "o" : "x";
  }

  /*
   * getGridElement(<int[]> coords):
   * 
   * Return the game board element at the specified coordinates
   * 
   * @param <int[]> coords
   * @returns <String> element: the entry at the grid coordinates
   */
  public static String getGridElement(int[] coords) {
    return gameGrid[coords[0]][coords[1]];
  }

  /*
   * gameIsActive():
   * 
   * returns true if there are still available grid rows
   * to choose from
   */
  public static boolean gameIsActive() {
    return !availableRows.isEmpty();
  }

    /*
   * gameIsOver():
   * 
   * returns true if there are NO MORE available moves
   * to make (when all grid rows are occupied)
   */
  public static boolean gameIsOver() {
    return !gameIsActive();
  }

  /*
   * initializeAvailableGameMoves():
   * 
   * Initialize the available rows & columns ArrayLists. by default,
   * the available lists are empty. They must be manually populated
   * with all of the entries of the gameGrid array.
   */
  public static void initializeAvailableGameMoves() 
  {
    for (int rowNum = 0; rowNum < gameGrid.length; rowNum++) 
    {
      // add available row numbers
      availableRows.add(rowNum);
      ArrayList<Integer> cols = new ArrayList<>();

      // add adailable column numbers to the row table
      for (int colNum = 0; colNum < gameGrid.length; colNum++)
        cols.add(colNum);

      availableCols.put(rowNum, cols);
    }
  }

  /*
   * simulateComputerMove():
   * 
   * Simulate the computer making a move. Returns true if
   * the computer was able to make a movie, or false if 
   * the computer could not make a move. 
   */
  public static int[] getComputerMove() 
  {
    // at this point, at least one row + col is remaining
    int randRow = availableRows.get((int) (Math.random()*availableRows.size()));
    ArrayList<Integer> row = availableCols.get(randRow);

    // get the random column
    int randCol = row.get((int) (Math.random()*row.size()));

    // construct the existing randomized grid coordinates
    int[] gridCoords = {randRow, randCol};
    return gridCoords;
  }

  /*
   * updateGameGrid(<int[]> coords, <String> character):
   * 
   * Update the game board at the given coordinates with
   * a new state. This method ASSUMES a move is already validated.
   * Moves can be validated using `validateMove()`
   * 
   * @param <int[]> coords: the coordinate pair on the grid to update
   * @param <String> character: the player character to enter at the coordinates
   */
  public static boolean updateGameGrid(Player player, int[] coords) 
  {
    try {
      // unpack the row:col coordinates
      int rowNum = coords[0];
      int colNum = coords[1];

      // update the game state grid
      gameGrid[rowNum][colNum] = player.getCharacter();

      // update the available columns array
      ArrayList<Integer> remainingCols = availableCols.get(rowNum);
      remainingCols.remove(Integer.valueOf(colNum));

      // if no more columns are available in the row, remove the row from available rows
      if (remainingCols.isEmpty())
        availableRows.remove(Integer.valueOf(rowNum));

      return true;
    } catch(Exception err) {
      return false;
    }
  }

  public static ArrayList<WinType> getWinningMoves(Player player, int[] coords) {
    // unpack the row:col coordinates
    int rowNum = coords[0];
    int colNum = coords[1];

    // update the game state grid
    String[] row = gameGrid[rowNum];
    ArrayList<WinType> winningMoves = new ArrayList<>();

    // check for winner
    winningMoves.add(WinType.ROW);
    winningMoves.add(WinType.COLUMN);

    // row check
    for (String entry : row)
      if (!player.getCharacter().equals(entry))
        winningMoves.remove(WinType.ROW);

    // column check
    for (String[] gridRow : gameGrid)
      if (!player.getCharacter().equals(gridRow[colNum]))
        winningMoves.remove(WinType.COLUMN);

    // diagonal check
    if (rowNum == colNum) {
      winningMoves.add(WinType.DIAGONAL);

      for (int i = 0; i < gameGrid.length; i++) {
        String[] gridRow = gameGrid[i];

        if (!player.getCharacter().equals(gridRow[i]))
          winningMoves.remove(WinType.DIAGONAL);
      }
    }

    return winningMoves;
  }

  /*
   * formatRowEntries(<String[]> row):
   * 
   * Take a given row in the game state array, and
   * convert it to a string array containing the
   * formatted entries.
   * 
   * @param <String[]> row: the row in the game state array
   * @returns: <String[]> formattedRow: an array with the formatted entries
   */
  public static String[] formatRowEntries(String[] row) 
  {
    String[] formattedRow = new String[gameGrid.length];

    // update all `null` entries with "_", or keep `entry` 
    for (int i = 0; i < row.length; i++) {
      String entry = row[i];
      formattedRow[i] = entry == null ? "_" : entry;
    }

    // return the formatted row of form: {<String>, ...}
    return formattedRow;
  }
  
  /*
   * draw__gameBoard(<String[][]> gameGrid):
   * 
   * Responsible for rendering the game board to the terminal
   * based on the current grid state of the game.
   * 
   * TODO: make the `printf` display support more than 3 rows
   * of tic tac toe
   */
  public static void draw__gameBoard(String[][] gameGrid) 
  {
    // iterate over game state
    print("\n");
    write("\t");

    for (int i = 0; i < gameGrid.length; i++) 
      write(((i > 0) ? " ".repeat(5) : " ") + (i + 1));

    print("\n");

    for (int i = 0; i < gameGrid.length; i++) 
    {
      String[] row = formatRowEntries(gameGrid[i]);

      // format and display the row
      printf(
        "%s\t %s  |  %s  |  %s \n", 
        (char) (97 + i), 
        row[0], 
        row[1], 
        row[2]
      );

      // separate rows in between data points
      if (i < gameGrid.length - 1) print("\t---------------");
    }

    print("\n");
  }

}
