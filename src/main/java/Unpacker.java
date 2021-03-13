import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrey Fyodorov
 * Created on 12.03.2021.
 */

public class Unpacker {

  private static final Pattern PAT_BRACKETS = Pattern.compile("(\\d+)\\[([^\\[]*?)]");
  private String inputString;
  private Validator validator;

  public Unpacker() {
    validator = new Validator();
  }

  public Unpacker(String inputString) {
    this();
    this.inputString = inputString;
  }

  public String getInputString() {
    return inputString;
  }

  public void setInputString(String target) {
    this.inputString = target;
  }

  public Validator getValidator() {
    return validator;
  }

  public String unpack() {
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

  //TODO delete or change first if
  private void unpack(StringBuilder src) {
    if (src.toString().startsWith("//d")) {
      return ;
    }
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
        Pattern pattern = Pattern.compile("(\\d*)(\\[)");
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
          if (matcher.group(1).length() == 0) {
            throw new IllegalArgumentException("Number must precede square brackets");
          }
          if (Integer.parseInt(matcher.group(1)) == 0) {
            throw new IllegalArgumentException("The number of repetitions must be greater than zero");
          }
        }
      }
    }

}
