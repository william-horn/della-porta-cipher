
import java.util.*;

public class TicTacToe {
  public static String[][] gameState = {
    {null, null, null},
    {null, null, null},
    {null, null, null},
  };

  public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {

      display__intoMessage();

      String playerName = prompt(input, "Enter username: ");
      String playerCharacter = prompt(input, "Choose your character: ");

      draw__gameBoard(gameState);

      String userMove = prompt(input, "Enter your move (row:col, [a-c]:[1-3]): ");
      int[] gridCoords = getGridCoords(userMove);
      updateGameState(gridCoords, playerCharacter);

      draw__gameBoard(gameState);

    } catch (Exception err) {
      // print("Error creating input object: " + err.getMessage());
      err.printStackTrace();
    }
  }

  public static void print(String message) {
    System.out.println(message);
  }

  public static void printf(String template, Object ...args) {
    System.out.printf(template, args);
  }

  public static String prompt(Scanner input, String message) {
    System.out.print(message);
    return input.nextLine();
  }

  public static void display__intoMessage() {
    print("------ WELCOME TO TIC TAC TOE ----------------------");
  }

  public static int[] getGridCoords(String userMove) {
    int row = userMove.charAt(0) - 'a';
    int col = Character.getNumericValue(userMove.charAt(1)) - 1;

    return new int[] {row, col};
  }

  public static void updateGameState(int[] coords, String character) {
    gameState[coords[0]][coords[1]] = character;
  }

  public static String[] formatRowEntries(String[] row) {
    String[] formattedRow = new String[3];

    for (int i = 0; i < row.length; i++) {
      String entry = row[i];
      formattedRow[i] = entry == null ? "_" : entry;
    }

    return formattedRow;
  }
  
  public static void draw__gameBoard(String[][] gameState) {
    for (int i = 0; i < gameState.length; i++) {
      String[] row = formatRowEntries(gameState[i]);

      printf("%s | %s | %s\n", row[0], row[1], row[2]);

      if (i < gameState.length - 1) print("---------");
    }
  }

}
