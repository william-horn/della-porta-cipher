
// imports
import java.util.*;

/*
 * TIC TACT TOE CLASS:
 * 
 * Contains utility methods for running the game of tic tac toe, and
 * executes the game within the main method.
 */
public class TicTacToe {

  // tic-tac-toe game board
  public static String[][] gameState = {
    {null, null, null},
    {null, null, null},
    {null, null, null},
  };

  // keep record of available grid slots
  public static ArrayList<Integer> availableRows = new ArrayList<>();
  public static HashMap<Integer, ArrayList<Integer>> availableCols = new HashMap<>();

  // global player settings
  public static String playerName = null;
  public static String computerName = "Computer";

  // global computer settings
  public static String playerCharacter = null;
  public static String computerCharacter = null;

  public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {

      // display intro message to console
      display__introMessage();

      /*
       * GAME LOOP:
       * 
       * Collect game settings from user input
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
        if (playerName == null) {
          playerName = prompt(input, "Enter username: ");
        }

        if (!isValidPlayerName(playerName)) {
          playerName = null;
          continue;
        }

        // valiate chosen player character
        if (playerCharacter == null) {
          playerCharacter = prompt(input, "Choose your character (x or o): ");
          computerCharacter = getInverseCharacter(playerCharacter);
        }

        if (!isValidCharacter(playerCharacter)) {
          print("Invalid character entry - must be the character 'o' or 'x'");
          playerCharacter = null;
          continue;
        }

        // draw the initial game board
        initializeAvailableGameMoves();
        draw__gameBoard(gameState);

        /*
         * TURN-BASED LOOP:
         * 
         * Responsible for running the turn-based functionality of the game. Continue
         * prompting the user for their next game move, and compare it against
         * the computer's simulated game move.
         */
        while (true) {
          // handle input validation
          String userMove = prompt(input, "Enter your move (ex: a2): ");
          int[] gridCoords = parseGridCoords(userMove);

          if (gridCoords == null) {
            print("Invalid move - user input must be a 2-character string of row:column");
            continue;
          }

          // if the game state could not be changed, then re-prompt the user 
          if (!updateGameState(playerName, gridCoords, playerCharacter)) {
            continue;
          }

          if (!simulateComputerMove()) {
            print("Game over - computer cannot make any more moves");
            System.exit(0);
          }

          draw__gameBoard(gameState);
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
   * INPUT VALIDATION METHODS
   */
  public static boolean isValidCharacter(String characterInput) {
    if (characterInput.length() > 1) return false;
    char chosenCharacter = Character.toLowerCase(characterInput.charAt(0));

    if (!(chosenCharacter == 'o' || chosenCharacter == 'x')) return false;

    return true;
  }

  public static boolean isValidMove(int[] gridCoords) {
    if (
      gridCoords[0] > gameState.length - 1 || gridCoords[0] < 0 || 
      gridCoords[1] > gameState.length - 1 || gridCoords[1] < 0
    ) {
      print("Invalid move - choice is out of bounds");
      return false;
    }

    if (getGridElement(gridCoords) != null) {
      print("Invalid move - slot is already taken");
      return false;
    }

    return true;
  }

  public static boolean isValidPlayerName(String playerName) {
    if (playerName.contains(" ")) {
      print("Invalid player name - cannot contain spaces");
      return false;
    }

    if (playerName.length() > 25) {
      print("Invalid player name - cannot exceed 25 characters");
      return false;
    }

    return true;
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
  public static void display__introMessage() {
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
  public static int[] parseGridCoords(String userMove) {
    // find the row/col number
    if (userMove.length() != 2)
      return null;

    int row = Character.toLowerCase(userMove.charAt(0)) - 'a';
    int col = Character.getNumericValue(userMove.charAt(1)) - 1;

    // return an array of grid coordinates
    return new int[] {row, col};
  }

  public static String getInverseCharacter(String character) {
    return character.equalsIgnoreCase("x") ? "o" : "x";
  }

  public static String getGridElement(int[] coords) {
    return gameState[coords[0]][coords[1]];
  }

  public static boolean gameIsActive() {
    return availableRows.size() > 0;
  }

  public static boolean gameIsOver() {
    return !gameIsActive();
  }

  public static void initializeAvailableGameMoves() {
    for (int rowNum = 0; rowNum < gameState.length; rowNum++) {
      availableRows.add(rowNum);
      ArrayList<Integer> cols = new ArrayList<>();

      for (int colNum = 0; colNum < gameState.length; colNum++)
        cols.add(colNum);

      availableCols.put(rowNum, cols);
    }
  }

  public static boolean simulateComputerMove() {
    // check if there are still available moves
    if (gameIsOver()) return false;

    // at this point, at least one row + col is remaining
    int randRow = availableRows.get((int) (Math.random()*availableRows.size()));
    ArrayList<Integer> row = availableCols.get(randRow);

    // get the random column
    int randCol = row.get((int) (Math.random()*row.size()));

    // construct the existing randomized grid coordinates
    int[] gridCoords = {randRow, randCol};

    return updateGameState(computerName, gridCoords, computerCharacter);
  }

  /*
   * updateGameState(<int[]> coords, <String> character):
   * 
   * Update the game board at the given coordinates with
   * a new state.
   * 
   * TODO: add validation for checking if the update is valid.
   * the method should return `true` if the update passed, or
   * `false` if the update failed.
   * 
   * @param <int[]> coords: the coordinate pair on the grid to update
   * @param <String> character: the player character to enter at the coordinates
   */
  public static boolean updateGameState(String playerName, int[] coords, String character) {
    // unpack the row:col coordinates
    int rowNum = coords[0];
    int colNum = coords[1];

    // check for move validation
    if (!isValidMove(coords)) return false;

    // update the game state grid
    gameState[rowNum][colNum] = character;

    // update the available columns array
    ArrayList<Integer> remainingCols = availableCols.get(rowNum);
    remainingCols.remove(Integer.valueOf(colNum));

    // if no more columns are available in the row, remove the row from available rows
    if (remainingCols.isEmpty())
      availableRows.remove(Integer.valueOf(rowNum));

    return true;
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
  public static String[] formatRowEntries(String[] row) {
    String[] formattedRow = new String[gameState.length];

    // update all `null` entries with "_", or keep `entry` 
    for (int i = 0; i < row.length; i++) {
      String entry = row[i];
      formattedRow[i] = entry == null ? "_" : entry;
    }

    // return the formatted row of form: {<String>, ...}
    return formattedRow;
  }
  
  /*
   * draw__gameBoard(<String[][]> gameState):
   * 
   * Responsible for rendering the game board to the terminal
   * based on the current grid state of the game.
   * 
   * TODO: make the `printf` display support more than 3 rows
   * of tic tac toe
   */
  public static void draw__gameBoard(String[][] gameState) {
    // iterate over game state
    print("\n");
    write("\t");

    for (int i = 0; i < gameState.length; i++) 
      write(((i > 0) ? " ".repeat(5) : " ") + (i + 1));

    print("\n");

    for (int i = 0; i < gameState.length; i++) {
      String[] row = formatRowEntries(gameState[i]);

      // format and display the row
      printf(
        "%s\t %s  |  %s  |  %s \n", 
        (char) (97 + i), 
        row[0], 
        row[1], 
        row[2]
      );

      // separate rows in between data points
      if (i < gameState.length - 1) print("\t---------------");
    }

    print("\n");
  }

}
