import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UnpackerTest {

  static Unpacker unpacker;

  @BeforeAll
  static void init() {
	unpacker = new Unpacker();
  }

  @Test
  public void validate_valid() {
	unpacker.setInputString("3[xy3[ab]z]4[xy]z");
	unpacker.getValidator().validate();
  }

  @Test
  public void checkValidChars_containCyrillicX() {
	unpacker.setInputString("3[хyz]4[xy]z");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkValidChars);
  }

  @Test
  public void checkValidChars_containPoint() {
	unpacker.setInputString("3[xyz]4[x.y]z");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkValidChars);
  }

  @Test
  public void checkBrackets_startWithCloseBracket() {
	unpacker.setInputString("3][хyz]4[xy]z");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkBrackets);
  }

  @Test
  public void checkBrackets_endWithOpenBracket() {
	unpacker.setInputString("3[хyz]4[xy][z");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkBrackets);
  }

  @Test
  public void checkBrackets_unbalanced_1() {
	unpacker.setInputString("3[хy[[z]4[xy]z");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkBrackets);
  }

  @Test
  public void checkBrackets_unbalanced_2() {
	unpacker.setInputString("3[хyz]4[xy]zabs4[assd2[aas]");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkBrackets);
  }

  @Test
  public void checkNumbersBeforeBrackets_bracketsInARow() {
	unpacker.setInputString("3[хyz][xy]zabs4[assd2[aas]");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkNumbersBeforeBrackets);
  }

  @Test
  public void checkNumbersBeforeBrackets_nestedBracketsWithoutNumber() {
	unpacker.setInputString("3[хyz]3[xy]zabs4[as[a]sd2[aas]");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkNumbersBeforeBrackets);
  }

  @Test
  public void checkNumbersBeforeBrackets_zeroTimesToRepeat() {
	unpacker.setInputString("3[хyz]3[xy]zabs4[as0[a]sd2[aas]");
	Assertions.assertThrows(IllegalArgumentException.class, unpacker.getValidator()::checkNumbersBeforeBrackets);
  }
}
