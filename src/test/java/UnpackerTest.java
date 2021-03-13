import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UnpackerTest {

  static Unpacker unpacker;

  static Map<String, String> validCases = new HashMap<>();

  @BeforeAll
  static void init() {
	unpacker = new Unpacker();

	validCases.put("3[xyz]4[xy]z", "xyzxyzxyzxyxyxyxyz");
	validCases.put("2[3[x]y]", "xxxyxxxy");
	validCases.put("", "");
	validCases.put("x", "x");
	validCases.put("xyz", "xyz");
	validCases.put("1[x]", "x");
	validCases.put("2[x]", "xx");
	validCases.put("123[x]", "x".repeat(123));
	validCases.put("40[x25[ab]y]", ("x" + "ab".repeat(25) + "y").repeat(40));
	validCases.put("xyz12[ab]", "xyz" + "ab".repeat(12));
	validCases.put("12[ab]xyz", "ab".repeat(12) + "xyz");
	validCases.put("xyz12[ab]xyz", "xyz" + "ab".repeat(12) + "xyz");
	//	validCases.put("", "");
  }

  @Test
  public void unpackRecursionRegEx_validCases() {
	for (Map.Entry<String, String> stringStringEntry : validCases.entrySet()) {
	  unpacker.setInputString(stringStringEntry.getKey());
	  Assertions.assertEquals(stringStringEntry.getValue(), unpacker.unpackRecursionRegEx());
	}
  }

  @Test
  public void unpackIterative_validCases() {
	for (Map.Entry<String, String> stringStringEntry : validCases.entrySet()) {
	  unpacker.setInputString(stringStringEntry.getKey());
	  unpacker.unpackIterative();
	  Assertions.assertEquals(stringStringEntry.getValue(), unpacker.unpackIterative());
	}
  }

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
	  Assertions.assertThrows(IllegalArgumentException.class, () -> unpacker.setInputString(invalidCase));
	}
  }
}
