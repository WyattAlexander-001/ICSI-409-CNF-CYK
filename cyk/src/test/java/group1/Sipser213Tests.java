package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class Sipser213Tests {
	
	private static Grammar grammar;

	@BeforeAll 
	private static void buildGrammar() {
		// grammar for the language {0^(n1)#0^(n2)#0^(n3) OR 0^n#0^(2n) | ni >= 0}
		grammar = new Grammar("S");

		// S -> T T | Z1 Z2 | # 
		Util.addRule(grammar, "S -> T T | Z1 Z2 | #");
		// T -> X0 T | T X0 | # 
		Util.addRule(grammar, "T -> X0 T | T X0 | #");
		// U -> Z1 Z2 | # 
		Util.addRule(grammar, "U -> Z1 Z2 | #");
		// Z1 -> X0 U 
		Util.addRule(grammar, "Z1 -> X0 U");
		// Z2 -> X0 X0 
		Util.addRule(grammar, "Z2 -> X0 X0");
		// X0 -> 0
		Util.addRule(grammar, "X0 -> 0");

		grammar.displayGrammar();
	}

	/*
	 * Testing Method: Input Space Partitioning (ISP)
	 * Coverage Criterion: Base Choice Coverage (BCC)
	 *
	 * Characteristics for testing 0^(n1)#0^(n2)#0^(n3):
	 *  c1: input string s is empty
	 *		b1: true 	b2: false
	 *  c2: every symbol in s exists in the grammar's alphabet
	 *		b1: true 	b2: false
	 *	c3: number of #s
	 *		b1: 0, b2: 1, b3: 2, b4: >2
	 * 	c4: length of n1 
	 *  	b1: 0, b2: 1, b3: >1
	 * 	c5: length of n2 
	 *  	b1: 0, b2: 1, b3: >1
	 * 	c6: length of n3
	 *  	b1: 0, b2: 1, b3: >1
	 *
	 * Characteristics for testing 0^n#0^(2n):
	 *  c1: input string s is empty
	 *		b1: true 	b2: false
	 *  c2: every symbol in s exists in the grammar's alphabet
	 *		b1: true 	b2: false
	 *	c3: number of #s
	 *		b1: 0, b2: 1, b3: >1
	 * 	c4: length of second set of 0's = 2 times length of first set of 0's 
	 *  	b1: true, b2: false
	 *
	 *  Total Test Requirements (TR): 17 
	 */

	// Tests for 0^(n1)#0^(n2)#0^(n3)

	/*
	 * Base Choice
	 * TR1: [b2, b1, b3, b3, b3, b3]
	 * Expected output: true
	 */
	@Test 
	public void testCommonAcceptedString() {
		assertTrue(CYKAlgorithm.cykAlgorithm("0000#00#00000", grammar));
	}

	/*
	 * TR2: [b1, b1, b3, b3, b3, b3]
	 * Infeasible as s cannot be empty and have 0's with length >1,
	 * alter to test the empty string as that must be tested
	 * TR2: [b1, b1, b1, b1, b1, b1]
	 * Expected output: false
	 */
	@Test 
	public void testEmpty() {
		assertFalse(CYKAlgorithm.cykAlgorithm("", grammar));
	}

	/*
	 * TR3: [b2, b2, b3, b3, b3, b3]
	 * Expected output: false
	 */
	@Test 
	public void testSymbolNotInAlphabet() {
		assertFalse(CYKAlgorithm.cykAlgorithm("0a000#00#00000", grammar));
	}

	/*
	 * TR4: [b2, b1, b1, b3, b3, b3]
	 * Expected output: false
	 */
	@Test 
	public void testNoHashes() {
		assertFalse(CYKAlgorithm.cykAlgorithm("00000000000", grammar));
	}

	/*
	 * TR5: [b2, b1, b2, b3, b3, b3]
	 * Expected output: false
	 */
	@Test 
	public void testOneHash() {
		assertFalse(CYKAlgorithm.cykAlgorithm("0000#0000000", grammar));
	}

	/*
	 * TR7: [b2, b1, b4, b3, b3, b3]
	 * Expected output: false
	 */
	@Test 
	public void testMoreThanTwoHashes() {
		assertFalse(CYKAlgorithm.cykAlgorithm("0000#00#00000#", grammar));
	}

	/*
	 * TR8: [b2, b1, b3, b1, b3, b3]
	 * Expected output: true
	 */
	@Test 
	public void testN1Empty() {
		assertTrue(CYKAlgorithm.cykAlgorithm("#00#00000", grammar));
	}

	/*
	 * TR9: [b2, b1, b3, b2, b3, b3]
	 * Expected output: true
	 */
	@Test 
	public void testN1One() {
		assertTrue(CYKAlgorithm.cykAlgorithm("0#00#00000", grammar));
	}

	/*
	 * TR10: [b2, b1, b3, b3, b1, b3]
	 * Expected output: true
	 */
	@Test 
	public void testN2Empty() {
		assertTrue(CYKAlgorithm.cykAlgorithm("0000##00000", grammar));
	}

	/*
	 * TR11: [b2, b1, b3, b3, b2, b3]
	 * Expected output: true
	 */
	@Test 
	public void testN2One() {
		assertTrue(CYKAlgorithm.cykAlgorithm("0000#0#00000", grammar));
	}

	/*
	 * TR12: [b2, b1, b3, b3, b3, b1]
	 * Expected output: true
	 */
	@Test 
	public void testN3Empty() {
		assertTrue(CYKAlgorithm.cykAlgorithm("0000#00#", grammar));
	}

	/*
	 * TR13: [b2, b1, b3, b3, b3, b2]
	 * Expected output: true
	 */
	@Test 
	public void testN3One() {
		assertTrue(CYKAlgorithm.cykAlgorithm("0000#00#0", grammar));
	}

	// Tests for 0^n#0^(2n)
	
	/*
	 * Base Choice
	 * TR14: [b2, b1, b2, b1]
	 * Expected output: true
	 */
	@Test 
	public void testValidString() {
		assertTrue(CYKAlgorithm.cykAlgorithm("00#0000", grammar));
	}
	
	/*
	 * [b2, b1, b2, b1]
	 * Infeasible as s cannot be empty and have a #,
	 * alter to test the empty string as that must be tested
	 * This becomes a duplicate of TR2
	 */
	
	/*
	 * TR15: [b2, b2, b2, b1]
	 * Expected output: false
	 */
	@Test 
	public void testSymbolNotInAlphabet2() {
		assertFalse(CYKAlgorithm.cykAlgorithm("00#0000a", grammar));
	}
	
	/*
	 * TR16: [b2, b1, b1, b1]
	 * Expected output: false
	 */
	@Test 
	public void testNoHashes2() {
		assertFalse(CYKAlgorithm.cykAlgorithm("000000", grammar));
	}
	
	/*
	 * [b2, b1, b3, b1]
	 * This becomes a duplicate of TR12
	 */
	
	/*
	 * TR17: [b2, b1, b1, b2]
	 * Expected output: false
	 */
	@Test 
	public void test0sNotDoubled() {
		assertFalse(CYKAlgorithm.cykAlgorithm("00#000", grammar));
	}
}
