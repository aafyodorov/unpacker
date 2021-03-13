import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
	  Assertions.assertEquals(stringStringEntry.getValue(), unpacker.unpackRecursion());
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

}
