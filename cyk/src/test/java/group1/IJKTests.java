package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class IJKTests {
	
	private static Grammar grammar;

	@BeforeAll
	private static void buildGrammar() {
		// grammar for the langauge {a^i b^j c^k | i = j or j = k}
		grammar = new Grammar("S");

		// S -> A Xc | Xa Z1 | c | Xa B | b Z2 | a | E
		Util.addRule(grammar, "S -> A Xc | Xa Z1 | c | Xa B | b Z2 | a | E"); 
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

	@Test
	public void abcTest() {
		assertTrue(CYKAlgorithm.cykAlgorithm("abc", grammar));
	}

	@Test 
	public void jEkTest() {
		assertTrue(CYKAlgorithm.cykAlgorithm("aabbbccc", grammar));
	}

	@Test
	public void iEjTest() {
		assertTrue(CYKAlgorithm.cykAlgorithm("aaabbbcc", grammar));
	}

	@Test 
	public void failTest() {
		assertFalse(CYKAlgorithm.cykAlgorithm("aabcc", grammar));
	}
}
