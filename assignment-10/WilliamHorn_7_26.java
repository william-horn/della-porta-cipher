/*
 * Author: William J. Horn
 * Written: 11/6/2024
 * 
 * Purpose: Strictly compare list equality between two lists
 * 
 * Input 1 -> 4 6 5 8
 * Input 2-> 4 8 7 8
 * 
 * Output -> "The lists are not strictly equal"
 */

import java.util.*;

public class WilliamHorn_7_26 {
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
   * equals(<int[]> list1, <int[]> list2): 
   * 
   * Compare two lists to see if they are strictly equal
   * 
   * @param <int[]> list1: The first list
   * @param <int[]> list2: The second list
   * @return <boolean> isEqual: Whether or not the lists are strictly equal (true = yes, false = no)
   */
  public static boolean equals(int[] list1, int[] list2) {
    for (int i = 0; i < list1.length; i++)
      if (list1[i] != list2[i]) return false;

    return true;
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
  public static int[] promptList(Scanner input, String message) {
    System.out.print(message);
    String list_1 = input.next();
    
    // Use the first integer in the list to set the array size
    int list_size = Integer.parseInt(list_1);
    int[] list = new int[list_size];
    list[0] = list_size;

    for (int i = 1; i < list_size; i++) {
      String listElement = input.next();
      list[i] = Integer.parseInt(listElement);
    }

    return list;
  }

  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) {
        // Prompt the user to enter the two lists:
        int[] list1 = promptList(input, "Enter first integer list: ");
        int[] list2 = promptList(input, "Enter second integer list: ");

        // Compare the two lists to see if they are strictly equal
        if (equals(list1, list2))
          System.out.println("The lists are strictly equal");
        else
          System.out.println("The lists are not exclusively equal");
      }
  }
}
