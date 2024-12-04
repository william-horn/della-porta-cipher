/*
 * Authors: Will, Jaylen, Alex
 * Written: 11/22/2024
 * 
 * DELLA PORTA CIPHER - "The Shifting Method"
 * 
 * ============
 * --- INFO ---
 * ============
 * 
 * This application takes text input from the user to encrypt
 * or decrypt according to the Della Porta cipher. It has several
 * debug options, and output can be read through the terminal
 * or through the output text files found in the directory that the
 * application was launched from.
 * 
 * NOTE: 
 *    if running on Windows on a system earlier than Windows 11, notepad
 *    may need to be manually navigated to in order to view the output. They 
 *    may also need to be manually closed before re-running the program, to
 *    avoid having several notepad windows at the same time. This is only
 *    a concern if running the program in debug mode.
 * 
 * ==============
 * --- OUTPUT ---
 * ==============
 * 
 * A folder called "DellaPortaOutput-WFT983JKS" will be generated in whatever
 * working directory the application is currently being launched form.
 * 
 * In this folder will be the "decrypted-WFT983JKS.txt" and "programlog-WFT983JKS.txt"
 * files that the application generates. The random alphanumeric characters at the end 
 * are an added security measure as to not over-write any existing files that may exist 
 * under the same directory.
 * 
 * ==============
 * --- CONFIG --- 
 * ==============
 * 
 * debugMode:
 *    Running the application in 'debugMode' will enable the program to perform write
 *    operations to the relevant output text files. When enabled, the application will give
 *    detailed descriptions of it's runtime progress and log them to the "programlog-WFT983JKS.txt"
 *    file.
 * 
 *    When debugMode is enabled, it will also output the decrypted cipher to a 
 *    "decrypted-WFT983JKS.txt" file, in the same directory as the programlog file.
 * 
 *    debugMode is disabled by default.
 * 
 * useConsoleColors:
 *    When enabled, the application will run in "color" mode. This will add text and background
 *    colors to the terminal interface if the terminal can support the ASCII color codes.
 * 
 *    The program does not detect if it is able to run in color mode, so the user must manually
 *    choose to start the program in color mode using the "colors" command line argument. For 
 *    example, this is how you could boot the program in color mode:
 * 
 *      java ../DellaPortaCipher.java colors
 * 
 * PROGRAM_LOG_MAX_PAIRS:
 *    Controls how many keyword/message pairs generate in the "programlog.txt"
 *    output file. For example, if this value was set to 3, a sample of the
 *    output will look something like this:
 * 
 *    {'a', 'g'},
 *    {'h', 'd'},
 *    {'x', 's'}
 *    ...49
 * 
 * 
 */

 // File IO
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class DellaPortaCipher {
  /*
   * -------------------
   * | Program Config: |
   * -------------------
   */

  // Constants
  final public static int PROGRAM_LOG_MAX_PAIRS = 15;

  // File I/O
  final public static String UNIQUE_FILE_NAME_KEY = "WFT983JKS";

  final public static String OUTPUT_DIR = "./DellaPortaOutput-" + UNIQUE_FILE_NAME_KEY;
  final public static String OUTPUT_PATH = OUTPUT_DIR + "/decrypted-" + UNIQUE_FILE_NAME_KEY + ".txt";
  final public static String PROGRAM_LOG_PATH = OUTPUT_DIR + "/programlog-" + UNIQUE_FILE_NAME_KEY + ".txt";

  // Output prefixes
  final public static String ERROR_PREFIX = "$text-red [ ! ] Error: ";
  final public static String DEBUG_PREFIX = "$text-cyan [ ? ] ";
  final public static String WARNING_PREFIX = "$text-yellow [ * ] ";

  // Other
  final public static String WEBSITE_LINK = "https://della-porta-cipher.vercel.app/";

  // Themes
  final public static String[][] DEFAULT_THEME = 
  {
    {"error", "$bg-black", "$text-white"},
    {"warn", "$bg-blue", "$text-black"}
  };

  /*
   * -----------------------
   * | States & Variables: |
   * -----------------------
   */

  // String patterns
  final public static String COLOR_TAG_PATTERN = "\\$([a-zA-Z]+)\\-?([a-zA-Z_]*)";

  // Program logs
  final public static ArrayList<String> programLogs = new ArrayList<>();

  // Togglable
  public static boolean debugMode = false;
  public static boolean useConsoleColors = false;

  /*
   * Console colors
   * Source: https://nonvalet.com/posts/20210413_java_console_colors/#:~:text=To%20change%20terminal%20colors%2C%20you,names%20for%20better%20code%20readability.
   */
  final public static String[][] TEXT_COLORS = 
  {
    {"reset", "\u001B[0m"},

    {"black", "\u001B[30m"},
    {"red", "\u001B[31m"},
    {"green", "\u001B[32m"},
    {"yellow", "\u001B[33m"},
    {"blue", "\u001B[34m"},
    {"purple", "\u001B[35m"},
    {"cyan", "\u001B[36m"},
    {"white", "\u001B[37m"},

    {"bright_black", "\u001B[90m"},
    {"bright_red", "\u001B[91m"},
    {"bright_green", "\u001B[92m"},
    {"bright_yellow", "\u001B[93m"},
    {"bright_blue", "\u001B[94m"},
    {"bright_purple", "\u001B[95m"},
    {"bright_cyan", "\u001B[96m"},
    {"bright_white", "\u001B[97m"},
  };

  final public static String[][] BG_COLORS = 
  {
    {"black", "\u001B[40m"},
    {"red", "\u001B[41m"},
    {"green", "\u001B[42m"},
    {"yellow", "\u001B[43m"},
    {"blue", "\u001B[44m"},
    {"purple", "\u001B[45m"},
    {"cyan", "\u001B[46m"},
    {"white", "\u001B[47m"},

    {"bright_black", "\u001B[100m"},
    {"bright_red", "\u001B[101m"},
    {"bright_green", "\u001B[102m"},
    {"bright_yellow", "\u001B[103m"},
    {"bright_blue", "\u001B[104m"},
    {"bright_purple", "\u001B[105m"},
    {"bright_cyan", "\u001B[106m"},
    {"bright_white", "\u001B[107m"}
  };


  /*
   * ===============================
   * | ---------- ENUMS ---------- |
   * ===============================
   */
  
  public static enum InputValidation 
  {
    VALID,
    INVALID
  }

  /*
   * ================================================
   * | ---------- STRING & REGEX METHODS ---------- |
   * ================================================
   */

  /*
   * substituteColors() with default: <String[][]> theme, <boolean> reset
   */
  public static String substituteColors(String source) {
    return substituteColors(DEFAULT_THEME, source);
  }

  /*
   * substituteColors() with default: <boolean> reset
   */
  public static String substituteColors(String[][] theme, String source) {
    return substituteColors(theme, source, true);
  }

  /*
   * substituteColors(<String[][]> theme, String source, <boolean> reset):
   * 
   * Takes an input string and parses it for the tokens: "$colorType-colorValue"
   * or "$colorSet". Replaces those tokens with their respective color values inside the
   * TEXT_COLORS and BG_COLORS arrays, and then substitutes them in the newly built 
   * string.
   * 
   */
  public static String substituteColors(String[][] theme, String source, boolean reset) 
  {
    if (source == null) return "";

    // escape the $ symbol with '/'
    source = escapeColorTag(source);

    Pattern colorTag = Pattern.compile(COLOR_TAG_PATTERN);
    Matcher matcher = colorTag.matcher(source);

    boolean initialMatch = matcher.find();
    int sourceLen = source.length();
    int lastStart = 0;

    String build = "";
    
    if (initialMatch) {
      do {
        // find the match start/end index
        int start = matcher.start();
        int end = matcher.end() + 1;

        /*
         * get the different captures
         * 
         * ex: $bg-black
         *    capture 1: "bg"
         *    capture 2: "black"
         */
        String colorType = matcher.group(1);
        String colorValue = matcher.group(2);

        /*
         * if no capture 2 (ex: $something):
         *    - capture 1 is treated as a lookup key in `theme`
         * 
         *    - if found, then return the data associated with the key
         *        * data in form: { "key", "bg-color", "text-color" }
         * 
         *    - recursively call method for "bg-color" and "text-color" to retrieve
         *      their values.
         */
        if (colorValue.equals("")) 
        {
          // get theme data in form: { "key", "bg-color", "text-color" }
          String[] themeTokenData = getThemeToken(theme, colorType);

          colorType = "theme";

          // replace "bg-color" and "text-color" with their literal console colors, and combine them
          String bgColor = substituteColors(theme, themeTokenData[1], false);
          String textColor = substituteColors(theme, themeTokenData[2], false);

          // check if either of them is missing in the theme declaration
          boolean bgExists = !bgColor.equals("");
          boolean textExists = !textColor.equals("");

          // bg-color by default
          colorValue = bgColor;

          // if both exist, combine them
          if (bgExists && textExists) {
            colorValue += textColor;

          // if only text-color exists, then re-assign to text-color
          } else if (textExists) {
            colorValue = textColor;
          }
        }

        // switch color list based on color type
        switch (colorType) {
          case "bg": { colorValue = getColor(BG_COLORS, colorValue); break; }
          case "text": { colorValue = getColor(TEXT_COLORS, colorValue); break; }
        }

        // append the string leading up to the match, plus the replaced match value
        build += source.substring(lastStart, start) + colorValue;

        // update the next starting index
        lastStart = end;

      } while (matcher.find());
    }

    // final case, if there is any remaining part of the string after the last match
    if (lastStart < sourceLen)
      build += source.substring(lastStart, sourceLen);

    // return final build and append reset, so colors don't carry over to the next print
    return unescapeColorTag(build) 
      + (reset ? getColor(TEXT_COLORS, "reset") : "");
  }

  /*
   * escapeColorTag(<String> str):
   * 
   * Escapes the "$colorType-colorValue" tokens in
   * a given string by using the "/" character
   * 
   * Ex:
   *    escapeColorTag("/$text-green hello world") -> "\0esc hello world"
   */
  public static String escapeColorTag(String str) {
    return str.replaceAll("/\\$", "\0esc");
  }

  /*
   * unescapeColorTag(<String> str):
   * 
   * Unescapes an already escaped string and returns the 
   * intended text.
   * 
   * Ex:
   *    unescapeColorTag(escapeColorTag("/$text-green hello world")) -> "$text-green hello world"
   */
  public static String unescapeColorTag(String str) {
    return str.replaceAll("\0esc", "\\$");
  }

  /*
   * replaceColorTags(<Object> message):
   * 
   * Replaces all "$colorType-colorValue" tokens with an
   * empty string - used when the system does not support
   * console colors
   */
  public static String replaceColorTags(Object message) {
    String text = "" + message;
    return text.replaceAll(COLOR_TAG_PATTERN + " ", "");
  }

  /*
   * =======================================
   * | ---------- PRINT METHODS ---------- |
   * =======================================
   */

  /*
   * println(<Object> message):
   * 
   * Shorthand for: System.out.println(...);
   */
  public static void println(Object message) 
  {
    if (useConsoleColors)
      System.out.println(substituteColors("" + message));
    else
      System.out.println(replaceColorTags(message));
  }

  /*
   * print(<Object> message):
   * 
   * Shorthand for: System.out.print(...);
   */
  public static void print(Object message) 
  {
    if (useConsoleColors)
      System.out.print(substituteColors("" + message));
    else 
      System.out.print(replaceColorTags(message));
  }

  /*
   * printf(<String> template, <Object> ...args):
   * 
   * Shorthand for: System.out.printf(...);
   */
  public static void printf(String template, Object ...args) 
  {
    if (useConsoleColors)
      System.out.printf(substituteColors("" + template), args);
    else
      System.out.printf(replaceColorTags(template), args);
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

  /*
   * ========================================
   * | ---------- PROMPT METHODS ---------- |
   * ========================================
   */

  /*
   * promptMessage(<Scanner> input, <String> message):
   * 
   * A combination of printing and inline message with
   * prompting 
   * 
   * @param <Scanner> input: The scanner object to prompt the user input
   * @param <String> message: The message to display before requesting the input
   */
  public static String promptMessage(Scanner input, String message, String def) 
  {
    print("> $text-green " + message);
    String submission = input.nextLine();

    // Handle default case
    if (submission.equals("")) {
      print("$text-purple default: $text-reset " + def + "\n\n");
      return def;
    }

    return submission;
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
  public static int promptMenu(Scanner input, String title, String promptMessage, int def, String[] choices)
  {
    // create padding in between | menuTitle |
    int padding = title.length() + 4;
    println("");

    // display the menu title
    println("$text-blue " + "-".repeat(padding));
    printf("$text-blue | $text-white %s$text-blue  |\n", title);
    println("$text-blue " + "-".repeat(padding) + "\n");

    // display the menu options
    for (int i = 1; i <= choices.length; i++)
      printf("$text-yellow %s)$text-reset  %s\n", i, choices[i - 1]);

    // collect user input
    println("");
    InputValidation menuChoiceValidation = InputValidation.INVALID;

    if (def > choices.length || def < 1) {
      addErrorLog("Default value to menu prompt must be in choice list range");
      error("Invalid menu default");
      return -1;
    }

    int itemNumber = 0; do 
    {
      // display the prompt message
      print("> $text-green " + promptMessage);
      String menuChoice = input.nextLine();
      
      // Handle the default case
      if (menuChoice.equals("")) {
        return def;
      }

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
  public static boolean promptBoolean(Scanner input, String message, boolean def)
  {
    InputValidation answerValidation = InputValidation.INVALID;

    // begin looped validation checking
    boolean answer = def; do
    {
      // prompt input
      print("> $text-green " + message + " (y/n/Enter): ");
      String submission = input.nextLine();

      try 
      {
        // Update answer according to input
        if (submission.equals("y")) {
          answer = true;

        } else if (submission.equals("n")) {
          answer = false;

        } else if (submission.equals("")) {
          programLogs.add("Choosing DEFAULT: " + def);
          println("$text-purple default: $text-reset " + def);
          
        } else {
          throw new Exception("Choice must be \"y\" or \"n\", user entered: \"" + submission + "\"");
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
   * ======================================
   * | ---------- FILE METHODS ---------- |
   * ======================================
   */


  /*
   * getOutputDir():
   * 
   * Locate the main program output directory. If one doesn't
   * exist, then create a new one and return it.
   */
  public static File getOutputDir() 
  {
    File outputDir = new File(OUTPUT_DIR);
    // Locate or create output folder directory
    if (!outputDir.exists()) {
      outputDir.mkdir();
    }

    return outputDir;
  }

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
      // ensure the output directory always exists
      getOutputDir();

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
        programLogs.add(log.replaceAll("%s", file.getName()));

        return true;
      }

      // if file was not created, announce that it already exists
      log = "File '%s' already exists";
      programLogs.add(log.replaceAll("%s", file.getName()));

      return false;

    } catch(IOException e) 
    {
      addErrorLog("Issue establishing file: " + e.getMessage());
      return false;
    }
  }

  /*
   * writeFile(<File> file, <String> text, <boolean> append):
   * 
   * Write text to a file
   * 
   * @param <File> file: The File object to write to
   * @param <String> text: The text to write
   * @param <boolean> append: 
   *    Whether to append the text or overwrite the 
   *    pre-existing text (true = append, false = overwrite)
   */
  public static void writeFile(File file, String text, boolean append) 
  {
    // write the new modified input source to the output file
    try 
    {
      establishFile(file, false);

      // create the file writer & buffered writer to write to the output file
      FileWriter fileWriter = new FileWriter(
        file.getAbsoluteFile(), 
        append
      );

      // create BufferWriter try-with-resources block to auto-close connection
      try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
        // write to the output file and close the connection
        bufferedWriter.write(text);
        // writer.flush();

      } catch (IOException e) {
        addErrorLog("Issue writing to file: " + e.getMessage());
      }
    } catch (IOException e) {
      addErrorLog("Issue creating FileWriter: " + e.getMessage());
    }
  }


  /*
   * ===========================================
   * | ---------- ALGORITHM METHODS ---------- |
   * ===========================================
   */


  /*
   * getKeywordMessagePairs(<String> keyword, <String> message):
   * 
   * Creates a 2D character array, mapping each letter in the given message
   * to the next character in the keyword sequence.
   * 
   * Ex: 
   *  message = "hello"
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
   * Note: The first array entry of each row is the message letter, while the
   * second entry is the keyword letter.
   * 
   * @param <String> keyword: The keyword to maps to the message letters
   * @param <String> message: The message that maps to the keyword letters
   * @return <char[][]> getKeywordMessagePairs: The 2D array containing each pair of keyword letters mapping to message letters
   */
  public static char[][] getKeywordMessagePairs(String message, String keyword)
  {
    // Initializes the keyword string, where the keyword will be copied into it until the string length is equal to that of the message string
    String keywordString = "";

    char[][] keywordPairs = new char[message.length()][2];

    programLogs.add("Preparing to create keyword/message pairs array...\n");

    keyword = keyword.toLowerCase();
    message = message.toLowerCase();

    // For loop that copies the original keyword into the keyword string that is the same length of the original message
    for (int i = 0; i < message.length();){
      for (int j = 0; j < keyword.length() + 2;){
        // If the keyword string length is equal to the message length, the loop breaks
        if (keywordString.length() == message.length()){
          break;
        }

        /* Checks to see if the character is a letter, if not, 
        it does not need to be encrypted and is copied directly from the message string into
        the keyword string */
        // Otherwise the keyword character at "j" position is copied into the keyword string and increments the "j" variable
        if (!Character.isLetter(message.charAt(i))){
          keywordString += message.charAt(i);
        } else {
          keywordString += keyword.charAt(j);
          j++;
        }

        // If variable j is equal to the keyword length, it is reset so it can start from the begining of the keyword
        if (j == keyword.length()){
          j = 0;
        }
        // Variable "i" is always incremented through each loop no matter what
        i++;
      }
    }

    // For loop that pairs the characters of the keywordString and the message into an array
    for (int i = 0; i < keywordPairs.length; i++)
    {
      // Copies each letter of the message string along the first column
      keywordPairs[i][0] = message.charAt(i);
      // Copies each letter of the keyword string string along the second column
      keywordPairs[i][1] = keywordString.charAt(i);

      // Update the program logs accordingly
      if (i < PROGRAM_LOG_MAX_PAIRS)
        programLogs.add("{ " + keywordPairs[i][0] + ", " + keywordPairs[i][1] + " }");
      else if (i == PROGRAM_LOG_MAX_PAIRS)
        programLogs.add("..." + (keywordPairs.length - PROGRAM_LOG_MAX_PAIRS));
    }

    return keywordPairs;
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
   * getPortaCompliment(<char> keywordLetter, <char> messageLetter):
   * 
   * Calculate the porta compliment of a given letter. That is, given a
   * message letter and a keyword letter, find the letter that corresponds
   * with the message letter on the porta chart.
   * 
   * Ex:
   *  getPortaCompliment('n', 'e') -> 'x'
   *  getPortaCompliment('a', 'z') -> 'm'
   * 
   * @param <char> keywordLetter: The keyword letter, representing which row on the porta chart to index
   * @param <char> messageLetter: The message letter, representing the column on the porta chart
   * @return <char> letter: The compliment of the message letter
   */
  public static char getPortaCompliment(char messageLetter, char keywordLetter) 
  {
    // if the keyword is non-alphabetic, then just return the character
    if (!Character.isLetter(keywordLetter)) return keywordLetter;

    // get the row index of the keyword letter
    int portaRowIndex = getPortaRowIndexOf(keywordLetter);

    char encryptedLetter;

    // calculate the compliment character of messageLetter
    if (messageLetter < 'n')
      encryptedLetter = (char) (('a' + 13) + ((messageLetter - 'a') + portaRowIndex)%13);
    else
      encryptedLetter = (char) ('a' + (12 - (('z' - messageLetter) + portaRowIndex)%13));

    return encryptedLetter;
  }

  /*
   * convertPortaCipher(<String> message, <String> keyword):
   * 
   * Encrypt or decrypt a given message string using the porta cipher encryption rules. 
   * 
   * @param <String> message: The string to encrypt or decrypt
   * @param <String> keyword: The keyword that determines the encryption or decryption
   * @return <String> text: The encrypted or decrypted message string
   */
  public static String convertPortaCipher(String message, String keyword) 
  {
    programLogs.add("Preparing to convert message...");

    // create the keyword/message letter pairs
    char[][] keywordMessagePairs = getKeywordMessagePairs(message, keyword);

    // the final encrypted/decrypted result
    String text = "";

    programLogs.add("\nPreparing to iterate over keywordMessagePairs to find letter compliments...\n");

    for (int i = 0; i < keywordMessagePairs.length; i++) 
    {
      // extract the mapped phrase and keyword letters from each row
      char[] row = keywordMessagePairs[i];

      char messageLetter = row[0];
      char keywordLetter = row[1];

      // get the compliment of a given message character based on it's corresponding keyword character
      char encryptedLetter = getPortaCompliment(messageLetter, keywordLetter);
      text += encryptedLetter;

      // Update program log files accordingly
      if (i < PROGRAM_LOG_MAX_PAIRS)
        programLogs.add(messageLetter + " -> " + encryptedLetter);
      else if (i == PROGRAM_LOG_MAX_PAIRS)
        programLogs.add("..." + (keywordMessagePairs.length - PROGRAM_LOG_MAX_PAIRS));
    }

    programLogs.add("");
    return text;
  }

  /*
   * ================================================
   * | ---------- MISC. UTILITY  METHODS ---------- |
   * ================================================
   */

  /*
   * getColor(<String[][]> colorList, <String> name):
   * 
   * Returns an ASCII console color from a given
   * color list
   */
  public static String getColor(String[][] colorList, String name) 
  {
    for (String[] colorData : colorList)
      if (name.equals(colorData[0])) return colorData[1];

    return "[color not found]";
  }

  /*
   * getThemeToken(<String[][]> theme, String token):
   * 
   * Returns a given theme token from a selected
   * theme array
   */
  public static String[] getThemeToken(String[][] theme, String token) 
  {
    for (String[] themeData : theme)
      if (token.equals(themeData[0])) return themeData;

    return new String[] {"null", "", ""};
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
   * or overwrite the existing logs with the new logs. true = append, false = overwrite.
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
    for (int i = 0; i < text.length(); i++) 
    {
      char letter = text.charAt(i);
      if (!Character.isLetter(letter)) {
        addErrorLog("Found non-alphabet character in text: '" + letter + "'");
        return false;
      }
    }

    // if all else, validate the string
    return true;
  }

  /*
   * chooseRandomDefaultMessage():
   * 
   * Returns a random default message to use
   */
  public static String chooseRandomDefaultMessage() 
  {
    String[] choices = {
      "The quick brown fox jumped over the lazy dog",
      "Somewhere over the rainbow",
      "A long time ago, in a galaxy far far away",
      "Lorem ipsum dolor sit amet consectetur adipisicing elit. Repudiandae mollitia, qui iusto voluptatibus provident harum cupiditate ratione debitis natus nobis!",
      "One small step for man, one giant leap for mankind",
      "If I have seen further than others, it is by standing on the shoulders of giants",
      "I see you have opted for a default message. Hello, I am the default message.",
    };

    return choices[(int) (Math.random()*choices.length)];
  }

  /*
   * chooseRandomDefaultKeyword():
   * 
   * Returns a random default keyword to use
   */
  public static String chooseRandomDefaultKeyword() 
  {
    String[] choices = {
      "fox",
      "cloud",
      "star",
      "lorem",
      "moon",
      "science",
      "friend"
    };

    return choices[(int) (Math.random()*choices.length)];
  }
 
  public static void main(String[] args) 
  {
    // Locate output files
    File outputFile = new File(OUTPUT_PATH);
    File programLogFile = new File(PROGRAM_LOG_PATH);

    // Set global command-line settings
    switch (args.length) {
      case 1: {
        useConsoleColors = args[0].equals("colors");
      }
    }

    /*
     * USER INPUT INTERFACE
     */
    try (Scanner input = new Scanner(System.in)) 
    {
      main: while (true) 
      {
        
        // Reset debug mode to factory
        debugMode = false;

        // Clear old logs and begin new logs
        programLogs.clear();

        // User input parameters
        println("\n$text-blue ======= $text-white DELLA PORTA CIPHER $text-blue ===========================$text-reset \n");
        programLogs.add("Waiting for user input...");
        updateLogs(programLogFile, true);

        println(WARNING_PREFIX + "Press 'Enter' without typing a value to choose defaults.\n");
        println(DEBUG_PREFIX + "For more info on settings, read the 'CONFIG' comment at the top of the source code.\n");
        println(DEBUG_PREFIX + "To learn more about the Della Porta cipher, visit our website: " + WEBSITE_LINK + "\n");
        println("$text-blue ======================================================$text-reset \n");


        // prompt for debug mode
        debugMode = promptBoolean(input, "Run in debug mode", false);

        /*
         * ONLY IF `debugMode` is TRUE:
         * 
         * - options:
         *    * resetProgramLogs: 
         *        - if true, then programlogs.txt resets every program re-run 
         *        - if false, then programlogs.txt is appended every program re-run
         */
        if (debugMode) 
        {
          boolean resetProgramLogs = promptBoolean(input, "Reset program logs", true);

          writeFile(
            programLogFile, 
            "\n----- START ----------------------------\n\n",
            !resetProgramLogs
          );
        }

        // Update program log
        programLogs.add("Set debug mode to: " + debugMode);
        updateLogs(programLogFile, true);
        println("");

        // Prompt for message string
        String message = promptMessage(input, "Enter message: ", chooseRandomDefaultMessage());
        programLogs.add("Set message to: \"" + message + "\"");
        updateLogs(programLogFile, true);

        // Prompt for keyword string
        String keyword = promptMessage(input, "Enter keyword: ", chooseRandomDefaultKeyword());
        programLogs.add("Set keyword to: \"" + keyword + "\"");

        // Update program log
        updateLogs(programLogFile, true);
        println("\n$text-blue ------------------------------------------------------$text-reset \n");

        // Input validation for keyword
        if (!containsExclusivelyLetters(keyword)) {
          addErrorLog("Invalid keyword");
          updateLogs(programLogFile, true);
          error("Keyword must be a single word containing only alphabet characters\n");
          System.exit(0);
        }

        // Generate the output text
        String output = convertPortaCipher(message, keyword).toUpperCase();
        println("$text-yellow => Output: $text-reset " + output);

        // Write to output file
        writeFile(outputFile, output, false);

        // Open the output and programlog files
        try {
          if (debugMode) {
            Desktop.getDesktop().open(programLogFile);
            Desktop.getDesktop().open(outputFile);
          }
        } catch (IOException e) {
          error("Could not open output file");
        }

        // End of the main program logic
        programLogs.add("END");
        programLogs.add("\nFor more info, visit: " + WEBSITE_LINK);
        updateLogs(programLogFile, true);

        // Prompt the user with some end-of-run options
        int menuItem = promptMenu(
          input, 
          "Options:",
          "Choose item number: ",
          1,
          new String[] {
            "Run Again",
            "Open Program Logs",
            "Toggle Colors",
            "Exit"
          }
        );

        // if the user choose to exit the program
        switch (menuItem) 
        {
          case 2: {
            try { 
              Desktop.getDesktop().open(programLogFile); 
            } catch (Exception e) { 
              error(e.getMessage());
            }

            break;
          }

          case 3:
            useConsoleColors = !useConsoleColors;
            break;

          case 4:
            break main;
        }
      }
    }
  }
}
