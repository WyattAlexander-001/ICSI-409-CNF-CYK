package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Sipser26dTests {

	private static Grammar grammar;

	@BeforeAll 
	private static void buildGrammar() {
		// grammar for the language {x1#x2# . . . #xk| k ≥ 1, each xi ∈ {a, b}*, and for some i and j, xi = xjR}

		grammar = new Grammar("S");
		// S -> L Z1 | C R | Xa Z2 | Xb Z3 | X# L | # 
		Util.addRule(grammar, "S -> L Z1 | C R | Xa Z2 | Xb Z3 | X# L | #");
		// M -> M M | a | b | # 
		Util.addRule(grammar, "M -> M M | a | b | #");
		// L -> M X#
		Util.addRule(grammar, "L -> M X#");
		// C -> Xa Z2 | Xb Z3 | X# L | #
		Util.addRule(grammar, "C -> Xa Z2 | Xb Z3 | X# L | #");
		// R -> X# M 
		Util.addRule(grammar, "R -> X# M");
		// Z1 -> C R | Xa Z2 | Xb Z3 | X# L | #
		Util.addRule(grammar, "Z1 -> C R | Xa Z2 | Xb Z3 | X# L | #");
		// Z2 -> C Xa
		Util.addRule(grammar, "Z2 -> C Xa");
		// Z3 -> C Xb
		Util.addRule(grammar, "Z3 -> C Xb");
		// Xa -> a
		Util.addRule(grammar, "Xa -> a");
		// Xb -> b
		Util.addRule(grammar, "Xb -> b");
		// X# -> #
		Util.addRule(grammar, "X# -> #");

		grammar.displayGrammar();
	}

	/*
	 * Testing Method: Input Space Partitioning (ISP)
	 * Coverage Criterion: Multiple Base Choice Coverage (MBCC)
	 *
	 * Characteristics:
	 * 	c1: input string s is empty
	 *  	b1: true, b2: false
	 *  c2: every symbol in s exists in the grammar's alphabet
	 *  	b1: true, b2: false
	 *  c3: number of partitions (number of #'s) 
	 *  	b1: 0, b2: 1, b3: >1
	 *  c4: length of each shortest xi
	 *  	b1: 0, b1: 1, b3: >1
	 *  c5: at least one xi = xjR
	 *  	b1: true, b2: false
	 *
	 *  Total Test Requirements (TR): 13
	 */

	/*
	 * Base Choice 1
	 * TR1: [b2, b1, b3, b3, b1]
	 * Expected output: true
	 */
	@Test
	public void test2PartitionsAccepted() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("ab#ba#aa", grammar));	
	}

	/*
	 * TR2: [b1, b1, b3, b3, b1]
	 * Infeasible as s cannot be empty and have more than one #,
	 * alter to test the empty string as that must be tested
	 * TR2: [b1, b1, b1, b1, b2]
	 * Expected output: false
	 */
	@Test
	public void testEmpty() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm("", grammar));	
	}

	/*
	 * TR3: [b2, b2, b3, b3, b1]
	 * Expected output: false
	 */
	@Test 
	public void testSymbolNotInAlphabet() {
		assertFalse(CYKAlgorithm.cykAlgorithm("ab#ba#cc", grammar));
	}

	/*
	 * TR4: [b2, b1, b1, b3, b1]
	 * Expected output: false
	 */
	@Test 
	public void testNoPartitions() {
		assertFalse(CYKAlgorithm.cykAlgorithm("aa", grammar));
	}

	/*
	 * TR5: [b2, b1, b2, b3, b1]
	 * Expected output: true
	 */
	@Test 
	public void testOnePartition() {
		assertTrue(CYKAlgorithm.cykAlgorithm("ab#ba", grammar));
	}

	/*
	 * TR6: [b2, b1, b3, b1, b1]
	 * Expected output: true
	 * NOTE: xi may be empty, and the reverse of the empty string is the empty string, 
	 * so this is a valid string in the grammar
	 */
	@Test 
	public void testEmptySegment() {
		assertTrue(CYKAlgorithm.cykAlgorithm("ab##", grammar));
	}

	/*
	 * TR7: [b2, b1, b3, b2, b1]
	 * Expected output: true
	 */
	@Test 
	public void testSegmentLengthOne() {
		assertTrue(CYKAlgorithm.cykAlgorithm("a#a#abab", grammar));
	}

	/*
	 * [b2, b1, b3, b3, b2] is a duplicate of the second base choice TR8
	 * Expected output: false
	 */

	/*
	 * Base Choice 2
	 * TR8: [b2, b1, b3, b3, b2]
	 * Expected output: false
	 */
	@Test 
	public void testNoMatchingReverse() {
		assertFalse(CYKAlgorithm.cykAlgorithm("aba#bab#aaa#bbb", grammar));
	}

	/*
	 * [b1, b1, b3, b3, b2]
	 * Infeasible as s cannot be empty and have more than one #,
	 * alter to test the empty string as that must be tested
	 * [b1, b1, b1, b1, b2] is a duplicate of TR2
	 * Expected output: false
	 */

	/*
	 * TR9: [b2, b2, b3, b3, b2]
	 * Expected output: false
	 */
	@Test
	public void testSymbolNotInAlphabetNoMatchingReverse() {
		assertFalse(CYKAlgorithm.cykAlgorithm("ab#abc#ab", grammar));
	}

	/*
	 * TR10: [b2, b1, b1, b3, b2]
	 * Expected output: false
	 */
	@Test
	public void testNoPartitionNoMatchingReverse() {
		assertFalse(CYKAlgorithm.cykAlgorithm("ab", grammar));
	}

	/*
	 * TR11: [b2, b1, b2, b3, b2]
	 * Expected output: false
	 */
	@Test 
	public void testOnePartitionNoMatchingReverse() {
		assertFalse(CYKAlgorithm.cykAlgorithm("ab#ab", grammar));
	}

	/*
	 * TR12: [b2, b1, b3, b1, b2]
	 * Expected output: false
	 */
	@Test 
	public void testEmptySegmentNoMatchingReverse() {
		assertFalse(CYKAlgorithm.cykAlgorithm("ab##ab", grammar));
	}

	/*
	 * TR13: [b2, b1, b3, b2, b2]
	 * Expected output: false
	 */
	@Test 
	public void testSegmentLengthOneNoMatchingReverse() {
		assertFalse(CYKAlgorithm.cykAlgorithm("a#aaa#bb", grammar));
	}
}
