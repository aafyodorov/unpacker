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

  public void setInputString(String inputString) {
    this.inputString = inputString;
  }

  public Validator getValidator() {
    return validator;
  }

  public String unpack() {
    Pattern patLetters = Pattern.compile("[a-zA-Z]+");
    String inputCopy = inputString;
    StringBuilder unpacked = new StringBuilder();
    Matcher tmpMatcher;
    String tmp;

    while (!inputCopy.isEmpty()) {
      if(inputCopy.startsWith("[a-zA-Z]")) {
        tmpMatcher = patLetters.matcher(inputCopy);
        if (tmpMatcher.find()) {
          throw new RuntimeException("It's impossible");
        }
        tmp = tmpMatcher.group();
        unpacked.append(tmp);
        inputCopy = inputCopy.substring(tmp.length());
      } else {
        unpack(inputCopy);
        unpacked.append(inputCopy);
      }
    }
    return unpacked.toString();
  }

  //TODO is possible if(!matcher.find()) ???
  private void unpack(String input) {
    if (/*input.isEmpty() || */input.startsWith("//d")) {
      return /*input*/;
    }
    Matcher matcher = PAT_BRACKETS.matcher(input);
    if (!matcher.find()) {
      return;
//      throw new RuntimeException();
    }
    String tmp = matcher.group(2);
    int times = Integer.parseInt(matcher.group(1));
//    System.out.println(tmp.repeat(times));
//    System.out.println(matcher.group(0));
    System.out.print(input + "\t");

    input = input.replace(matcher.group(0), tmp.repeat(times));
    System.out.println(input);
    unpack(input);
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
