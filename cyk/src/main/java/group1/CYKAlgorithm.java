package group1;

import java.util.*;

public class CYKAlgorithm {

    static class Grammar {
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

    // CYK algorithm implementation
    public static boolean cykAlgorithm(String input, Grammar grammar) {
        int n = input.length();

        if (n == 0) {
            // Check if the start symbol can derive epsilon (E)
            List<List<String>> startProductions = grammar.productions.get(grammar.startSymbol);
            if (startProductions != null) {
                for (List<String> rhs : startProductions) {
                    if (rhs.size() == 1 && rhs.get(0).equals("E")) {
                        return true;
                    }
                }
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        Set<String>[][] table = new HashSet[n][n];

        // Initialize the table with productions that produce terminal symbols
        for (int i = 0; i < n; i++) {
            table[i][i] = new HashSet<>();
            String terminal = String.valueOf(input.charAt(i));
            System.out.println("Processing terminal '" + terminal + "' at position " + i);

            for (String variable : grammar.nonTerminals) {
                List<List<String>> rhsList = grammar.productions.get(variable);
                if (rhsList != null) {
                    for (List<String> rhs : rhsList) {
                        if (rhs.size() == 1 && rhs.get(0).equals(terminal)) {
                            table[i][i].add(variable);
                            System.out.println("Adding variable '" + variable + "' to table[" + i + "][" + i + "] because it produces '" + terminal + "'");
                        }
                    }
                }
            }
        }

        // Fill the table using the CYK algorithm
        for (int length = 2; length <= n; length++) { // Length of the span
            for (int i = 0; i <= n - length; i++) { // Start of the span
                int j = i + length - 1; // End of the span
                table[i][j] = new HashSet<>();

                for (int k = i; k < j; k++) { // Partition of the span
                    for (String variable : grammar.nonTerminals) {
                        List<List<String>> rhsList = grammar.productions.get(variable);
                        if (rhsList != null) {
                            for (List<String> rhs : rhsList) {
                                if (rhs.size() == 2) {
                                    String B = rhs.get(0);
                                    String C = rhs.get(1);
                                    if (table[i][k].contains(B) && table[k + 1][j].contains(C)) {
                                        table[i][j].add(variable);
                                        System.out.println("Adding variable '" + variable + "' to table[" + i + "][" + j + "] because it derives from '" + B + "' at table[" + i + "][" + k + "] and '" + C + "' at table[" + (k + 1) + "][" + j + "]");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check if the start symbol can derive the entire string
        return table[0][n - 1].contains(grammar.startSymbol);
    }

    // Method to read a grammar from the user
    public static Grammar readGrammar(Scanner scanner) {
        System.out.println("\nEnter the grammar in CNF (enter 'END' to finish):");
        System.out.println("Format: S -> AB | a");
        System.out.println("Use '|' to separate multiple productions for the same variable.");
        System.out.println("Use 'E' to represent the empty string (only allowed for the start symbol).");

        Grammar grammar = null;

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("->");
            if (parts.length != 2) {
                System.out.println("Invalid production format. Please use 'A -> B C' or 'A -> a'.");
                continue;
            }

            String lhs = parts[0].trim();
            if (grammar == null) {
                // The first LHS is assumed to be the start symbol
                grammar = new Grammar(lhs);
            }
            String rhsPart = parts[1].trim();
            String[] rhsOptions = rhsPart.split("\\|");
            for (String rhsOption : rhsOptions) {
                List<String> rhsSymbols = new ArrayList<>();
                for (String symbol : rhsOption.trim().split("\\s+")) {
                    rhsSymbols.add(symbol.trim());
                }
                grammar.addProduction(lhs, rhsSymbols);
            }
        }

        if (grammar == null) {
            System.out.println("No grammar provided.");
            return null;
        }

        // Display the grammar for debugging purposes
        grammar.displayGrammar();

        return grammar;
    }

    // Method to parse strings using the current grammar
    public static void parseStrings(Scanner scanner, Grammar grammar) {
        while (true) {
            // Read the input string
            System.out.print("\nEnter the string to be parsed (or type 'BACK' to return to the main menu): ");
            String input = scanner.nextLine().trim();

            // Check if the user wants to return to the main menu
            if (input.equalsIgnoreCase("BACK")) {
                System.out.println("Returning to the main menu.");
                break;
            }

            // Handle empty input
            if (input.isEmpty()) {
                input = "E";
            }

            // Run the CYK algorithm
            boolean result = cykAlgorithm(input, grammar);

            // Output the result
            if (result) {
                System.out.println("The string is in the language generated by the grammar.");
            } else {
                System.out.println("The string is NOT in the language generated by the grammar.");
            }

            // Ask if the user wants to parse another string
            System.out.print("Do you want to parse another string with the current grammar? (yes/no): ");
            String again = scanner.nextLine().trim();
            if (!again.equalsIgnoreCase("yes") && !again.equalsIgnoreCase("y")) {
                System.out.println("Returning to the main menu.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome To The CYK Algorithm App!");

            Grammar grammar = null;

            while (true) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Enter a new grammar");
                System.out.println("2. Parse strings with the current grammar");
                System.out.println("3. Exit");
                System.out.print("Please enter your choice (1-3): ");
                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    // Option 1: Enter a new grammar
                    grammar = readGrammar(scanner);
                } else if (choice.equals("2")) {
                    // Option 2: Parse strings with the current grammar
                    if (grammar == null) {
                        System.out.println("No grammar has been entered yet. Please enter a grammar first.");
                        continue;
                    }
                    parseStrings(scanner, grammar);
                } else if (choice.equals("3")) {
                    // Option 3: Exit the program
                    System.out.println("Exiting the program.");
                    break;
                } else {
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
