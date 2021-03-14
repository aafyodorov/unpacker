import org.junit.jupiter.api.*;

import java.util.*;

class UnpackerTest {

  private static final String[][] validCases;

  static  {
	validCases = new String[][]{
		{"Test case 1 from task", "3[xyz]4[xy]z", "xyzxyzxyzxyxyxyxyz"},
		{"Test case 2 from task", "2[3[x]y]", "xxxyxxxy"},
		{"Empty string", "", ""},
		{"One character string", "x", "x"},
		{"Three characters string", "xyz", "xyz"},
		{"Single repeat of one character", "1[x]", "x"},
		{"Double repetition of one character", "2[x]", "xx"},
		{"Three-digit number of repetition of one character", "123[x]", "x".repeat(123)},
		{"Brackets at the end of the string", "xyz12[ab]", "xyz" + "ab".repeat(12)},
		{"Brackets at the beginning of the string", "12[ab]xyz", "ab".repeat(12) + "xyz"},
		{"Brackets in the middle of the string", "xyz12[ab]xyz", "xyz" + "ab".repeat(12) + "xyz"},
		{"Double nested brackets", "40[x25[ab]y]", ("x" + "ab".repeat(25) + "y").repeat(40)},
		{"Triple nested brackets", "xyz12[a124[cv]b]xyz", "xyz" + ("a" + "cv".repeat(124) + "b").repeat(12) + "xyz"}
	};
  }

  @TestFactory
  Iterable<DynamicTest> dynamicTestIterable_unpackRecursion_validCases() {
    List<DynamicTest> testList = new ArrayList<>();

	for (String[] curCase : validCases) {
	  testList.add(DynamicTest.dynamicTest(curCase[0],
		  () -> Assertions.assertEquals(curCase[2], new Unpacker(curCase[1]).unpackRecursion())));
	}
	return testList;
  }

  @TestFactory
  Iterable<DynamicTest> dynamicTestIterable_unpackIterative_validCases() {
	List<DynamicTest> testList = new ArrayList<>();

	for (String[] curCase : validCases) {
	  testList.add(DynamicTest.dynamicTest(curCase[0],
		  () -> Assertions.assertEquals(curCase[2], new Unpacker(curCase[1]).unpackIterative())));
	}
	return testList;
  }

}
