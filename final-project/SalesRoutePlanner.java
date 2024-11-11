/*
 * Will Horn
 * 11/9/2024
 */

import java.util.*;

{
  00, 12, 43, 98, 34,
  12, 00, 84, 19, 23,
  43, 84, 00, 33, 17,
  98, 19, 33, 00, 99
  34, 23, 17, 99, 00
}

public class SalesRoutePlanner {
  final public static int[][] DISTANCE_MAP = {
    {0, 90, 76, 171, 136},
    {90, 112, 97, 110, 162, 77, 174, 234, 201},
    {},
    {
  };

  /*
   * println(<Object> message):
   * 
   * Shorthand for: System.out.println(...);
   */
  public static void println(Object message) {
    System.out.println(message);
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
   * error(<String> message):
   *
   * Display a formatted error message to the screen
   *
   * @param <String> message: The error info to display
   * @return void
   */
  public static void error(String message) {
    println("\nError: " + message);
  }

  public static void main(String[] args) {
    
  }
}
