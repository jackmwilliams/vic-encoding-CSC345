package Assg1;

/*+----------------------------------------------------------------------
||
||  Class VICOperations
||
||         Author:  Jack Williams
||
||        Purpose:  The VICOperations class exists to perform several
||                  operations to assist with the VIC InComplete cipher
||                  algorithm, 4 in total. These are described below.
||
||  Inherits From:  None.
||
||     Interfaces:  None.
||
|+-----------------------------------------------------------------------
||
||      Constants:  None.
||
|+-----------------------------------------------------------------------
||
||   Constructors:  None (default constructor only; all methods are static).
||
||  Class Methods:  static String noCarryAddition(String num1, String num2)
||                  static String chainAddition(String toExtend, int len)
||                  static String digitPermutation(String toPermute)
||                  static ArrayList<String? straddlingCheckerboard(
||                      String permutation, String anagram)
||
||  Inst. Methods:  None.
||
++-----------------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Arrays;

public class VICOperations {
	
	 /*---------------------------------------------------------------------
    |  Method noCarryAddition(String num1, String num2)
    |
    |  Purpose:  Performs no-carry addition on two decimal numbers
    |      (Strings num1 and num2), returning the sum.
    |      No-carry addition simply adds two numbers without
    |      carrying between places.
    |
    |  Pre-condition:  num1 and num2 are composed fully of numerical
    |      characters.
    |
    |  Post-condition: The returned value will be the no-carry sum
    |      of the two input numbers.
    |
    |  Parameters:
    |      String num1 -- The first number to add, as a String.
    |      String num2 -- The second number to add, as a String.
    |
    |  Returns:  The no-carry sum of the two input numbers.
    *-------------------------------------------------------------------*/
	public static String noCarryAddition(String num1, String num2) {
		String sum = "";		// the string to hold the sum
		
		while (num1.length()>0 || num2.length()>0) {
			// If both nums have chars left, perform normal no-carry addition:
			if (num1.length()>0 && num2.length()>0) {
				// Get the normal sum of the two least significant digits:
				String num1Digit = String.valueOf(num1.charAt(num1.length()-1));
				String num2Digit = String.valueOf(num2.charAt(num2.length()-1));
				int digitSum = Integer.parseInt(num1Digit)
						+ Integer.parseInt(num2Digit);
				
				// Determine and add the no-carry digit to the running sum:
				sum = String.valueOf(digitSum%10) + sum;
				
				// Remove the read least significant digits from both inputs:
				num1 = num1.substring(0, num1.length()-1);
				num2 = num2.substring(0, num2.length()-1);
			} else {	// if one num is depleted
				sum = num1 + num2 + sum;	// just add the full strings now
				num1 = "";
				num2 = "";
			}
		}
		
		return sum;
	}
	
	/*---------------------------------------------------------------------
    |  Method chainAddition(String toExtend, int len)
    |
    |  Purpose:  Takes a String consisting solely of numerical values,
    |      performs chain addition to extend it to the input len,
    |      and returns the extended result.
    |      Chain addition works by taking the earliest consecutive pair of
    |      digits in the number that has not yet been compared,
    |      taking the no-carry sum of them, and adding them to the end
    |      of the number, repeating until len is reached.
    |
    |  Pre-condition:  toExtend is a String made up solely of numerical
    |      characters, and has length >= 2. len is an integer >= 2.
    |
    |  Post-condition: The returned value will be an extended numerical
    |      String of length len, representing the value after chain
    |      addition is performed.
    |
    |  Parameters:
    |      String toExtend: The numerical String to extend with chain
    |          addition.
    |      int len: The length to extend to.
    |
    |  Returns:  The generated extended number (as a String) of size len,
    |      being the result of chain addition.
    *-------------------------------------------------------------------*/
	public static String chainAddition(String toExtend, int len) {
		String extended = toExtend;		// extended chain sum
		
		// End if len already reached:
		if (extended.length() >= len) {
			return extended.substring(0, len);
		}
		
		// Perform chain addition until length is reached:
		while (extended.length() < len) {
			// Extract the first two digits from toExtend:
			int digit1 = Integer.parseInt(toExtend.substring(0,1));
			int digit2 = Integer.parseInt(toExtend.substring(1,2));
			
			toExtend = toExtend.substring(1);	// remove most significant digit
			
			// Perform no-carry addition on digits and add to extension:
			String newDigit = String.valueOf((digit1+digit2)%10);
			toExtend += newDigit;
			extended += newDigit;
		}
		
		return extended;
	}
	
	/*---------------------------------------------------------------------
    |  Method digitPermutation(String toPermute)
    |
    |  Purpose:  This method performs digit permutation on the first 10 digits
    |      of the accepted String, returning this permutation.
    |      Digit permutation assigns each index in a String a number,
    |      based on that char's ASCII value, ascending (or position,
    |      in case of a tie).
    |
    |  Pre-condition:  toPermute should contain at least 10 chars.
    |
    |  Post-condition: The returned value will be the digit permutation
    |      of the first 10 digits of the input String.
    |
    |  Parameters:
    |      String toPermute -- The String to permute (the first 10 chars of).
    |
    |  Returns:  The digit permutation of the first 10 digits of the accepted
    |      String.
    *-------------------------------------------------------------------*/
	public static String digitPermutation(String toPermute) {
		// Validate input:
		if (toPermute.length() < 10) {	// if too short
			return null;
		} else {
			// Shorten to the first 10 digits and capitalize:
			toPermute = (toPermute.substring(0,10)).toUpperCase();
		}
		
		char[] charsInOrder = toPermute.toCharArray();	// chars to find
		Arrays.sort(charsInOrder);	// sort the chars
		
		int indexFoundAt = -1;	// in loop, index where prev letter was found
		int digitsWritten = 0;	// num of digits written
		String permutation = "__________";	// perm destination, 10 digits
		
		// Iterate on input string, finding chars in ASCII order:
		for (int i = 0; i < 10; i++) {
			char lowChar = charsInOrder[i];	// the lowest character left
			int strI;	// the index of toPermute
			
			// Find strI in toPermute:
			if (i >= 1 && lowChar==charsInOrder[i-1]) {	// if lowChar == prevChar
				strI = toPermute.indexOf(lowChar, indexFoundAt+1);
			} else {	// if this char is first
				strI = toPermute.indexOf(lowChar);
			}
			indexFoundAt = strI;	// set location of found char
			
			// Write val to perm:
			permutation = permutation.substring(0,strI) +
					String.valueOf(digitsWritten) +
					permutation.substring(strI+1);
			digitsWritten++;
		}
		
		return permutation;
	}
	
	/*---------------------------------------------------------------------
    |  Method ArrayList<String> straddlingCheckerboard(String permutation, String anagram)
    |
    |  Purpose:  Generates a straddling checkerboard based on the input anagram and
    |      permutation, returning this as an ArrayList.
    |      A straddling checkerboard assigns numbers to each letter of the alphabet
    |      in a 10x3 pattern (with 4 spaces total). Two spaces are used as indices
    |      to rows 2 and 3 (with row 1 having no prefix), and two spaces are
    |      left over in row 3. Each letter is thus assigned a unique 1- or 2-digit
    |      index in the checkerboard.
    |
    |  Pre-condition:  anagram consists of 10 characters, 8 of which are letters,
    |      the other 2 of which are spaces.
    |      permutation is a 10-digit permutation of {0,1,...,9}.
    |
    |  Post-condition: The returned value will be an ArrayList representing the
    |      checkerboard.
    |
    |  Parameters:
    |      String permutation: A 10-digit permutation of {0,1,...,9}.
    |      String anagram: A 10-char phrase with 2 spaces and 8 letters.
    |
    |  Returns:  An ArrayList<String> representing the checkerboard, with
    |      each unique index as the value, and indices (0,,25) for the letters.
    *-------------------------------------------------------------------*/
	public static ArrayList<String> straddlingCheckerboard(String permutation, String anagram){
		ArrayList<String> checker = new ArrayList<>();	// checkerboard
		char nextLetter = 'A';	// next letter to insert
		int curI = 0;			// current index to insert into checkerboard

		// Find the space indices:
		char sp1 = permutation.charAt(anagram.indexOf(' '));
		char sp2 = permutation.charAt(anagram.lastIndexOf(' '));
		
		// Iterate through each possible index, inserting at each:
		while (curI < 18) {
			char curStartIndex = permutation.charAt(curI%10);	// column index
			
			// Determine if nextLetter is in anagram:
			int letterI = anagram.indexOf(nextLetter);
			if (letterI == -1) {	// letter not in anagram
				if (curI < 10) {	// if on first row
					checker.add(sp1 + String.valueOf(curStartIndex));
				} else {			// if on second row
					checker.add(sp2 + String.valueOf(curStartIndex));
				}
				
				curI++;
			} else {				// letter in anagram
				// Add first row letter index to list:
				checker.add(String.valueOf(permutation.charAt(letterI)));
			}
			
			nextLetter++;
		}
		
		return checker;
	}
}
