
class StringTest {
  public static void print(Object... objects) {
    String s = "";
    for (Object obj : objects) s += obj.toString();

    System.out.println(s);
  }
  public static void main(String[] args) {
      String str1 = "Testing Java";

      // for (int i = 0; i < str1.length(); i++) {
      //   System.out.println(str1.matches(str1.indexOf(i)));
      // }
      
  }
}
