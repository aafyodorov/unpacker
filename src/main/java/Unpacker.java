import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrey Fyodorov
 * Created on 12.03.2021.
 */

public class Unpacker {

  private static final Pattern PAT_BRACKETS = Pattern.compile("(\\d+)\\[([^\\[]*?)]");
  private String inputString;

  public Unpacker() {
  }

  public Unpacker(String inputString) {
    setInputString(inputString);
  }

  public void setInputString(String target) {
    Validator.validate(target);
    this.inputString = target;
  }

  public String getInputString() {
    return inputString;
  }

  public String unpackRecursion() {
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
        unpackRecursion(inputSB);
      }
    }
    return unpacked.toString();
  }

  private void unpackRecursion(StringBuilder src) {
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

  public String unpackIterative() {
    StringBuilder unpacked = new StringBuilder(inputString);
    int closeBracket;
    int openBracket;
    int startNbr;

    while ((closeBracket = unpacked.indexOf("]")) != -1) {
      int tmp;
      openBracket = -1;
      while ((tmp = unpacked.indexOf("[", openBracket + 1)) < closeBracket && tmp != -1) {
        openBracket = tmp;
      }
      startNbr = openBracket - 1;
      while (startNbr >= 0 && Character.isDigit(unpacked.charAt(startNbr))) {
        --startNbr;
      }
      ++startNbr;
      int times = Integer.parseInt(unpacked.substring(startNbr, openBracket));
      String pattern = unpacked.substring(openBracket + 1, closeBracket);
      unpacked.replace(startNbr, closeBracket + 1, pattern.repeat(times));
    }
    return unpacked.toString();
  }

}
