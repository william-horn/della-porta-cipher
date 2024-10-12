


public class StringParsing_Part1 {
  public static void main(String[] args) {
    
    String str = "some ordinary string";

    for (int i = 0; i < str.length(); i++) {
      char character = str.charAt(i);

      if (i%2 == 0) {
        System.out.println("Character " + character + " is at position: " + i);
      }

      if (i%3 == 0) {
        System.out.println("Character " + character + " is the next 3rd character in the string!");
      }
    }

    

  }
}
