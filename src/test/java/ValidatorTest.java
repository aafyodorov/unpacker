import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ValidatorTest {

  @Test
  public void unpack_invalidCases() {
	List<String> invalidCases = new ArrayList<>();
	invalidCases.add("2");
	invalidCases.add("[xy]");
	invalidCases.add("0[xy]");
	invalidCases.add("3[хyz]4[xy]z");			//Cyrillic 'x'
	invalidCases.add("3[xy3[ab]z]4[x_y]z");
	invalidCases.add("3[xy3[ab]z]4[xüy]z");
	invalidCases.add("3][хyz]4[xy]z");
	invalidCases.add("3][хyz]4[xy]z");
	invalidCases.add("3[хyz]4[xy][z");
	invalidCases.add("3[хy[[z]4[xy]z");
	invalidCases.add("3[хyz]4[xy]zabs4[assd2[aas]");
	invalidCases.add("3[хyz][xy]zabs4[assd2[aas]");
	invalidCases.add("3[хyz]3[xy]zabs4[as[a]sd2[aas]");
	invalidCases.add("3[хyz]3[xy]zabs4[as0[a]sd2[aas]");
//	invalidCases.add("");

	for (String invalidCase : invalidCases) {
	  Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validate(invalidCase));
	}
  }
}