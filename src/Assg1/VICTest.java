package Assg1;

import java.util.ArrayList;

public class VICTest {

	public static void main(String[] args) {
		// Run all of the tests:
		
		testNoCarryAddition1();
		testNoCarryAddition2();
		
		testChainAddition1();
		
		testDigitPermutation1();
		testDigitPermutation2();
		testDigitPermutation3();
		testDigitPermutation4();
		testDigitPermutation5();
		testDigitPermutation6();
		
		testStraddlingCheckerboard1();
	}
	
	public static void testNoCarryAddition1() {
		String s = VICOperations.noCarryAddition("12345", "67890");
		System.out.println(s);
	}
	public static void testNoCarryAddition2() {
		String s = VICOperations.noCarryAddition("109137", "234206");
		System.out.println(s);
	}
	
	
	public static void testChainAddition1() {
		String s = VICOperations.chainAddition("13579", 20);
		System.out.println(s);
	}
	
	
	public static void testDigitPermutation1() {
		String s = VICOperations.digitPermutation("BANANALAND");
		System.out.println(s);
	}
	public static void testDigitPermutation2() {
		String s = VICOperations.digitPermutation("algalgalg");
		System.out.println(s);
	}
	public static void testDigitPermutation3() {
		String s = VICOperations.digitPermutation("BANANALANDANWEEAFEAEEGAE");
		System.out.println(s);
	}
	public static void testDigitPermutation4() {
		String s = VICOperations.digitPermutation("Pretensest");
		System.out.println(s);
	}
	public static void testDigitPermutation5() {
		String s = VICOperations.digitPermutation("maegfpegadz");
		System.out.println(s);
	}
	public static void testDigitPermutation6() {
		String s = VICOperations.digitPermutation("2527058253");
		System.out.println(s);
	}
	
	
	public static void testStraddlingCheckerboard1() {
		ArrayList<String> l = VICOperations.straddlingCheckerboard("4071826395", "A TIN SHOE");
		System.out.println(l.toString());
	}
}
