/*
 * Author: William Horn
 * Written: 9/28/2024
 * 
 * Compilation: javac WilliamHorn_StringAccessCode.java
 * Execution: java WilliamHorn_StringAccessCode
 * 
 * Purpose: Generate a random sequence of uppercase alphabetic characters
 * separated by a hyphen
 * 
 * Example:
 * Output -> TSVZQ-PYZUQ-UGIUL-ZPMAK-TGVCB
 * 
 * Note: I've been advised to disclose that I have prior programming experience.
 */

class WilliamHorn_StringAccessCode {
  // declare global constants defining code generation config
  final static int UPPER_CHAR_START = 65;
  final static int UPPER_CHAR_END = 90;
  final static int CHAR_RANGE = UPPER_CHAR_END - UPPER_CHAR_START + 1;
  final static int CODE_LENGTH = 25;

  public static void main(String[] args) {
    // -------------------- //
    // GENERATE RANDOM CODE //
    // -------------------- //

    // Declare code string outside of loop
    String code = "";

    // loop through CODE_LENGTH many cycles generating a random character and appending it to `code` variable
    for (int i = 0; i < CODE_LENGTH; i++) {
      // generate the random ascii code within the alphabet range
      int randIndex = (int) (Math.random()*CHAR_RANGE);

      // cast the ascii code to a character
      char randChar = (char) (65 + randIndex);

      // for every 5th cycle in the loop, append a hyphen to the code string
      // this excludes the first cycle of the loop where i <= 0
      if (i % 5 == 0 && i > 0) code += "-";

      // append the random character
      code += randChar;
    }

    // print out the code
    System.out.println("Random Code: " + code);
  }
}


