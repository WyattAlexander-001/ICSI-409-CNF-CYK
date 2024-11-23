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


	@Test 
	public void quickTest() {
		assertTrue(CYKAlgorithm.cykAlgorithm("ab#ba", grammar));
	}
}

