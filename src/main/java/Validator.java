import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrey Fyodorov
 * Created on 14.03.2021
 */

public final class Validator {

  private Validator() {
  }

  public static void validate(String target) {
	checkValidChars(target);
	checkBrackets(target);
	checkNumbersBeforeBrackets(target);
  }

  private static void checkValidChars(String target) {
	if (target.matches(".*[^\\da-zA-Z\\[\\]].*")) {
	  throw new IllegalArgumentException("Input string must contain only Latin letters, positive numbers and box " +
		  "brackets.");
	}
  }

  private static void checkBrackets(String target) {
	int balance = 0;

	for (int i = 0; i < target.length(); i++) {
	  if (target.charAt(i) == '[') {
		++balance;
	  } else if (target.charAt(i) == ']') {
		--balance;
		if (balance < 0 ) {
		  throw new IllegalArgumentException("Invalid bracket in input string at: " + (i + 1));
		}
	  }
	}

	if (balance > 0) {
	  throw new IllegalArgumentException("Unbalanced brackets");
	}
  }

  private static void checkNumbersBeforeBrackets(String target) {
	Pattern patNumBeforeBracket = Pattern.compile("(\\d*)(\\[)");
	Matcher matNumBeforeBracket = patNumBeforeBracket.matcher(target);
	Pattern patBracketAfterNum = Pattern.compile("(\\d+)(.?)");
	Matcher matBracketAfterNum = patBracketAfterNum.matcher(target);
	boolean matFind1, matFind2;

	while ((matFind1 = matNumBeforeBracket.find()) |
		(matFind2 = matBracketAfterNum.find())) {
	  if (matFind1 ^ matFind2 ||
		  !matBracketAfterNum.group(2).equals("[") ||
		  matNumBeforeBracket.group(1).length() == 0) {
		throw new IllegalArgumentException("Number must precede square brackets");
	  }
	  else if (Integer.parseInt(matNumBeforeBracket.group(1)) == 0) {
		throw new IllegalArgumentException("The number of repetitions must be greater than zero");
	  }
	}
  }

}
