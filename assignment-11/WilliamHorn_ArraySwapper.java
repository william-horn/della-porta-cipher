/*
 * Author: William J. Horn
 * Written: 11/11/2024
 * 
 * Purpose: Swap random rows or columns in a randomized matrix 
 * of integers. 
 * 
 * Rows and Columns are defined by the first 2 command line arguments respectively.
 * 
 * Select which action (rows or columns) to swap by choosing "1" or "2", or
 * select "3" to exit the program.
 */

import java.util.*;

public class WilliamHorn_ArraySwapper {
  /*
   * println(<Object> message):
   * 
   * Shorthand for: System.out.println(...);
   */
  public static void println(Object message) {
    System.out.println(message);
  }

  /*
   * printf(<String> template, <Object> ...args):
   * 
   * Shorthand for: System.out.printf(...);
   */
  public static void printf(String template, Object ...args) {
    System.out.printf(template, args);
  }

  /*
   * print(<Object> message):
   * 
   * Shorthand for: System.out.print(...);
   */
  public static void print(Object message) {
    System.out.print(message);
  }

  /*
   * promptMessage(<Scanner> input, <String> message):
   * 
   * A combination of printing and inline message with
   * prompting 
   * 
   * @param <Scanner> input: The scanner object to prompt the user input
   * @param <String> message: The message to display before requesting the input
   */
  public static String promptMessage(Scanner input, String message) {
    print(message);
    return input.nextLine();
  }

  /*
   * lengthInt(<int> n):
   * 
   * Calculate the magnitude of a number `n`
   * 
   * @param <int> n: The number to get the length of
   * @return <int> magnitude: The magnitude of number `n`
   */
  public static int lengthInt(int n) {
    if (n <= 0) return 0;
    return (int) Math.floor(Math.log10(n));
  }

  /*
   * randInt(<int> min, <int> max):
   * 
   * Calculate a random integer between `min` and `max`,
   * inclusive of `max`
   * 
   * @param <int> min: The minimum integer value
   * @param <int> max: The maximum integer value
   * @return <int> randomInt: The random integer betweem `min` and `max`
   */
  public static int randInt(int min, int max) {
    return (int) (min + Math.random()*(max + 1));
  }

  /*
   * randFillMatrix(<int[][]> matrix, <int> min, <int> max):
   * 
   * Given an already existing 2D array matrix, populate it with
   * random integer values between `min` and `max`, following
   * the rules of the `randInt()` method
   * 
   * @param <int[][]> matrix: The 2D array to populate
   * @param <int> min: The min of `randInt()`
   * @param <int> max: The max of `randInt()`
   * @return <int[][]> matrix: The original matrix reference
   */
  public static int[][] randFillMatrix(int[][] matrix, int min, int max) 
  {
    for (int[] row : matrix)
      for (int i = 0; i < row.length; i++) 
        row[i] = randInt(min, max);

    return matrix;
  }

  /*
   * displayMatrix(<int[][]> matrix):
   * 
   * Output the content of a given 2D array matrix as text
   * to the console
   * 
   * @param <int[][]> matrix: The 2D array matrix
   */
  public static void displayMatrix(int[][] matrix) 
  {
    println("[");
    for (int[] row : matrix) {
      for (int num : row) {
        print(
          "  " + 
          num + 
          // account for spacing offsets that occur for higher digit count numbers
          " ".repeat(4 - lengthInt(num))
        );
      }
      println("");
    }
    println("]");
  }

  /*
   * swapRows(<int[][]> matrix, <int> row1, <int> row2):
   * 
   * Swap 2 given rows in a matrix with each other. Does
   * not mutate the original matrix.
   * 
   * @param <int[][]> matrix: The given matrix
   * @param <int> row1: The first row in the matrix to swap
   * @param <int> row2: The second row in the matrix to swap
   * @return <int[][]> newMatrix: The newly mutated matrix
   */
  public static int[][] swapRows(int[][] matrix, int row1, int row2) 
  {
    int[][] newMatrix = matrix.clone();

    int[] temp = newMatrix[row1];
    newMatrix[row1] = newMatrix[row2];
    newMatrix[row2] = temp;

    return newMatrix;
  }

  /*
   * swapColumns(<int[][]> matrix, <int> row1, <int> row2):
   * 
   * Swap 2 given columns in a matrix with each other. Does
   * not mutate the original matrix.
   * 
   * @param <int[][]> matrix: The given matrix
   * @param <int> col1: The first column in the matrix to swap
   * @param <int> col2: The second column in the matrix to swap
   * @return <int[][]> newMatrix: The newly mutated matrix
   */
  public static int[][] swapColumns(int[][] matrix, int col1, int col2) 
  {
    int[][] newMatrix = matrix.clone();

    for (int[] row : newMatrix) {
      int temp = row[col1];
      row[col1] = row[col2];
      row[col2] = temp;
    }

    return newMatrix;
  }

  public static void main(String[] args) 
  {
    // gather command line args & create initial matrix
    final int ROWS = Integer.parseInt(args[0]);
    final int COLS = Integer.parseInt(args[1]);
    int[][] matrix = new int[ROWS][COLS];

    // populate the matrix with random values between 0 and 100 & display the matrix
    matrix = randFillMatrix(matrix, 0, 100);
    println("\nRandomized Matrix: \n");
    displayMatrix(matrix);

    // prompt user choice for swapping random rows or columns in the matrix
    try (Scanner input = new Scanner(System.in)) 
    {
      appLoop: while (true) 
      {
        // menu options
        println("\n1) Swap 2 random rows");
        println("2) Swap 2 random columns");
        println("3) Exit\n");

        // user selected menu action
        String selectedAction = promptMessage(input, "Your choice: ");

        // handle menu selection logic:
        switch (selectedAction) 
        {
          // If the user chooses to swap rows...
          case "1":
            int row1 = randInt(0, ROWS - 1);
            int row2 = randInt(0, ROWS - 1);

            // ensure the randomly swapped rows are always different
            if (row1 == row2) row2 = (row1 + 1)%(ROWS - 1);
            matrix = swapRows(matrix, row1, row2);

            printf("\n(Swapped rows %s and %s)\n", row1, row2);
            break appLoop;

          // If the user chooses to swap columns...
          case "2": 
            int col1 = randInt(0, COLS - 1);
            int col2 = randInt(0, COLS - 1);

            // ensure the randomly swapped columns are always different
            if (col1 == col2) col2 = (col1 + 1)%(COLS - 1);
            matrix = swapColumns(matrix, col1, col2);

            printf("\n(Swapped columns %s and %s)\n", col1, col2);
            break appLoop;

          case "3":
            println("Goodbye!");
            System.exit(0);

          default:
            printf("[!] %s is not a valid menu choice item. Try again.\n", selectedAction);

        }
      }

      // display the updated matrix
      println("\nUpdated matrix: \n");
      displayMatrix(matrix);
    }
  }
}
