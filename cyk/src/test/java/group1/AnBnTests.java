package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AnBnTests {
	private static Grammar grammar;

	@BeforeAll
	private static void buildGrammar() {
		// grammar for the langauge {a^n b^n | n >= 0}
		grammar = new Grammar("S0");
		
		// S0 -> Xa Z1 | E
		Util.addRule(grammar, "S0 -> Xa Z1 | E");
		// S -> Xa Z1
		Util.addRule(grammar, "S -> Xa Z1");
		// Z1 -> S Xb | b
		Util.addRule(grammar, "Z1 -> S Xb | b");
		// Xa -> a
		Util.addRule(grammar, "Xa -> a");
		// Xb -> b
		Util.addRule(grammar, "Xb -> b");

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
	 *  c3: number of a's = number of b's 
	 *  	b1: true, b2: false
	 *  c4: only a's followed by b's 
	 *  	b1: true, b2: false
	 *
	 *  Total Test Requirements (TR): 5
	 */

	/*
	 * Base Choice
	 * TR1: [b2, b1, b1, b1]
	 * Expected output: true
	 */
	@Test
	public void testOnePair() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("aaabbb", grammar));	
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
		assertFalse(CYKAlgorithm.cykAlgorithm("aaacbbb", grammar));	
	}
	
	/*
	 * TR4: [b2, b1, b2, b1]
	 * Expected output: false
	 */
	@Test
	public void testAsAndBsNotEqual() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm("aaaabbb", grammar));	
	}
	
	/*
	 * TR5: [b2, b1, b1, b2]
	 * Expected output: false
	 */
	@Test
	public void testWrongOrder() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm("aababb", grammar));	
	}
}
