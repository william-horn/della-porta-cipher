/*
 * Will, Jaylen, Alex 
 */

 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.Scanner;
 import java.util.ArrayList;

public class DellaPortaCipher {
  /*
   * -------------------
   * | Program Config: |
   * -------------------
   */

  // Constants
  final public static int PORTA_MATRIX_SIZE = 13;

  final public static String OUTPUT_PATH = "/output.txt";
  final public static String PROGRAM_LOG_PATH = "/programlog.txt";

  // States & Variables
  public static boolean inDebugMode = false;
  public static ArrayList<String> programLogs = new ArrayList<>();

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
    println("[ ! ] ERROR: " + message);
  }

  // conditional println() when debug mode is enabled
  public static void debugPrintln(Object message) {
    if (inDebugMode) println("[ ? ] " + message);
  }

  // conditional print() when debug mode is enabled
  public static void debugPrint(Object message) {
    if (inDebugMode) print("\n[ ? ] " + message);
  }

  // conditional printf() when debug mode is enabled
  public static void debugPrintf(String template, Object ...args) {
    if (inDebugMode) {
      println("");
      printf("[ ? ] " + template, args);
    }
  }

  // conditional error() when debug mode is enabled
  public static void debugError(String message) {
    if (inDebugMode) error(message);
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

    // display the prompt message
    print("\n> " + promptMessage);

    // collect user input
    int itemNumber = input.nextInt();
    input.nextLine();

    return itemNumber;
  }

  public static boolean promptBoolean(Scanner input, String message)
  {
    String submission = promptMessage(input, message + " (y/n): ");
    return submission.toLowerCase().equals("y");
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

  public static void addLog(String message) {
    programLogs.add(message);
  }

  public static void updateLogs(File programLogFile, boolean append) 
  {
    if (!getDebugMode()) return;

    String text = "";

    for (String log : programLogs)
      text += log + "\n";

    writeFile(programLogFile, text, append);
    clearLogs();
  }

  public static void addErrorLog(String message) {
    addLog("ERROR: " + message);
  }

  public static void addWarningLog(String message) {
    addLog("WARNING: " + message);
  }

  public static void clearLogs() {
    programLogs.clear();
  }

  public static ArrayList<String> getLogs() {
    return programLogs;
  }

  public static void setDebugMode(boolean enabled) {
    inDebugMode = enabled;
  }

  public static boolean getDebugMode() {
    return inDebugMode;
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
  //     debugError(e.getMessage());
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
    String log = "";

    try 
    {
      // check if the file is required and the file doesn't exist
      // if true: create the file and exit
      // if false: continue
      if (required && !file.exists()) 
      {
        log = "Required file '%s' does not exist (creating blank txt file)";

        debugPrintf(log + "\n", file.getName());
        addLog(log.replaceAll("%s", file.getName()));

        file.createNewFile();
        System.exit(0);
      }

      // create the file or do nothing if it already exists
      boolean fileCreated = file.createNewFile();

      // if the file was created, then announce new file name
      if (fileCreated) 
      {
        log = "New file created: %s";

        debugPrintf(log + "\n", file.getName());
        addLog(log.replaceAll("%s", file.getName()));

        return true;
      }

      // if file was not created, announce that it already exists
      log = "File '%s' already exists";

      debugPrintf(log + "\n", file.getName());
      addLog(log.replaceAll("%s", file.getName()));

      return false;

    } catch(IOException e) 
    {
      debugError(e.getMessage());
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
        debugError(e.getMessage());
      }
    } catch (IOException e) {
      addErrorLog("Issue creating FileWriter: " + e.getMessage());
      debugError(e.getMessage());
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
    addLog("Mapping keyword letters to phrase letters...\n");

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

      addLog("{ " + phraseLetter + ", " + keywordLetter + " }");
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
    addLog("Preparing to convert phrase...");

    // create the keyword/phrase letter pairs
    char[][] keywordPhrasePairs = getKeywordPhrasePairs(phrase, keyword);

    // the final encrypted/decrypted result
    String text = "";

    addLog("\nPreparing to iterate over keywordPhrasePairs to find letter compliments...\n");

    for (char[] row : keywordPhrasePairs) 
    {
      // extract the mapped phrase and keyword letters from each row
      char phraseLetter = row[0];
      char keywordLetter = row[1];

      // get the compliment of a given phrase character based on it's corresponding keyword character
      char encryptedLetter = getPortaCompliment(phraseLetter, keywordLetter);
      text += encryptedLetter;

      addLog(phraseLetter + " -> " + encryptedLetter);
    }

    addLog("");
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
        setDebugMode(false);
        clearLogs();
        writeFile(programLogFile, "START\n", false);

        // user input parameters
        println("\n======= DELLA PORTA CIPHER ===========================\n");
        addLog("Waiting for user input...");
        updateLogs(programLogFile, true);
        setDebugMode(promptBoolean(input, "Run in debug mode"));

        addLog("Set debug mode to: " + getDebugMode());
        updateLogs(programLogFile, true);

        println("");

        String phrase = promptMessage(input, "Enter phrase: ");
        addLog("Set phrase to: \"" + phrase + "\"");
        updateLogs(programLogFile, true);

        String keyword = promptMessage(input, "Enter keyword: ");
        addLog("Set keyword to: \"" + keyword + "\"");

        establishFile(outputFile, false);
        establishFile(programLogFile, false);

        updateLogs(programLogFile, true);

        println("\n======================================================\n");

        // input validation
        if (!containsExclusivelyLetters(keyword)) {
          addErrorLog("Invalid keyword");
          updateLogs(programLogFile, true);
          error("Keyword must be a single word containing only alphabet characters");
          System.exit(0);
        }

        // generate the output text
        String output = convertPortaCipher(phrase, keyword).toUpperCase();
        println("=> Output: " + output);

        // write to output file
        writeFile(outputFile, output, false);

        // end of the main program logic
        addLog("END");
        addLog("Waiting on user selection...");
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
            println("\nGoodbye!");
            break main;
        }
      }
    }
  }
}
