/*
 * Will Horn
 * 11/9/2024
 */

import java.util.*;

public class DellaPortaCipher {
  final public static int PORTA_MATRIX_SIZE = 13;

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

  /*
   * getKeywordPhrasePairs(<String> keyword, <String> phrase):
   * 
   * Creates a 2D character array, mapping each letter in the given phrase
   * to the next character in the keyword sequence.
   * 
   * Ex: 
   *  Phrase = "hello"
   *  Keyword = "box"
   * 
   *  Output -> {
   *    {'h', 'b'}, {'e', 'o'}, {'l', 'x'}, {'l', b'}, {'o', 'o'}
   *  }
   * 
   * Note: The first array entry of each row is the phrase letter, while the
   * second entry is the keyword letter.
   * 
   * @param <String> keyword: The keyword to maps to the phrase letters
   * @param <String> phrase: The phrase that maps to the keyword letters
   * @return <char[][]> keywordPhrasePairs: The 2D array containing each pair of keyword letters mapping to phrase letters
   */
  public static char[][] getKeywordPhrasePairs(String phrase, String keyword) 
  {
    // convert keyword and phrase to lowercase to make bytecode-checking easier
    keyword = keyword.toLowerCase();
    phrase = phrase.toLowerCase();

    int keywordLength = keyword.length();
    int phraseLength = phrase.length();

    // The 2D array that contains the phrase letter/keyword letter pairs.
    char[][] keywordPhrasePairs = new char[phraseLength][2];

    /*
     * Iterate over the phrase:
     * 
     * `phraseIndex` is automatically incremented, since this is just pulling the next
     * character in the phrase.
     * 
     * `keywordCounter` is manually incremented, because we don't want to grab the next letter
     * in the keyword if we encounter a space character in the phrase. A keyword letter should not
     * map to a space character. Space characters are still added to the array, but are not included
     * in the counting process.
     */
    for (int phraseIndex = 0, keywordCounter = 0; phraseIndex < phraseLength; phraseIndex++) 
    {
      // Reset the keyword index after the keyword length has been exhausted.
      int keywordIndex = keywordCounter % keywordLength;

      // Grab the next phrase letter & keyword letter
      char phraseLetter = phrase.charAt(phraseIndex);
      char keywordLetter = keyword.charAt(keywordIndex);

      // 1) If the phrase letter is non-alphabetic, set the keyword to the phrase character
      // 2) Do not increment the keywordCounter, since we are not including the keyword letter for non-alphabetic characters
      if (!Character.isLetter(phraseLetter))
        keywordLetter = phraseLetter;

      // 1) Otherwise, increment the keywordCounter to grab the next keyword letter during the next iteration.
      // 2) Increment the keywordCounter, since it is an alphabetic character
      else
        keywordCounter++;

      /*
       * Create the phraseLetter/keywordLetter pair as a character array. 
       * This becomes a new row in the 2D `keywordPhrasePairs` array, with
       * the phrase letter being in column 1 and the keyword letter in column 2.
       */
      keywordPhrasePairs[phraseIndex] = new char[] 
      {
        phraseLetter,
        keywordLetter
      };
    }

    return keywordPhrasePairs;
  }

  /*
   * getPortaRowIndexOf(<char> keywordCharacter):
   * 
   * Given a keyword character, convert it to an integer representing the
   * row index of said character in the Porta Cipher table. 
   * 
   * Ex: 
   *  charToPortaRowIndex('a') -> 0
   *  charToPortaRowIndex('b') -> 0
   *  charToPortaRowIndex('c') -> 1
   *  charToPortaRowIndex('d') -> 1
   *  charToPortaRowIndex('e') -> 2
   *  charToPortaRowIndex('f') -> 2
   *  ...
   * 
   * @param <char> character: The keyword character to find the Porta Cipher row index of.
   * @return <int> index: The Porta Cipher row index.
   */
  public static int getPortaRowIndexOf(char character) 
  {
    int bytecode = character;
    int letterIndex = (bytecode - 97);

    return (int) (Math.ceil(letterIndex/2));
  }

  /*
   * getPortaCompliment(<char> keywordLetter, <char> phraseLetter):
   * 
   * Calculate the porta compliment of a given letter. That is, given a
   * phrase letter and a keyword letter, find the letter that corresponds
   * with the phrase letter on the porta chart.
   * 
   * Ex:
   *  getPortaCompliment('n', 'e') -> 'x'
   *  getPortaCompliment('a', 'z') -> 'm'
   * 
   * @param <char> keywordLetter: The keyword letter, representing which row on the porta chart to index
   * @param <char> phraseLetter: The phrase letter, representing the column on the porta chart
   * @return <char> letter: The compliment of the phrase letter
   */
  public static char getPortaCompliment(char phraseLetter, char keywordLetter) 
  {
    // if the keyword is non-alphabetic, then just return the character
    if (!Character.isLetter(keywordLetter)) return keywordLetter;

    // get the row index of the keyword letter
    int portaRowIndex = getPortaRowIndexOf(keywordLetter);

    char letter = phraseLetter < 'n' ?
      // phrase letter is before 'n':
      (char) (('a' + PORTA_MATRIX_SIZE) + ((phraseLetter - 'a') + portaRowIndex)%PORTA_MATRIX_SIZE):

      // phrase letter is including or after n:
      (char) ('a' + (PORTA_MATRIX_SIZE - (('z' - phraseLetter) + portaRowIndex)%PORTA_MATRIX_SIZE) - 1);

    return letter;
  }

  /*
   * convertPortaCipher(<String> phrase, <String> keyword):
   * 
   * Encrypt or decrypt a given phrase string using the porta cipher encryption rules. 
   * 
   * @param <String> phrase: The string to encrypt or decrypt
   * @param <String> keyword: The keyword that determines the encryption or decryption
   * @return <String> text: The encrypted or decrypted phrase string
   */
  public static String convertPortaCipher(String phrase, String keyword) 
  {
    // create the keyword/phrase letter pairs
    char[][] keywordPhrasePairs = getKeywordPhrasePairs(phrase, keyword);

    // the final encrypted/decrypted result
    String text = "";

    for (int i = 0; i < keywordPhrasePairs.length; i++) 
    {
      char[] row = keywordPhrasePairs[i];

      // extract the mapped phrase and keyword letters from each row
      char phraseLetter = row[0];
      char keywordLetter = row[1];

      // get the compliment of a given phrase character based on it's corresponding keyword character
      char encryptedLetter = getPortaCompliment(phraseLetter, keywordLetter);
      text += encryptedLetter;
    }

    return text;
  }

  /*
   * containsExclusivelyLetters(<String> text):
   * 
   * Validates whether or not a given string contains
   * only letters.
   * 
   * @param <String> text: The string to validate
   * @return <boolean> isValid: The validation result
   */
  public static boolean containsExclusivelyLetters(String text) 
  {
    // if the string is blank, invalidate the string
    if (text.trim().equals("")) return false;

    // if the string contains non-alphabet characters, invalidate the string
    for (int i = 0; i < text.length(); i++) 
      if (!Character.isLetter(text.charAt(i))) return false;

    // if all else, validate the string
    return true;
  }
 
  public static void main(String[] args) 
  {
    try (Scanner input = new Scanner(System.in)) 
    {
      // user input parameters
      String phrase = promptMessage(input, "Enter phrase: ");
      String keyword = promptMessage(input, "Enter keyword: ");

      // input validation
      if (!containsExclusivelyLetters(keyword)) {
        error("Keyword must be a single word containing only alphabet characters");
        System.exit(0);
      }

      println(convertPortaCipher(phrase, keyword));
    }
  }
}
