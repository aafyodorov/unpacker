import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrey Fyodorov
 * Created on 12.03.2021.
 */

public class Unpacker {

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

  public void setInputString(String inputString) {
    this.inputString = inputString;
  }

  public Validator getValidator() {
    return validator;
  }

  class Validator {

    public void validate() {
      checkValidChars();
      checkBrackets();
      checkNumbersBeforeBrackets();
    }

    protected void checkValidChars() {
        if (inputString.matches(".*[^\\w\\[\\]].*")) {
          throw new IllegalArgumentException("Input string must contain only Latin letters, numbers and box brackets.");
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
        }
      }
    }

}
