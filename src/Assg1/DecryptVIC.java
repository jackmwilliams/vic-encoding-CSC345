package Assg1;

/*=============================================================================
|   Assignment:  Program #1:  The VIC (VIC InComplete) Cipher
|       Author:  Jack Williams (jackmwilliams@arizona.edu)
|       Grader:  Unsure
|
|       Course:  CSC 345
|   Instructor:  L. McCann
|     Due Date:  19 Sep. 2024, 3:30 pm (submitted 2 days late, by 21 Sep. 2024)
|
|  Description:  The DecryptVIC program uses the VIC InComplete algorithm to
|                decode a message using several values: a date in YYMMDD form,
|                a 10+ letter String phrase, an anagram consisting of 8 letters
|                and 2 spaces, and the numerical message to decode.
|                Using these details, an 8-step decryption process is performed,
|                described in the main() method below. Once a decrypted value is
|                generated, it is printed to the console.
|
|     Language:  Java 17
| Ex. Packages:  Imports: java.util.ArrayList
|                Classes: DecryptVICData.java, VICOperations.java
|                
| Deficiencies:  Not all edge cases have been tested, so there are likely
|                several issues with nonstandard input.
*===========================================================================*/

import java.util.ArrayList;

public class DecryptVIC {
	
	public static void main(String[] args) {
		// Get the input from a file with a DecryptVICData obj, validating:
		DecryptVICData input = DecryptVICData.readVICData(args[0]);
		
		// 1. Extract the ID from the message:
		int idI = Character.getNumericValue(input.date.charAt(5));
		String id, msgMinusID;
		if (idI+5 < input.message.length()) {
			id = input.message.substring(idI, idI+5);
			msgMinusID = input.message.substring(0,idI) +
					input.message.substring(idI+5);
		} else {
			id = input.message.substring(input.message.length()-5);
			msgMinusID = input.message.substring(0, input.message.length()-5);
		}
		
		// 2. Perform no-carry addition on the agent ID and the first five
		// digits of the date:
		String dateIDSum = VICOperations.noCarryAddition(id, input.date.substring(0,5));
		
		// 3. Extend the result of 2. with chain addition:
		String extDateIDSum = VICOperations.chainAddition(dateIDSum, 10);
		
		// 4. Create a digit permutation based on the first 10 letters in the phrase:
		String permutation = VICOperations.digitPermutation(input.phrase.substring(0,10));
		
		// 5. Perform no-carry addition on the results from 3. and 4.:
		String dateIDPermSum = VICOperations.noCarryAddition(extDateIDSum, permutation);
		
		// 6. Create a a digit permutation on the result of 5.:
		String numDigitPerm = VICOperations.digitPermutation(dateIDPermSum);
		
		// 7. Create a straddling checkerboards based off of input anagram and 6.'s result:
		ArrayList<String> checker = VICOperations.straddlingCheckerboard(numDigitPerm, input.anagram);
		
		// 8. Use the straddling checkerboard to decode the message:
		String decoded = decodeWithChecker(msgMinusID, checker);
		
		System.out.println(decoded);
	}
	
	/*---------------------------------------------------------------------
    |  Method decodeWithChecker(String msg, ArrayList<String> checker)
    |
    |  Purpose:  Takes a straddling checkerboard (as an ArrayList) and an
    |      encoded String message, decoding the latter using the former.
    |
    |  Pre-condition:  message is solely numerical characters.
    |      checker is a filled straddling checkerboard.
    |
    |  Post-condition: The decoded String message will be generated and
    |      returned.
    |
    |  Parameters:
    |      String msg -- A String message (numerical) to decode.
    |      ArrayList<String> checker -- The straddling checkerboard,
    |          mapping each letter of the alphabet to one or two numbers,
    |          to decode message with.
    |
    |  Returns:  The input message, decoded using the straddling checkerboard.
    *-------------------------------------------------------------------*/
	private static String decodeWithChecker(String msg, ArrayList<String> checker) {
		String decoded = "";	// destination for decoded msg
		
		// Iterate on encoded message, decoding each char with the checkerboard:
		for (int i = 0; i < msg.length(); i++) {
			// Index of the checkerboard with a match (0,,25), searching first row:
			int matchI = checker.indexOf(msg.charAt(i) + "");
			
			// If not found, then should be on another row (with an index length 2):
			if (matchI == -1) {
				// Get new 2-digit matchI:
				matchI = checker.indexOf(msg.charAt(i) + "" + msg.charAt(i+1));
				
				// If still not found, then a space is getting read, an error:
				if (matchI == -1) {
					System.out.println("Error: Attempted to access a space in checkerboard.");
					System.exit(1);
				}
				
				i++;	// extra iter on i, since we used 2 digits here
			}
			
			decoded += (char)(matchI+'A');	// append index as a letter to decoded
		}
		
		return decoded;
	}
}
