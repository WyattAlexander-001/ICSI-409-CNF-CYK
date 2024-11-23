package group1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalancedParenthesesTests {
	private static Grammar grammar;

	@BeforeAll
	private static void buildGrammar() {
		grammar = new Grammar("S");
		// S -> OZ | AA | E
		grammar.addProduction("S", new LinkedList<>(Arrays.asList(new String[] {"O", "Z"})));
		grammar.addProduction("S", new LinkedList<>(Arrays.asList(new String[] {"A", "A"})));
		grammar.addProduction("S", new LinkedList<>(Arrays.asList(new String[] {"E"})));
		// A -> OZ | AA
		grammar.addProduction("A", new LinkedList<>(Arrays.asList(new String[] {"O", "Z"})));
		grammar.addProduction("A", new LinkedList<>(Arrays.asList(new String[] {"A", "A"})));
		// Z -> AC | )
		grammar.addProduction("Z", new LinkedList<>(Arrays.asList(new String[] {"A", "C"})));
		grammar.addProduction("Z", new LinkedList<>(Arrays.asList(new String[] {")"})));
		// O -> (
		grammar.addProduction("O", new LinkedList<>(Arrays.asList(new String[] {"("})));
		// C -> )
		grammar.addProduction("C", new LinkedList<>(Arrays.asList(new String[] {")"})));
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
