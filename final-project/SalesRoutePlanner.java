/*
 * Will Horn
 * 11/9/2024
 */

import java.util.*;

public class SalesRoutePlanner {
  final public static int[][] DISTANCE_MAP = {
    {90, 76, 171, 136, 132, 89, 99, 172, 158},
    {112, 97, 110, 162, 77, 174, 234, 201},
    {130, 80, 61, 63, 76, 121, 93},
    {56, 132, 82, 203, 248, 209},
    {76, 44, 157, 202, 153},
    {87, 125, 128, 77},
    {139, 184, 152},
    {83, 87},
    {51}
  };

  final public static String[] LOCATION_NAMES = {
    "Allendale", "Charleston", "Columbia",
    "Conway", "Florence", "Lancaster",
    "Manning", "McCormick", "Pickens",
    "Spartanburg"
  };

  // public static enum Locations {
  //   ALLENDALE, CHARLESTON, COLUMBIA,
  //   CONWAY, FLORENCE, LANCASTER,
  //   MANNING, MCCORMICK, PICKENS,
  //   SPARTANBURG
  // }

  public static int getIndexOf(String[] list, String element) 
  {
    for (int i = 0; i < list.length; i++) 
      if (list[i].equals(element)) return i;

    return -1;
  }

  /*
   * getDistanceFrom(<String> from, <String> to):
   * 
   * Compute the distance from location A to location B based on the
   * provided Distance chart. Computation uses only half of the distance mappings.
   * 
   * @param <String> from: The origin location
   * @param <String> to: The destination location
   * @return <int> Distance: The distance between 'from' and 'to'
   */
  public static int getDistanceFrom(String from, String to) 
  {
    if (from.equals(to)) return 0;

    
  }

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
    // TODO: add logic for user input 
  }
}
