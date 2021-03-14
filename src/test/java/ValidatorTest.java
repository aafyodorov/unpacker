import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;

class ValidatorTest {

  private static final String[][] invalidCases;

  static {
	invalidCases = new String[][]{
		{"Number without brackets", "2"},
		{"Brackets without number", "[xy]"},
		{"Repeat zero times", "0[xy]"},
		{"Incorrect symbol: х (cyrillic)", "3[хyz]4[xy]z"},
		{"Incorrect symbol: _", "3[xy3[ab]z]4[x_y]z"},
		{"Incorrect symbol: ü", "3[xy3[ab]z]4[xüy]z"},
		{"Closing bracket without leading opening bracket", "3][хyz]4[xy]z"},
		{"Opening bracket without closing bracket", "3[хyz]4[xy][z"},
		{"Unbalanced brackets (two extra opening brackets)", "3[хy[[z]4[xy]z"},
		{"Unbalanced brackets (one less closing bracket)", "3[хyz]4[xy]zabs4[assd2[aas]"},
		{"Brackets in a row without a number", "3[хyz][xy]zabs4[assd2[aas]"},
		{"Nested brackets without a number", "3[хyz]3[xy]zabs4[as[a]sd2[aas]"},
		{"Nested repeat zero times", "3[хyz]3[xy]zabs4[as0[a]sd2[aas]"},
	};
  }

  @TestFactory
  Iterable<DynamicTest> dynamicTestIterable_unpackRecursion_validCases() {
	List<DynamicTest> testList = new ArrayList<>();

	for (String[] curCase : invalidCases) {
	  testList.add(DynamicTest.dynamicTest(curCase[0],
		  () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.validate(curCase[1]))));
	}
	return testList;
  }

}