package com.czetsuya.converter;

/**
 * A class that converts amount in numbers to words.
 * 
 * @author Edward P. Legaspi <czetsuya@gmail.com>
 */
public class AmountToWordsConverter {

	// ones
	private final String[] _ones = new String[] { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
			"fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	// tens
	private final String[] _tens = new String[] { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
	
	// thousand, million, billion
	private final String[] _billions = new String[] { "", "thousand", "million", "billion" };

	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("usage AmountToWordsConverter <numeric>");
			return;
		}
		System.out.println(new AmountToWordsConverter().convert(args[1]));
	}

	public String convert(String input) {
		if (input.length() > 0 && input.indexOf(".") != -1) {
			String words[] = input.split("\\.");
			String dollars = addDollarsOrCentsWord(intToWords(words[0]), true);
			String cents = "";
			if (words.length > 1) {
				cents = addDollarsOrCentsWord(intToWords(words[1]), false);
			}

			// removed the unnecessary comma if exact value
			dollars = dollars.replace(", D", " D");
			return String.format("%s AND %s", dollars, cents);
		}

		return "Invalid input";
	}

	private String intToWords(String word) {
		StringBuilder sb = new StringBuilder();
		if (word.length() == 1) {
			return _ones[Integer.parseInt(word)];
		}

		// group the input
		word = padSpace(word);

		String groups[] = word.split(" ");
		int length = groups.length;
		for (int i = 0; i < length; i++) {
			sb.append(threeDigitToWord(length - 1 - i, groups[i]));
		}

		return sb.toString();
	}

	private String threeDigitToWord(int group, String value) {
		StringBuilder sb = new StringBuilder();
		StringBuilder threeDigitStringValue = new StringBuilder();
		int word = Integer.parseInt(value);

		int onesValue = 0;
		if (value.length() == 3) {
			int hundredValue = word / 100;
			int tensValue = (word % 100) / 10;
			onesValue = word % 100 % 10;
			if (hundredValue != 0)
				threeDigitStringValue.append(_ones[hundredValue] + " hundred ");
			if (tensValue != 0)
				threeDigitStringValue.append(_tens[tensValue] + " ");
		} else if (value.length() == 2) {
			int tensValue = (word % 100) / 10;
			onesValue = word % 100 % 10;
			if (tensValue != 0)
				threeDigitStringValue.append(_tens[tensValue] + " ");
		} else if (value.length() == 1) {
			onesValue = Integer.parseInt(value);
		}

		if (onesValue != 0)
			threeDigitStringValue.append(_ones[onesValue]);

		if (group == 0) { // ones
			if (threeDigitStringValue.length() != 0)
				sb.append(threeDigitStringValue);
		} else { // tens, thousand, million, billion
			if (threeDigitStringValue.length() != 0)
				sb.append(threeDigitStringValue + " " + _billions[group] + ", ");
		}

		return sb.toString();
	}

	private static String padSpace(String word) {
		if (word.length() <= 3)
			return word;

		char[] cWord = word.toCharArray();

		String paddedWord = "";
		int length = word.length();
		for (int i = 0; i < length; i++) {
			if (i != 0) {
				if (i % 3 == 0)
					paddedWord = ' ' + paddedWord;
			}

			paddedWord = cWord[length - 1 - i] + paddedWord;
		}
		return paddedWord;
	}

	private static String addDollarsOrCentsWord(String word, Boolean isDollar) {
		String newWord = "";
		word = word.trim();
		if (isDollar) {
			if (word.equals("one")) {
				newWord = word + " DOLLAR";
			} else {
				newWord = word + " DOLLARS";
			}
		} else {
			if (word.equals("one")) {
				newWord = word + " CENT";
			} else {
				newWord = word + " CENTS";
			}
		}
		return newWord;
	}
}
