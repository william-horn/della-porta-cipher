


public class Main {
  public static void main(String[] args) {
      /*
       * I am now writing this comment as a means to practice my improvisational skills and spelling,
       * because I really am that bored. There is truly nothing else to be done at this time and I regret 
       * ever taking this position
       */
      for (int i = 0; i < 13; i++) {
          for (int j = 0; j < 13; j++) {
              System.out.print((110 + (j + i)%13) + ((j == 12) ? " " : ", "));
          }
          System.out.println();
      }
  }
}