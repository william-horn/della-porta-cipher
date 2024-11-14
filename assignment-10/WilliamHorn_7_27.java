/*
 * Author: William J. Horn
 * Written: 11/6/2024
 * 
 * Purpose: Compare list equality between two lists
 * 
 * Input 1 -> 4 6 5 8
 * Input 2-> 4 8 6 5
 * 
 * Output -> "The lists are equal"
 */

 import java.util.*;

 public class WilliamHorn_7_27 {
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
   * createElementFrequencyChart(<int[]> list):
   * 
   * Create a new data structure of `list`, which is a 2D array
   * of the form:
   * 
   *    frequencyChart = {
   *      {listElement_1, occurrences},
   *      {listElement_2, occurrences},
   *      ...
   *    }
   * 
   * where the first column represents an element in the array, and 
   * the second element represents the amount of occurrences of that
   * element in the array. This is used to keep track of how many repeat
   * elements are in a given array when comparing the equality between
   * two arrays.
   * 
   * @param <int[]> list: The array to create an element frequency chart of
   * @return <int[][]> elementFrequencyChart: The 2D freauency chart of `list`
   */
  public static int[][] createElementFrequencyChart(int[] list) {
    // new 2D array structure
    int[][] elementFrequencyChart = new int[list.length][2];

    for (int i = 0; i < list.length; i++) 
    {
      int value = list[i];
      int[] elementRecord = getFrequencyChartElement(elementFrequencyChart, value);

      if (elementRecord == null) {
        elementFrequencyChart[i] = new int[] {value, 1};
      } else {
        elementRecord[1]++;
      }
    }

    return elementFrequencyChart;
  }

  /*
   * getFrequencyChartElement(<int[][]> chart, <int> element):
   * 
   * Grab a row from a provided element frequency chart. The row is
   * represented by the form: {listElement, occurrences} as described
   * in `createElementFrequencyChart`. If the `listElement` in the row
   * equals the `element` argument, then return the row.
   * 
   * @param <int[][]> chart: The frequency chart to iterate over
   * @param <int> element: The element to find within the frequency chart
   * @return <int[][] | null> chartElement: The row of the frequency chart to return. If none exists, returns null.
   */
  public static int[] getFrequencyChartElement(int[][] chart, int element) {
    for (int[] row : chart)
      if ((row != null) && (row[0] == element)) return row;

    return null;
  }
 
  /*
  * equals(<int[]> list1, <int[]> list2): 
  * 
  * Compare two lists to see if they are equal
  * 
  * @param <int[]> list1: The first list
  * @param <int[]> list2: The second list
  * @return <boolean> isEqual: Whether or not the lists are strictly equal (true = yes, false = no)
  */
  public static boolean equals(int[] list1, int[] list2) {
    int[][] list1FrequencyChart = createElementFrequencyChart(list1);

    // for (int[] g : list1FrequencyChart) {
    //   System.out.println("list 1 FC: " + g[0] + ", " + g[1]);
    // }

    for (int i = 0; i < list2.length; i++) {
      int list2Element = list2[i];
      int[] chartElement = getFrequencyChartElement(list1FrequencyChart, list2Element);

      if (chartElement == null) {
        error("Number does not exist inside the other list");
        return false;
      }

      if (chartElement[1] == 0) {
        error("Number exceeds occurences in the original list");
        return false;
      }

      chartElement[1]--;
    }

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

      // handle case when user enters different list sizes for both lists
      if (list1[0] != list2[0]) {
        error("Both lists must be the same size (same starting number)");
        System.exit(0);
      }

      // Compare the two lists to see if they are strictly equal
      if (equals(list1, list2))
        System.out.println("\n> The lists are equal");
      else
        System.out.println("\n> The lists are not equal");
    }
  }
 }
 