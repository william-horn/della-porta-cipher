/*
 * Author: William J. Horn
 * Written: 10/23/2024
 * 
 * Purpose: Validate a password string
 */

import java.util.*;


public class WilliamHorn_PasswordCheck {
  // short-hand print method
  public static void println(String message) {
    System.out.println(message);
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
    System.out.print(message);
    return input.nextLine();
  }

  /*
   * Password validator utility methods
   */
  public static boolean isValidLength(String password) {
    return password.length() >= 8;
  }

  public static boolean isDigit(char c) {
    return (c >= 48) && (c <= 57);
  }

  public static boolean isCharacter(char c) {
    return (c >= 97) && (c <= 122);
  }

  public static boolean isValidTypography(String password) {
    for (int i = 0; i < password.length(); i++) {
      char character = password.charAt(i);

      if (!(isDigit(character) || isCharacter(character))) {
        return false;
      }
    }

    return true;
  }

  public static int getDigitCount(String password) {
    int digitCount = 0;

    for (int i = 0; i < password.length(); i++) {
      if (isDigit(password.charAt(i))) 
        digitCount++;
    }

    return digitCount;
  }

  // Overall password validator
  public static boolean isValidPassword(String password) {
    if (!isValidLength(password)) {
      println("Invalid password: Incorrect length (must be >= 8 characters)");
      return false;
    }

    if (!isValidTypography(password)) {
      println("Invalid password: Cannot contain symbols (digits and characters ONLY)");
      return false;
    }

    if (getDigitCount(password) < 2) {
      println("Invalid password: Not enough digits (must contain at least 2)");
      return false;
    }

    return true;
  }

  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) {
        String userPassword = promptMessage(input, "Enter password to validate: ");

        if (isValidPassword(userPassword)) {
          println("Password is valid!");
        } else {
          println("Password did not pass validation");
        }

      }
  }
}
