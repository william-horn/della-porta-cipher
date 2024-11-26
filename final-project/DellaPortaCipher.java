/*
 * Will, Jaylen, Alex 
 */

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DellaPortaCipher {
  /*
   * -------------------
   * | Program Config: |
   * -------------------
   */

  // Constants
  final public static int PORTA_MATRIX_SIZE = 13;
  final public static int PROGRAM_LOG_MAX_PAIRS = 15;

  // File I/O
  final public static String OUTPUT_PATH = "/output.txt";
  final public static String PROGRAM_LOG_PATH = "/programlog.txt";

  final public static String ERROR_PREFIX = "[ ! ] Error: ";
  final public static String DEBUG_PREFIX = "[ ? ] ";
  final public static String WARNING_PREFIX = "[ * ] ";

  // States & Variables
  public static boolean debugMode = false;

  public static ArrayList<String> programLogs = new ArrayList<>();

  /*
   * ---------
   * | Enums |
   * ---------
   */
   public static enum InputValidation 
   {
    VALID,
    INVALID
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
   * printf(<String> template, <Object> ...args):
   * 
   * Shorthand for: System.out.printf(...);
   */
  public static void printf(String template, Object ...args) {
    System.out.printf(template, args);
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
    println(ERROR_PREFIX + message);
  }

  // conditional println() when debug mode is enabled
  public static void debug__println(Object message) {
    if (debugMode) println(DEBUG_PREFIX + message);
  }

  // conditional print() when debug mode is enabled
  public static void debug__print(Object message) {
    if (debugMode) print("\n" + DEBUG_PREFIX + message);
  }

  // conditional printf() when debug mode is enabled
  public static void debug__printf(String template, Object ...args) 
  {
    if (debugMode) {
      printf("\n" + DEBUG_PREFIX + template, args);
    }
  }

  // conditional error() when debug mode is enabled
  public static void debug__error(String message) {
    if (debugMode) error(message);
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
    print("> " + message);
    return input.nextLine();
  }

  /*
   * promptMenu(<Scanner> input, <String> title, <String> promptMessage, String[] choiceArray):
   * 
   * Prompt the user with a list of menu items to choose from.
   * 
   * @param <Scanner> input: The scanner object to prompt the user input
   * @param <String> title: The title of the menu list
   * @param <String> promptMessage: The message that comes before the input cursor
   * @param <String[]> choiceArray: An array of string values to be displayed as menu items
   * @return <int> The menu item (starting at 1)
   */
  public static int promptMenu(Scanner input, String title, String promptMessage, String[] choices)
  {
    // create padding in between | menuTitle |
    int padding = title.length() + 4;
    println("");

    // display the menu title
    println("-".repeat(padding));
    printf("| %s |\n", title);
    println("-".repeat(padding) + "\n");

    // display the menu options
    for (int i = 1; i <= choices.length; i++)
      printf("%s) %s\n", i, choices[i - 1]);

    // collect user input
    println("");
    InputValidation menuChoiceValidation = InputValidation.INVALID;

    int itemNumber = 0; do 
    {
      // display the prompt message
      print("> " + promptMessage);
      String menuChoice = input.nextLine();

      // handle input validation for selecting the menu item
      try 
      {
        // if this fails, go to catch
        itemNumber = Integer.parseInt(menuChoice);

        // if this fails, go to catch and give "out of bounds" error
        if (itemNumber < 1 || itemNumber > choices.length)
          throw new Exception("Out of bounds. Choice must be between 1 and " + choices.length + ", user entered: \"" + itemNumber + "\"");

        // if validation passed, then update the menuChoiceValidation state to VALID
        menuChoiceValidation = InputValidation.VALID;

      // handle caught errors
      } catch (Exception e) 
      {
        addErrorLog("Invalid menu input: " + e.getMessage());
        println("Invalid menu item. Try again.");
      }
    } while (menuChoiceValidation == InputValidation.INVALID);

    return itemNumber;
  }

  /*
   * promptBoolean(<Scanner> input, <String> message):
   * 
   * Prompts the user for a binary choice (yes/no) selection
   * 
   * @param <Scanner> input: The scanner object
   * @param <String> message: The message to prompt for choice selection
   * @return <boolean> answer: A true or false value (true if input = "y", false otherwise)
   */
  public static boolean promptBoolean(Scanner input, String message)
  {
    InputValidation answerValidation = InputValidation.INVALID;

    // begin looped validation checking
    boolean answer = false; do
    {
      // prompt input
      print("> " + message + " (y/n): ");
      String submission = input.nextLine();

      try 
      {
        // if not "y" or "n", throw invalid input error
        if (!(submission.equals("y") || submission.equals("n"))) {
          throw new Exception("Choice must be \"y\" or \"n\", user entered: \"" + submission + "\"");

        // else if "y", then set answer to true
        } else if (submission.equals("y")) {
          answer = true;
        }

        // no condition for "n" because `answer` is false by default
        // at this point the validation passed

        answerValidation = InputValidation.VALID;
      } catch (Exception e) 
      {
        addErrorLog("Invalid boolean input: " + e.getMessage());
        println("Invalid boolean input. Try again.");
      }
    } while (answerValidation == InputValidation.INVALID);

    return answer;
  }

  /*
   * getPath(<String> path):
   * 
   * Get the absolute path based on the PATH_NAME constant defined in the class.
   * 
   * @param path: the relative path to the java file being executed
   * @returns: <String> absolutePath
   */
  public static String getPath(String path) {
    String dir = System.getProperty("user.dir"); 
    return dir + path;
  }

  /*
   * updateLogs(<File> programLogFile, <boolean> append):
   * 
   * Updates a given file for storing program logs with the global
   * programLogs array list entries. This method only writes to a file
   * if `debugMode` is true. Once the file is written to, the global
   * `programLogs` array list will be cleared.
   * 
   * @param <File> programLogFile: The output file of the program logs
   * @param <boolean> append: Determines if the logs will append the updated information
   * or overwrite the existng logs with the new logs. true = append, false = overwrite.
   */
  public static void updateLogs(File programLogFile, boolean append) 
  {
    // ignore this method when not in debugMode
    if (!debugMode) return;

    // all of the current programLogs concatenated together
    String text = "";

    for (String log : programLogs)
      text += log + "\n";

    writeFile(programLogFile, text, append);
    programLogs.clear();
  }

  public static void addErrorLog(String message) {
    programLogs.add(ERROR_PREFIX + message);
  }

  public static void addWarningLog(String message) {
    programLogs.add(WARNING_PREFIX + message);
  }

  public static void setDebugMode(boolean enabled) {
    debugMode = enabled;
  }

  /*
   * getFileSource(<File> file):
   * 
   * Compile all text source lines in the file into one string
   * 
   * @param file: The file object to read and convert to a string
   * @returns: <String> fileSource
   */
  // public static String getFileSource(File file) 
  // {
  //   String source = "";

  //   try (Scanner reader = new Scanner(file)) 
  //   {
  //     while (reader.hasNextLine()) 
  //       source += reader.nextLine() + "\n";

  //     return source;

  //   } catch (IOException e) 
  //   {
  //     debug__error(e.getMessage());
  //     return "READ_ERROR";
  //   }
  // }

  /*
   * establishFile(<File> file, <boolean> required):
   * 
   * Create a file with the given path in the `file` argument, unless it
   * already exists. If the second argument `required` is true, then the
   * program will exit if the file does not exist. Regardless, if a file
   * does not exist but is used in the program, the program will create
   * the file.
   * 
   * @param file: The file path to establish
   * @param required: whether or not the file is required for the program to run
   * @returns: <boolean> newFileWasCreated
   */
  public static boolean establishFile(File file, boolean required) 
  {
    String log;

    try 
    {
      /*
       * check if the file is required and the file doesn't exist
       * if true: create the file and exit
       * if false: continue
       * 
       * If file is required and does not exist, program will create the
       * file and then exit
       */
      if (required && !file.exists()) 
      {
        log = "Required file '%s' does not exist (creating blank txt file)";

        debug__printf(log + "\n", file.getName());
        programLogs.add(log.replaceAll("%s", file.getName()));

        file.createNewFile();
        System.exit(0);
      }

      // create the file or do nothing if it already exists
      boolean fileCreated = file.createNewFile();

      // if the file was created, then announce new file name
      if (fileCreated) 
      {
        log = "New file created: %s";

        debug__printf(log + "\n", file.getName());
        programLogs.add(log.replaceAll("%s", file.getName()));

        return true;
      }

      // if file was not created, announce that it already exists
      log = "File '%s' already exists";

      debug__printf(log + "\n", file.getName());
      programLogs.add(log.replaceAll("%s", file.getName()));

      return false;

    } catch(IOException e) 
    {
      debug__error(e.getMessage());
      addErrorLog("Issue establishing file: " + e.getMessage());

      return false;
    }
  }

  public static void writeFile(File file, String text, boolean append) 
  {
    // write the new modified input source to the output file
    try 
    {
      // create the file writer & buffered writer to write to the output file
      FileWriter fw = new FileWriter(file.getAbsoluteFile(), append);

      // create BufferWriter try-with-resources block to auto-close connection
      try (BufferedWriter bw = new BufferedWriter(fw)) {
        // write to the output file and close the connection
        bw.write(text);
        // bw.flush();

      } catch (IOException e) {
        addErrorLog("Issue writing to file: " + e.getMessage());
        debug__error(e.getMessage());
      }
    } catch (IOException e) {
      addErrorLog("Issue creating FileWriter: " + e.getMessage());
      debug__error(e.getMessage());
    }
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
   *    {'h', 'b'}, 
   *    {'e', 'o'}, 
   *    {'l', 'x'}, 
   *    {'l', 'b'}, 
   *    {'o', 'o'}
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
    programLogs.add("Mapping keyword letters to phrase letters...\n");

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
     * in the keyword if we encounter a non-alphabet character in the phrase. A keyword letter should not
     * map to a non-alphabet character. Non-alphabet characters are still added to the array, but are not included
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

      if (phraseIndex < PROGRAM_LOG_MAX_PAIRS)
        programLogs.add("{ " + phraseLetter + ", " + keywordLetter + " }");
      else if (phraseIndex == PROGRAM_LOG_MAX_PAIRS)
        programLogs.add("..." + (phraseLength - PROGRAM_LOG_MAX_PAIRS));
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

    return letterIndex/2;
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

    char encryptedLetter;

    // calculate the compliment character of phraseLetter
    if (phraseLetter < 'n')
      encryptedLetter = (char) (('a' + PORTA_MATRIX_SIZE) + ((phraseLetter - 'a') + portaRowIndex)%PORTA_MATRIX_SIZE);
    else
      encryptedLetter = (char) ('a' + (PORTA_MATRIX_SIZE - (('z' - phraseLetter) + portaRowIndex)%PORTA_MATRIX_SIZE) - 1);

    return encryptedLetter;
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
    programLogs.add("Preparing to convert phrase...");

    // create the keyword/phrase letter pairs
    char[][] keywordPhrasePairs = getKeywordPhrasePairs(phrase, keyword);

    // the final encrypted/decrypted result
    String text = "";

    programLogs.add("\nPreparing to iterate over keywordPhrasePairs to find letter compliments...\n");

    for (int i = 0; i < keywordPhrasePairs.length; i++) 
    {
      // extract the mapped phrase and keyword letters from each row
      char[] row = keywordPhrasePairs[i];

      char phraseLetter = row[0];
      char keywordLetter = row[1];

      // get the compliment of a given phrase character based on it's corresponding keyword character
      char encryptedLetter = getPortaCompliment(phraseLetter, keywordLetter);
      text += encryptedLetter;

      if (i < PROGRAM_LOG_MAX_PAIRS)
        programLogs.add(phraseLetter + " -> " + encryptedLetter);
      else if (i == PROGRAM_LOG_MAX_PAIRS)
        programLogs.add("..." + (keywordPhrasePairs.length - PROGRAM_LOG_MAX_PAIRS));
    }

    programLogs.add("");
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
    if (text.trim().equals("")) {
      addErrorLog("Text is empty: ''");
      return false;
    }

    // if the string contains non-alphabet characters, invalidate the string
    for (int i = 0; i < text.length(); i++) {
      char letter = text.charAt(i);
      if (!Character.isLetter(letter)) {
        addErrorLog("Found non-alphabet character in text: '" + letter + "'");
        return false;
      }
    }

    // if all else, validate the string
    return true;
  }
 
  public static void main(String[] args) 
  {
    File outputFile = new File(getPath(OUTPUT_PATH));
    File programLogFile = new File(getPath(PROGRAM_LOG_PATH));

    try (Scanner input = new Scanner(System.in)) 
    {
      main: while (true) 
      {
        /*
         * FILE WRITING:
         * 
         * Create an output file that contains progress logs as the program ran or
         * is running. An output file will only be updated or generated if the
         * algorithm is running in debug mode
         */
        // reset debug mode to factory
        setDebugMode(false);

        // clear old logs and begin new logs
        programLogs.clear();

        // create or locate necessary files
        establishFile(outputFile, false);
        establishFile(programLogFile, false);

        // user input parameters
        println("\n======= DELLA PORTA CIPHER ===========================\n");
        programLogs.add("Waiting for user input...");
        updateLogs(programLogFile, true);
        setDebugMode(promptBoolean(input, "Run in debug mode"));

        // overwrite programLogFile with new beginning logs
        if (debugMode) {
          boolean resetProgramLogs = promptBoolean(input, "Reset program logs");

          writeFile(
            programLogFile, 
            "\n----- START ----------------------------\n\n",
            !resetProgramLogs
          );
        }

        programLogs.add("Set debug mode to: " + debugMode);
        updateLogs(programLogFile, true);

        println("");

        String phrase = promptMessage(input, "Enter phrase: ");
        programLogs.add("Set phrase to: \"" + phrase + "\"");
        updateLogs(programLogFile, true);

        String keyword = promptMessage(input, "Enter keyword: ");
        programLogs.add("Set keyword to: \"" + keyword + "\"");

        updateLogs(programLogFile, true);

        println("\n======================================================\n");

        // input validation
        if (!containsExclusivelyLetters(keyword)) {
          addErrorLog("Invalid keyword");
          updateLogs(programLogFile, true);
          error("Keyword must be a single word containing only alphabet characters\n");
          System.exit(0);
        }

        // generate the output text
        String output = convertPortaCipher(phrase, keyword).toUpperCase();
        println("=> Output: " + output);

        // write to output file
        writeFile(outputFile, output, false);

        // open the output and programlog files
        try {
          if (debugMode) {
            Desktop.getDesktop().open(programLogFile);
          }

          Desktop.getDesktop().open(outputFile);
        } catch (IOException e) {
          error("Could not open output file");
        }

        // end of the main program logic
        programLogs.add("END");
        programLogs.add("\nFor more info, visit: https://will-blog-sigma.vercel.app/");
        updateLogs(programLogFile, true);

        // prompt the user with some end-of-run options
        int menuItem = promptMenu(
          input, 
          "Options:",
          "Choose item number: ",
          new String[] {
            "Run Again",
            "Exit"
          }
        );

        // if the user choose to exit the program
        switch (menuItem) {
          case 2:
            break main;
        }
      }
    }
  }
}
