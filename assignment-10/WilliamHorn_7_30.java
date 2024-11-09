/*
 * Author: William J. Horn
 * Written: 11/6/2024
 * 
 * Purpose: Check a list to see of it has 4 consecutive numbers
 * 
 * Input 1 -> 7
 * Input 2 -> 1 2 5 5 5 5 8
 * 
 * Output -> The list has 4 consecutive numbers
 */

import java.util.Scanner;

public class WilliamHorn_7_30 {
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
    System.out.print(message);
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
    System.out.println("\nError: " + message);
  }

  /*
  * promptList(<Scanner> input, <String> message):
  * 
  * Prompt the user to input a list of integers
  * 
  * @param <Scanner> input: The Scanner object
  * @param <String> message: The message prompting the user
  * @return <int[]> list: An integer array containing the integers the user provided
  */
  public static int[] promptList(Scanner input, int size, String message) {
    System.out.print(message);

    int[] list = new int[size];

    for (int i = 0; i < size; i++) {
      String listElement = input.next();
      list[i] = Integer.parseInt(listElement);
    }

    return list;
  }

  /*
   * isConsecutiveFour(<int[]> list):
   * 
   * Returns whether or not a list contains 4 consecutive occurrences of
   * a number
   * 
   * @param <int[]> list: The list input
   * @returns <boolean> isConsecutiveFour: Whether or not the list contains 4 consecutive numbers (true = yes, false = no)
   */
  public static boolean isConsecutiveFour(int[] list) {
    int consecutiveCounter = 1;

    // if list has less than 4 elements, result will always be false
    if (list.length < 4) return false;

    // check to see if the current element is equal to the last and conclude at 4 iterations of this
    for (int i = 1; i < list.length; i++) {
      int element = list[i];

      if (element == list[i - 1])
        consecutiveCounter++;
      else
        consecutiveCounter = 1;

      if (consecutiveCounter == 4) return true;
    }

    return false;
  }

  public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {
      // gather user input
      int sizeOfList = Integer.parseInt(promptMessage(input, "Enter number of values: "));
      int[] list = promptList(input, sizeOfList, "Enter list of integers: ");

      // preform the check
      if (isConsecutiveFour(list))
        System.out.println("List has 4 consecutive numbers");
      else
        System.out.println("List does not have 4 consecutive numbers");
    }
  }
}


