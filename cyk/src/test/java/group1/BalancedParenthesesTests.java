package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalancedParenthesesTests {
	private static Grammar grammar;

	@BeforeAll
	private static void buildGrammar() {
		// grammar for the language of balanced parentheses
		grammar = new Grammar("S");
		
		// S -> O Z | A A | E
		Util.addRule(grammar, "S -> O Z | A A | E");
		// A -> O Z | A A
		Util.addRule(grammar, "A -> O Z | A A");
		// Z -> A C | )
		Util.addRule(grammar, "Z -> A C | )");
		// O -> (
		Util.addRule(grammar, "O -> (");
		// C -> )
		Util.addRule(grammar, "C -> )");

		grammar.displayGrammar();
	} 

	/*
	 * Testing Method: Input Space Partitioning (ISP)
	 * Coverage Criterion: Base Choice Coverage (BCC)
	 *
	 * Characteristics:
	 * 	c1: input string s is empty
	 *  	b1: true, b2: false
	 *  c2: every symbol in s exists in the grammar's alphabet
	 *  	b1: true, b2: false
	 *  c3: number of nested levels of parentheses
	 *  	b1: 0, b2: 1, b3: >1
	 *  c4: open/closed status
	 *  	b1: all pairs are closed, b2: at least one pair is unclosed, b3: at least one pair is unopened
	 *
	 *  Total Test Requirements (TR): 7
	 */

	/*
	 * Base Choice
	 * TR1: [b2, b1, b1, b1]
	 * Expected output: true
	 */
	@Test
	public void testOnePair() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("()", grammar));	
	}

	/*
	 * TR2: [b1, b1, b1, b1]
	 * Expected output: true
	 */
	@Test
	public void testEmpty() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("", grammar));	
	}

	/*
	 * TR3: [b2, b2, b1, b1]
	 * Expected output: false
	 */
	@Test
	public void testSymbolNotInAlphabet() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm("(a)", grammar));	
	}

	/*
	 * TR4: [b2, b1, b2, b1]
	 * Expected output: true
	 */
	@Test
	public void testOneLevelNesting() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("(())", grammar));	
	}

	/*
	 * TR5: [b2, b1, b3, b1]
	 * Expected output: true
	 */
	@Test
	public void testMoreThanOneLevelOfNesting() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("((()))", grammar));	
	}

	/*
	 * TR6: [b2, b1, b1, b2]
	 * Expected output: false
	 */
	@Test
	public void testUnclosedPair() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm("(", grammar));	
	}

	/*
	 * TR7: [b2, b1, b1, b3]
	 * Expected output: false
	 */
	@Test
	public void testUnopenedPair() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm(")", grammar));	
	}
}
