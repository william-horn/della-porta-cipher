


public class Example2_RockPaperScissorsGame {
  public static final Object[][] GAME_STATES = {
    {"r", 55.5},
    {"p", 56.5},
    {"s", 58.5}
  };

  public static Object[] getGameState(String move) {
    for (Object[] state : GAME_STATES) 
      if (state[0].equals(move)) return state;
 
    return null;
  }

  public static void main(String[] args) {
      
    Object[] user = getGameState("p");
    Object[] computer = getGameState("r");

    if (user[0].equals(computer[0])) {
      System.out.println("It's a tie!");
      System.exit(0);
    }

    String result = "" + (char) ((double) user[1] + (double) computer[1]);

    if (result.equals(user[0])) {
      System.out.println("User won!");
    } else {
      System.out.println("Computer won!");
    }

  }
}
