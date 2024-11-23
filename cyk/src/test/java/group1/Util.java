package group1;

import java.util.LinkedList;

public class Util {

	public static void addRule(Grammar grammar, String rule) {
		
		String[] ruleSides = rule.split("->");
		if (ruleSides.length != 2) {
                throw new RuntimeException("Invalid production format. Please use 'A -> B C' or 'A -> a'.");
        }

		String lhs = ruleSides[0].trim();
		String rhs = ruleSides[1].trim();

		for (String rhsOption : rhs.split("\\|")) {
			LinkedList<String> rhsSymbols = new LinkedList<>();
			for (String symbol : rhsOption.trim().split("\\s+")) {
				rhsSymbols.add(symbol.trim());
			}
			grammar.addProduction(lhs, rhsSymbols);
        }

	}
}
