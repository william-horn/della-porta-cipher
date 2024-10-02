/*
 * Author: William Horn
 * Written: 9/6/2024
 * 
 * Compilation: javac WilliamHorn_18.java
 * Execution: java WilliamHorn_18
 * 
 * Purpose:
 * Given integers a_1 through a_5, compute a^(a + 1)
 * 
 * Example:
 * Output -> 
 * 	a	b	pow(a, b)
	1	2	1
	2	3	8
	3	4	81
	4	5	1024
	5	6	15625
 * 
 */

public class WilliamHorn_18 {
	public static void main(String[] args) {
    	// -------------- //
    	// PRINTING TABLE //
    	// -------------- //
        
        // general form
        System.out.println("a\tb\tpow(a, b)");
        
        // print inputs/outputs from 1 to 5
        for (int i = 0; i < 5; i++) {
        	int a = i + 1;
        	int b = a + 1;
        	
        	System.out.println(a + "\t" + b + "\t" + (int) Math.pow(a,  b));
        }
	}

}
