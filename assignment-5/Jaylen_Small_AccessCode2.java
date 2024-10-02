public class Jaylen_Small_AccessCode2 {
  public static void main(String[] args) {
      char one = (char)(Math.random() * 26 + 'A');
      char two = (char)(Math.random() * 26 + 'A');
      char three = (char)(Math.random() * 26 + 'A');
      char four = (char)(Math.random() * 26 + 'A');
      char five = (char)(Math.random() * 26 + 'A');

      // before
      System.out.println(one);
      System.out.println(two);
      System.out.println(three);
      
      // after
      String newCode = "";

      newCode += one;
      newCode += two;
      newCode += three;

      System.out.println(newCode);


      char six = (char)(Math.random() * 26 + 'A');
      char seven = (char)(Math.random() * 26 + 'A');
      char eight = (char)(Math.random() * 26 + 'A');
      char nine = (char)(Math.random() * 26 + 'A');
      char ten = (char)(Math.random() * 26 + 'A');


      char eleven = (char)(Math.random() * 26 + 'A');
      char twelve = (char)(Math.random() * 26 + 'A');
      char thirteen = (char)(Math.random() * 26 + 'A');
      char forteen = (char)(Math.random() * 26 + 'A');
      char fifteen = (char)(Math.random() * 26 + 'A');


      char sixteen = (char)(Math.random() * 26 + 'A');
      char seventeen = (char)(Math.random() * 26 + 'A');
      char eighteen = (char)(Math.random() * 26 + 'A');
      char nineteen = (char)(Math.random() * 26 + 'A');
      char twenty = (char)(Math.random() * 26 + 'A');


      char twentyOne = (char)(Math.random() * 26 + 'A');
      char twentyTwo = (char)(Math.random() * 26 + 'A');
      char twentyThree = (char)(Math.random() * 26 + 'A');
      char twentyFour = (char)(Math.random() * 26 + 'A');
      char twentyFive = (char)(Math.random() * 26 + 'A');

      String code = (one + two + three + four + five + "-" 
      + six + seven + eight + nine + ten + "-" 
      + eleven + twelve + thirteen + forteen + fifteen + "-"
      + sixteen + seventeen + eighteen + nineteen + twenty + "-" 
      + twentyOne + twentyTwo + twentyThree + twentyFour + twentyFive);

      System.out.println("Random Access Code");
      System.out.println(code);
  }
}