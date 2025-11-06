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
|  Description:  The EncryptVIC program uses the VIC InComplete algorithm to
|                encode a message using several values: a 5-digit agent ID,
|                a date in YYMMDD form, a 10+ letter String phrase,
|                an anagram consisting of 8 letters and 2 spaces,
|                and the message to encode, keeping just the letters therein.
|                Using these details, an 8-step encryption process is performed,
|                described in the main() method below. Once an encrypted value is
|                generated, it is printed to the console.
|
|     Language:  Java 17
| Ex. Packages:  Imports: java.util.ArrayList
|                Classes: EncryptVICData.java, VICOperations.java
|                
| Deficiencies:  Not all edge cases have been tested, so there are likely
|                several issues with nonstandard input.
*===========================================================================*/

import java.util.ArrayList;

public class EncryptVIC {

	public static void main(String[] args) {
		// Get the input from a file with an EncryptVICData obj, validating:
		EncryptVICData input = EncryptVICData.readVICData(args[0]);

		// 1. Perform no-carry addition on the agent ID and the first five
		// digits of the date:
		String dateIDSum = VICOperations.noCarryAddition(input.agentID, input.date.substring(0,5));
		
		// 2. Extend the result of 1. with chain addition:
		String extDateIDSum = VICOperations.chainAddition(dateIDSum, 10);
		
		// 3. Create a digit permutation based on the first 10 letters in the phrase:
		String permutation = VICOperations.digitPermutation(input.phrase.substring(0,10));
		
		// 4. Perform no-carry addition on the results from 2. and 3.:
		String dateIDPermSum = VICOperations.noCarryAddition(extDateIDSum, permutation);
		
		// 5. Create a a digit permutation on the result of 4.:
		String numDigitPerm = VICOperations.digitPermutation(dateIDPermSum);
		
		// 6. Create a straddling checkerboards based off of input anagram and 5.'s result:
		ArrayList<String> checker = VICOperations.straddlingCheckerboard(numDigitPerm, input.anagram);
		
		// 7. Use the checkerboard to encode the message:
		String msgEncoded = encodeWithChecker(input.message, checker);
		
		// 8. Insert agent ID into encoded message:
		String msgEncodedWithID = insertID(msgEncoded, input.agentID, input.date.charAt(5)-'0');
		
		System.out.println(msgEncodedWithID);
	}
	
	/*---------------------------------------------------------------------
    |  Method encodeWithChecker(String message, ArrayList<String> checker)
    |
    |  Purpose:  Takes a straddling checkerboard (as an ArrayList) and a
    |      String message, encoding the latter using the former.
    |
    |  Pre-condition:  message is solely alphabetical characters.
    |      checker is a filled straddling checkerboard.
    |
    |  Post-condition: The encoded String message will be generated and
    |      returned.
    |
    |  Parameters:
    |      String message -- A String message (alphabetical) to encode.
    |      ArrayList<String> checker -- The straddling checkerboard,
    |          mapping each letter of the alphabet to one or two numbers,
    |          to encode message with.
    |
    |  Returns:  The input message, encoded using the straddling checkerboard.
    *-------------------------------------------------------------------*/
	public static String encodeWithChecker(String message, ArrayList<String> checker) {
		String encoded = "";	// destination for encoded msg
		
		// Iterate on each char in the msg, encoding each:
		for (int i = 0; i < message.length(); i++) {
			char thisChar = message.charAt(i);	// current iter char
			int thisCharI = thisChar - 'A';		// index or thisChar, (0,,25)
			
			encoded += checker.get(thisCharI);
		}
		
		return encoded;
	}
	
	/*---------------------------------------------------------------------
    |  Method insertID(String encoded, String ID, int insPos
    |
    |  Purpose:  Takes two Strings (encoded and ID) and inserts the latter
    |      into the former, at the position insPos (or at the end if insPos
    |      is too large). The insertion result is returned.
    |
    |  Pre-condition:  encoded and ID are both Strings consisting solely of
    |      numerical characters. ID has a length of 5.
    |      insPos is an integer s.t. 0 <= insPos <= 9.
    |
    |  Post-condition: The returned value will consist of the ID inserted
    |      into the encoded String.
    |
    |  Parameters:
    |      String encoded -- An encoded string, consisting of digit characters.
    |      String ID -- A 5-digit numerical agent ID to insert.
    |      int insPos -- The index of encoded to insert at.
    |
    |  Returns:  The String resulting from the insertion, of length
    |      (encoded.length()+5).
    *-------------------------------------------------------------------*/
	public static String insertID(String encoded, String ID, int insPos) {
		// If insPos is longer than fits, append ID:
		if (encoded.length() <= insPos) {
			return encoded + ID;
		// Insert ID normally:
		} else {
			return encoded.substring(0,insPos) + ID + encoded.substring(insPos);
		}
	}
}
