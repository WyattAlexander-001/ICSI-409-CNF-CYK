package group1;

import java.util.*;

public class Grammar {
        Set<String> nonTerminals; // Non-terminal symbols
        Set<String> terminals; // Terminal symbols
        Map<String, List<List<String>>> productions; // Productions: LHS -> RHS
        String startSymbol;

        public Grammar(String startSymbol) {
            this.startSymbol = startSymbol;
            this.nonTerminals = new HashSet<>();
            this.terminals = new HashSet<>();
            this.productions = new HashMap<>();
        }

        // Method to add a production to the grammar
        public void addProduction(String lhs, List<String> rhs) {
            nonTerminals.add(lhs);
            for (String symbol : rhs) {
                if (symbol.matches("[a-z0-9()]") || symbol.matches("\\W")) {
                    // Symbol is a terminal
                    terminals.add(symbol);
                } else if (!symbol.equals("E")) {
                    // Symbol is a variable (non-terminal), excluding epsilon 'E'
                    nonTerminals.add(symbol);
                }
            }
            productions.computeIfAbsent(lhs, k -> new ArrayList<>()).add(rhs);
        }

        // Method to display the grammar (for debugging purposes)
        public void displayGrammar() {
            System.out.println("Start Symbol: " + startSymbol);
            System.out.println("nonTerminals: " + nonTerminals);
            System.out.println("Terminals: " + terminals);
            System.out.println("Productions:");
            for (Map.Entry<String, List<List<String>>> entry : productions.entrySet()) {
                for (List<String> rhs : entry.getValue()) {
                    System.out.println(entry.getKey() + " -> " + String.join(" ", rhs));
                }
            }
        }
    }

