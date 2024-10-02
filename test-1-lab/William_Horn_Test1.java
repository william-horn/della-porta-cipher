/*
 * Author: William Horn
 * Written: 9/20/2024
 * 
 * Compilation: javac William_Horn_Test1.java
 * Execution: java William_Horn_Test1
 * 
 * Purpose:
 * Number comparison
 * 
 * Example:
 * Input -> 44
 * Randomly generated number -> 23
 * Output -> The user's value of: 44 is larger than the computer's value of: 23
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */

import java.util.*;

class William_Horn_Test1 {
  public static void main(String[] args) {
    final int UPPER_BOUND = 99;
    final int LOWER_BOUND = 0;
    
    // set up a try-with-resources block for opening the input stream & catching errors
    try (Scanner input = new Scanner(System.in)) {

      // prompt the user for an integer between 0 and 99
      System.out.print("Please enter an integer from 0 to 99: ");
      int userInt = input.nextInt();

      // validate the integer the user input to ensure it's between 0 and 99 (inclusive)
      if ((userInt < LOWER_BOUND) || (userInt > UPPER_BOUND)) {
        System.out.println("Incorrect value");
        System.exit(0);
      }

      // have the computer generate a random number bound by the number the user input
      int computerRandomInt = (int) (Math.random()*(UPPER_BOUND + 1));

      // comparitively check to determine which value is larger: the randomly generated one or the user's input
      if (computerRandomInt > userInt) {
        System.out.println("The computer's value of " + computerRandomInt + " is larger than the user's value of: " + userInt);
      } else if (userInt > computerRandomInt) {
          System.out.println("The user's value of: " + userInt + " is larger than the computer's value of: " + computerRandomInt);
      } else {
        System.out.println("The number the user input: " + userInt + " and the number the computer randomly generated: " + computerRandomInt + " are equal.");
      }

    // catch any error exceptions reading user input
    } catch(Exception err) {
      System.out.println("Error opening input stream: " + err.getMessage());
    }
  }
}
