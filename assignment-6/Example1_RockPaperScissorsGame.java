


public class Example1_RockPaperScissorsGame {
  public static void main(String[] args) {
      
    char user = 'p';
    char computer = 'r';

    if (user == computer) {
      System.out.println("It's a tie!");
      System.exit(0);
    }

    switch (user) {
      case 'r' -> {
        if (computer =='p') {
          System.out.println("Computer won!");
        } else if (computer == 's') {
          System.out.println("User won!");
        }
      }

      case 'p' -> {
        if (computer =='s') {
          System.out.println("Computer won!");
        } else if (computer == 'r') {
          System.out.println("User won!");
        }
      }

      case 's' -> {
        if (computer =='r') {
          System.out.println("Computer won!");
        } else if (computer == 'p') {
          System.out.println("User won!");
        }
      }
    }

  }
}
