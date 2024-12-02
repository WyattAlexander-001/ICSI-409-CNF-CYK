package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class IJKTests {
	
	private static Grammar grammar;

	@BeforeAll
	private static void buildGrammar() {
		// grammar for the language {a^i b^j c^k | i = j or j = k}
		grammar = new Grammar("S");

		// S -> A Xc | Xa Z1 | c | Xa B | b Z2 | a | E
		Util.addRule(grammar, "S -> A Xc | Xa Z1 | c | Xa B | Xb Z2 | a | E");
		// A -> A Xc | Xa Z1 | c 
		Util.addRule(grammar, "A -> A Xc | Xa Z1 | c"); 
		// C -> Xa Z1
		Util.addRule(grammar, "C -> Xa Z1"); 
		// B -> Xa B | Xb Z2 | a 
		Util.addRule(grammar, "B -> Xa B | Xb Z2 | a"); 
		// D -> Xb Z2 
		Util.addRule(grammar, "D -> Xb Z2"); 
		// Z1 -> C Xb | b 
		Util.addRule(grammar, "Z1 -> C Xb | b"); 
		// Z2 -> D Xc | c 
		Util.addRule(grammar, "Z2 -> D Xc | c"); 
		// Xa -> a 
		Util.addRule(grammar, "Xa -> a"); 
		// Xb -> b 
		Util.addRule(grammar, "Xb -> b"); 
		// Xc -> c
		Util.addRule(grammar, "Xc -> c"); 

		grammar.displayGrammar();
	}

	/*
	 * Testing Method: Input Space Partitioning (ISP)
	 * Coverage Criterion: Multiple Base Choice Coverage (MBCC)
	 *
	 * Characteristics:
	 *  c1: input string s is empty
	 *		b1: true 	b2: false
	 *  c2: every symbol in s exists in the grammar's alphabet
	 *		b1: true 	b2: false
	 * 	c3: length of i 
	 *  	b1: 0, b2: 1, b3: >1
	 * 	c4: length of j 
	 *  	b1: 0, b2: 1, b3: >1
	 * 	c5: length of k
	 *  	b1: 0, b2: 1, b3: >1
	 *
	 *  Test Requirements (TR): 17
	 */

	/*
	 * Base Choice 1
	 * TR1: [b2, b1, b3, b3, b3]
	 * expected output: true
	 */
	@Test
	public void baseCase1() {
		assertTrue(CYKAlgorithm.cykAlgorithm("aaaabbbccc", grammar));
	}

	/*
	 * INFEASIBLE: length of i,j,k cannot be >1 if the string is empty 
	 * empty string will be tested instead
	 * TR2: [c1 = true]
	 * expected output: true
	 */
	@Test 
	public void emptyTest() {
		assertTrue(CYKAlgorithm.cykAlgorithm("", grammar));
	}

	/*
	 * TR3: [b2, b1, b3, b3, b3]
	 * expected output: false
	 */
	@Test
	public void outsideAlphabetBase1Test() {
		assertFalse(CYKAlgorithm.cykAlgorithm("aabbccdd", grammar));
	}

	/*
	 * TR4: [b2, b1, b1, b3, b3]
	 * expected output: true
	 */
	@Test 
	public void base1c3_1Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("bbcc", grammar));
	}

	/*
	 * TR5: [b2, b1, b2, b3, b3]
	 * expected output: true
	 */
	@Test 
	public void base1c3_2Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("abbbccc", grammar));
	}

	/*
	 * TR6: [b2, b1, b3, b1, b3]
	 * expected output: false
	 */
	@Test 
	public void base1c4_1Test() {
		assertFalse(CYKAlgorithm.cykAlgorithm("aaccc", grammar));
	}

	/*
	 * TR7: [b2, b1, b3, b2, b3]
	 * expected output: false
	 */
	@Test 
	public void base1c4_2Test() {
		assertFalse(CYKAlgorithm.cykAlgorithm("aabccc", grammar));
	}

	/*
	 * TR8: [b2, b1, b3, b3, b1]
	 * expected output: false
	 */
	@Test 
	public void base1c5_1Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("aabb", grammar));
	}

	/*
	 * TR9: [b2, b1, b3, b3, b2]
	 * expected output: false
	 */
	@Test 
	public void base1c5_2Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("aabbc", grammar));
	}

	/*
	 * Base Choice 2
	 * TR10: [b2, b1, b2, b2, b2]
	 * expected output: true
	 */	
	@Test 
	public void base2Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("abc", grammar));
	}

	/*
	 * the empty case was already tested with the first base choice
	 * so it is ommitted here.
	 */

	/*
	 * TR11: [b2, b2, b2, b2, b2]
	 * expected output: false
	 */	
	@Test 
	public void outsideAlphabetBase2Test() {
		assertFalse(CYKAlgorithm.cykAlgorithm("abcdd", grammar));
	}

	/*
	 * TR12: [b2, b1, b1, b2, b2]
	 * expected output: true
	 */	
	@Test 
	public void base2c3_1Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("bc", grammar));
	}	

	/*
	 * TR13: [b2, b1, b3, b2, b2]
	 * expected output: true
	 */	
	@Test 
	public void base2c3_2Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("aaabc", grammar));
	}	

	/*
	 * TR14: [b2, b1, b2, b1, b2]
	 * expected output: false
	 */	
	@Test 
	public void base2c4_1Test() {
		assertFalse(CYKAlgorithm.cykAlgorithm("ac", grammar));
	}	

	/*
	 * TR15: [b2, b1, b2, b3, b2]
	 * expected output: false
	 */	
	@Test 
	public void base2c4_2Test() {
		assertFalse(CYKAlgorithm.cykAlgorithm("abbbbc", grammar));
	}

	/*
	 * TR16: [b2, b1, b2, b2, b1]
	 * expected output: true
	 */	
	@Test 
	public void base2c5_1Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("ab", grammar));
	}

	/*
	 * TR17: [b2, b1, b2, b2, b3]
	 * expected output: true
	 */	
	@Test 
	public void base2c5_2Test() {
		assertTrue(CYKAlgorithm.cykAlgorithm("abccccc", grammar));
	}
}
