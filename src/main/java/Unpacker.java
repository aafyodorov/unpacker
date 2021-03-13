import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrey Fyodorov
 * Created on 12.03.2021.
 */

public class Unpacker {

  private static final Pattern PAT_BRACKETS = Pattern.compile("(\\d+)\\[([^\\[]*?)]");
  private String inputString;
  private final Validator validator;

  public Unpacker() {
    validator = new Validator();
  }


  public void setInputString(String target) {
    this.inputString = target;
  }

  public String unpack() {
    validator.validate();
    Pattern patLetters = Pattern.compile("^[a-zA-Z]+");
    StringBuilder inputSB = new StringBuilder(inputString);
    StringBuilder unpacked = new StringBuilder();
    Matcher tmpMatcher;
    String tmp;

    while (!inputSB.isEmpty()) {
      tmpMatcher = patLetters.matcher(inputSB);
      if (tmpMatcher.find()) {
        tmp = tmpMatcher.group();
        unpacked.append(tmp);
        int start = inputSB.indexOf(tmp);
        inputSB.delete(start, start + tmp.length());
      } else {
        unpack(inputSB);
      }
    }
    return unpacked.toString();
  }

  private void unpack(StringBuilder src) {

    Matcher matcher = PAT_BRACKETS.matcher(src);
    if (!matcher.find()) {
      return;
    }
    String tmp = matcher.group(2);
    int times = Integer.parseInt(matcher.group(1));
    int start = src.indexOf(matcher.group(0));
    int end = start + matcher.group(0).length();
    src.replace(start, end, tmp.repeat(times));
  }

  class Validator {

    public void validate() {
      checkValidChars();
      checkBrackets();
      checkNumbersBeforeBrackets();
    }

    protected void checkValidChars() {
        if (inputString.matches(".*[^\\da-zA-Z\\[\\]].*")) {
          throw new IllegalArgumentException("Input string must contain only Latin letters, positive numbers and box " +
              "brackets.");
        }
      }

      protected void checkBrackets() {
        int balance = 0;

        for (int i = 0; i < inputString.length(); i++) {
          if (inputString.charAt(i) == '[') {
            ++balance;
          } else if (inputString.charAt(i) == ']') {
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

      protected void checkNumbersBeforeBrackets() {
        Pattern patNumBeforeBracket = Pattern.compile("(\\d*)(\\[)");
        Matcher matNumBeforeBracket = patNumBeforeBracket.matcher(inputString);
        Pattern patBracketAfterNum = Pattern.compile("(\\d+)(.?)");
        Matcher matBracketAfterNum = patBracketAfterNum.matcher(inputString);
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

}
