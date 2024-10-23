/*
 * Author: William J. Horn
 * Written: 10/18/2024
 * 
 * Input 1 -> "Some random string"
 * Input 2 -> "Another random string"
 * 
 * Output -> "Longest suffix is: random string"
 */

import java.util.*;

public class WilliamHorn_Exercise_5_51 {
  /*
   * reverseString(<String> str) 
   * 
   * Reverses some string 'str'
   * 
   * @param <String> str: The string to reverse
   * @returns <String> reversedString: The reversed version of 'str'
   */
  public static String reverseString(String str) {
    String reversed = "";
    
    for (int i = str.length() - 1; i >= 0; i--)
        reversed += str.charAt(i);
    
    return reversed;
  }

  /*
   * Begin main program execution
   */
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    // Prompt the user to enter two strings
    System.out.print("Enter the first string: ");
    String s1 = input.nextLine();
    System.out.print("Enter the second string: ");
    String s2 = input.nextLine();

    String result = "";

    // Modified: Reverse both input strings
    String rev_s1 = reverseString(s1);
    String rev_s2 = reverseString(s2);

    // continue the same for loop logic
    for (int i = 0; i < s1.length() && i < s2.length(); i++) {

      /*
       * Modified: Compare the reversed strings at index 'i', which now
       * represents a comparison starting from the end of both strings to
       * the start, until the smallest string length is met
       */
      if (rev_s1.charAt(i) == rev_s2.charAt(i))
        result += rev_s1.charAt(i);
      else
        break;
    }

    // Modified output from saying 'prefix' to 'suffix'
    if (result.length() > 0) {
      System.out.println("The longest common suffix is " + reverseString(result));
    }
    else {
      System.out.println(s1 + " and " + s2 + " have no common suffix");
    }
  }
}
