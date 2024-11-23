package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalancedParenthesesTests {
	private static Grammar grammar;

	@BeforeAll
	private static void buildGrammar() {
		grammar = new Grammar("S");
		// S -> OZ | AA | E
		Util.addRule(grammar, "S -> O Z | A A | E");
		// A -> OZ | AA
		Util.addRule(grammar, "A -> O Z | A A");
		// Z -> AC | )
		Util.addRule(grammar, "Z -> A C | )");
		// O -> (
		Util.addRule(grammar, "O -> (");
		// C -> )
		Util.addRule(grammar, "C -> )");
	} 

	@Test
	public void onePairMatching() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("()", grammar));	
	}

	@Test
	public void nestedPairsMatching() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("(()(()))", grammar));	
	}

	@Test
	public void emptyMatching() throws Exception {
		assertTrue(CYKAlgorithm.cykAlgorithm("", grammar));	
	}

	@Test
	public void unclosedNotMatching() throws Exception {
		assertFalse(CYKAlgorithm.cykAlgorithm("(", grammar));	
	}
}
