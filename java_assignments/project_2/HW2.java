/**
 * Java String/StringBuilder usage 
 */

import javax.swing.*;
import java.io.*;

public class HW2 {
	public static String replaceFirstK(String str, char src, char target, int k) {

		if (str == null)
			return null;

		int i;
		int cnt = 0;
		int length = str.length();
		StringBuilder store = new StringBuilder();

		for (i = 0; i < length; ++i) {
			char ch = str.charAt(i);

			if (cnt < k && ch == src) {
				store.append(target);
				++cnt;
			} else
				store.append(ch);
		}

		return store.toString();
	}

	public static String allChars(char startChar, char endChar) {
		char ch;
		int i = 0;
		StringBuilder store = new StringBuilder();

		for (ch = startChar; ch <= endChar && i < 52; ++ch) {
			if (Character.isLetter(ch)) {
				store.append(ch);
				++i;
			}
		}

		return store.toString();
	}

	public static String showCharOfString(String first, String second) {
		if (first == null || second == null)
			return null;

		int length = first.length();
		StringBuilder store = new StringBuilder();

		int i;
		for (i = 0; i < length; ++i) {
			int j;
			boolean keep = false;

			char ch = first.charAt(i);

			for (j = 0; j < second.length() && keep == false; ++j) {
				if (second.charAt(j) == ch)
					keep = true;
			}

			if (keep)
				store.append(ch);
			else
				store.append('_');
		}

		return store.toString();
	}

	public static boolean hangman(String word, int maxBadGuess) {
		StringBuilder guessedLetters = new StringBuilder();
		int noOfBadGuess = 0; // keep count of bad guesses
		boolean status = false; // Will set to true when word is matched

		// Will continue looping till we exceed the max bad guesses
		// or we don't have our word match
		while ((maxBadGuess > noOfBadGuess) && !status) {

			// This line will substitute all non matched with "_"
			String str = showCharOfString(word, guessedLetters.toString());

			System.out.println(str + " No of bad guesses:" + noOfBadGuess);

			// Take user input from a dialog box
			String letter = JOptionPane
					.showInputDialog(null, "Enter a letter?");

			// We want a valid letter. Also since the above dialog returns
			// string
			// we want user to input just one letter in dialog
			// If not we don't think this a valid input
			if (letter != null && letter.length() == 1) {
				boolean alreadyGuessed = false;

				// Add the guessedLetters only when the letter is already not
				// present
				// So the following loop searches for our new letter in the the
				// guessedLetters
				for (int j = 0; j < guessedLetters.length() && !alreadyGuessed; j++) {
					if (guessedLetters.charAt(j) == letter.charAt(0)) {
						alreadyGuessed = true;
					}
				}
				if (!alreadyGuessed)
					guessedLetters.append(letter.charAt(0)); // Not present, so
																// add

				// Check the letter is present in our word
				boolean present = false;
				int j;

				// Match every character in our word
				for (j = 0; j < word.length() && !present; j++) {
					if (word.charAt(j) == letter.charAt(0)) {
						present = true;
					}
				}
				if (!present)
					noOfBadGuess++; // user entered wrong letter so increment
									// bad guess

				// We need now check if user correctly guessed all letters
				// so that we can say status = true or success
				boolean matched = true;
				// If all words are correctly guessed, then matchStr will
				// be same as our word
				String matchStr = showCharOfString(word,
						guessedLetters.toString());

				// this check compares letter by letter in our word and matchStr
				if (matchStr.length() == word.length()) {
					for (j = 0; j < word.length() && j < matchStr.length(); j++) {
						if (word.charAt(j) != matchStr.charAt(j)) {
							matched = false;
						}
					}
					if (matched) {
						// We are done, at last time print everything
						System.out.println(matchStr + " No of bad guesses:"
								+ noOfBadGuess);
						status = true;
					}
				}
			}
		}

		return status;
	}

	public static boolean hiddenInString(String first, String second, int k) {
		boolean status = false;
		int i, j;

		// There are two loops. The outer loop picks each character in first
		// string
		// and matches second string, inner loop will will compare
		for (i = 0; i < first.length() && !status; ++i) {
			boolean partialMatch = true; // used to see if we should continue
											// comparing or not
			int index;

			// if user enters negative k then we will start from the end of the
			// string
			// else start from the beginning
			if (k < 0)
				index = first.length() - 1 - i; // Need -1 from as this will be
												// last valid char
			else
				index = i;

			// partialMatch == false will be break the loop if any match fails
			// index will keep track of the character to match starting from the
			// current
			// character from the current string and jumping to next based on
			// value
			// of k.
			for (j = 0; j < second.length() && partialMatch; ++j) {
				// If index is taking beyond our string end then we need
				// to exit this loop; For k > 0 it is length our first string
				// for k < 0, it will be 0
				if (k > 0 && index >= first.length())
					partialMatch = false;
				else if (k < 0 && index < 0)
					partialMatch = false;
				else {
					if (first.charAt(index) == second.charAt(j)) {
						partialMatch = true;
						index += k; // increment index for next match
					} else
						partialMatch = false;
				}
			}
			if (j == second.length() && partialMatch)
				status = true; // We got it
		}

		return status;
	}

	public static String capitalizeWords(String input) {
		StringBuilder store = new StringBuilder();

		// While going through each letter we want to see if there was any
		// capital letter. If we mark this flag to true. We will set
		// to false after we have a completer word, for checking in next word
		boolean capitalLetterPresent = false;

		// wordBegin will track the index of the valid character in input
		// Since is 0 is a valid index, it is initialized to a negative value
		// So when negative value means that we haven't yet have start of the
		// word
		// positive means that we have a beginning word marker
		// Also we have wordEnd to track end of a word

		int wordBegin = -1;
		int wordEnd = -1;
		int length = input.length();
		int i, j;

		for (i = 0; i < length; ++i) {
			char ch = input.charAt(i);

			if (Character.isLetter(ch) || ch == '-') {
				wordEnd = -1;
				if (wordBegin < 0)
					wordBegin = i; // word begin index
				if (Character.isUpperCase(ch))
					capitalLetterPresent = true; // we have a capital letter
				// Special case when this is the last character in the input, we need to still add
				if (i == length -1) 
					wordEnd = i;
			} else
				wordEnd = i;
	
			if ((wordBegin >= 0) && (wordEnd > wordBegin)) {
				// We have a word, word begin is start of valid character
				// We will convert to capital if we had a capital letter
				// identified and add this word to string builder
				for (j = wordBegin; j < wordEnd; ++j) {
					char ch1 = input.charAt(j);

					if (capitalLetterPresent) {
						if (Character.isLetter(ch1))
							ch1 = Character.toUpperCase(ch1);
					}
					store.append(ch1);
				}
				// initialize word marker and capital letter variable for
				// next word
				wordBegin = -1;
				capitalLetterPresent = false;
			}
			// Add the last valid non letter/hyphen character anyway
			if (wordEnd > 0)
				store.append(ch);
		}

		return store.toString();
	}

	public static boolean hangmanSolitaire(String fileName, int maxBadGuess) {
		return true;
	}

	// Testing only. we must remove for submission
	public static void main(String[] args) {
		boolean status;
		/*
		 * System.out.println(replaceFirstK(null, 'i', 'I', 3));
		 * System.out.println(replaceFirstK("Mississippi River", 'i', 'I', 3));
		 * System.out.println(replaceFirstK("Missouri River", 'r', '*', 3));
		 * 
		 * System.out.println(allChars('d', 'm'));
		 * System.out.println(allChars('a', 'z'));
		 * System.out.println(allChars('A', 'z'));
		 * 
		 * System.out.println(showCharOfString("Missouri River", "s SR!r"));
		 * System.out.println(showCharOfString("Missouri River", ""));
		 * 
		 * //hangman("HANGMAN", 5);
		 * 
		 * status = hiddenInString("abracadabra", "cad", 1);
		 * System.out.println("hiddenInString = " + status); status =
		 * hiddenInString("abracadabra", "cad", 2);
		 * System.out.println("hiddenInString = " + status); status =
		 * hiddenInString("abracadabra", "baaa", 2);
		 * System.out.println("hiddenInString = " + status); status =
		 * hiddenInString("abracadabra", "ab", 3);
		 * System.out.println("hiddenInString = " + status); status =
		 * hiddenInString("abracadabra", "abc", 3);
		 * System.out.println("hiddenInString = " + status); status =
		 * hiddenInString("abracadabra", "bar", -3);
		 * System.out.println("hiddenInString = " + status);
		 */
		System.out
				.println(capitalizeWords("Guess what?? There are twenty-sIx letters in the English alphABEt!"));
		System.out
				.println(capitalizeWords("?? Guess what?? There are twenty-sIx letters in the English alphABEt! ??"));
		System.out.println(capitalizeWords("hello "));
		System.out.println(capitalizeWords("hello world"));

	}

}
