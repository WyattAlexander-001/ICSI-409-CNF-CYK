package group1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class Sipser213Tests {
	
	private static Grammar grammar;

	@BeforeAll 
	private static void buildGrammar() {
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

	@Test 
	public void quickTest() {
		assertTrue(CYKAlgorithm.cykAlgorithm("#", grammar));
	}
}
