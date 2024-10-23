/*
 * Author: William J. Horn
 * Written: 10/18/2024
 * 
 * Purpose: Find # of vowels and consonants in a given sentence
 * 
 * Input -> "Some example sentence"
 * 
 * Output ->
 * Vowels: 8
 * Consonants: 13
 */

import java.util.*;

public class WilliamHorn_Exercise_5_49 {
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
   * isVowel(<char> character):
   * 
   * Determines whether a given character is a vowel
   * or not
   * 
   * @param <char> character: the character to evaluate
   * @return <boolean> isVowel: whether or not the character is a vowel
   */
  public static boolean isVowel(char character) {
    return character == 'a' ||
      character == 'e' ||
      character == 'i' ||
      character == 'o' ||
      character == 'u';
  }

  public static void main(String[] args) {
      try (Scanner input = new Scanner(System.in)) {
        
        // get the sentence string from the user
        String sentence = promptMessage(input, "Enter some sentence: ")
          .toLowerCase();

        // keep track of the vowels/consonants
        int vowelCount = 0;
        int consonantCount = 0;

        // iterate over the string and check for vowels/consonants
        for (int i = 0; i < sentence.length(); i++)
          if (isVowel(sentence.charAt(i))) 
            vowelCount++;
          else
            consonantCount++;

        // display the results
        println("Vowels: " + vowelCount);
        println("Consonants: " + consonantCount);

      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
}
