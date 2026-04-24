package tm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class TMSimulator
 * This class reads the Turing machine encoding from the input file it builds the machine, runs the simulation, and prints the contents of the visited tape cells after halting
 * NOTES:
 *  State 0 is always the strt state
 *  State n-1 is always the final state
 *  Tape symbol '0' is the blank symbol and is not added to sigma
 *  Transition lines are read in the order provided in the project specs
 *
 * @author Cesar Ochoa, Sherry de Villiers
 * @date 4/20/2026
 */
public class TMSimulator {

    /**
     * Read the TM encoding file, builds the TM, run the machine, prints the final tape contents.
     * @param args command line args, where args[0] is the input file name
     * @author Cesar Ochoa, Melody de Villiers
     */
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.err.println("Usage: java tm.TMSimulator <input file>");
            return;
        }

        try {
            List<String> lines = readAllLines(args[0]);

            if (lines.size() < 2) {
                throw new IllegalArgumentException("Input file is missing required header lines.");
            }

            int numStates = Integer.parseInt(lines.get(0).trim());
            int sigmaSize = Integer.parseInt(lines.get(1).trim());
            TM tm = new TM();

            // Add states 0 through n-1.
            for (int i = 0; i < numStates; i++) {
                tm.addState(String.valueOf(i));
            }

            // Per project specs: strt state is always 0, halting/final state is always n-1
            tm.setStart("0");
            tm.setFinal(String.valueOf(numStates - 1));

            // Sigma is {m}, Blank 0 is part of tape alphabet, not sigma
            for (int i = 1; i <= sigmaSize; i++) {
                tm.addSigma((char) ('0' + i));
            }

            int transitionLineIndex = 2;

            // Transition order: for each non-halting state, for each tape symbol
            for (int stateNum = 0; stateNum < numStates - 1; stateNum++) {
                for (int symbolNum = 0; symbolNum <= sigmaSize; symbolNum++) {
                    if (transitionLineIndex >= lines.size()) {
                        throw new IllegalArgumentException("Input file ended before all transitions were read.");
                    }

                    String transitionLine = lines.get(transitionLineIndex).trim();
                    transitionLineIndex++;
                    if (transitionLine.isEmpty()) {
                        throw new IllegalArgumentException("Empty transition line.");
                    }
                    String[] parts = transitionLine.split(",");
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Invalid transition format: " + transitionLine);
                    }

                    String fromState = String.valueOf(stateNum);
                    String toState = parts[0].trim();

                    // NOTE for TM/TMState
                    // symbols in the file are numeric text NEED to convert them to char digits
                    char readSymb = (char) ('0' + symbolNum);
                    char writeSymb = (char) ('0' + Integer.parseInt(parts[1].trim()));

                    String moveText = parts[2].trim();
                    boolean moveRight;

                    if (moveText.equals("R")) {
                        moveRight = true;
                    } else if (moveText.equals("L")) {
                        moveRight = false;
                    } else {
                        throw new IllegalArgumentException("Invalid move direction: " + moveText);
                    }
                    boolean added = tm.addTransition(fromState, toState, readSymb, writeSymb, moveRight);

                    if (!added) {
                        throw new IllegalStateException(
                            "Failed to add transition from state " + fromState +
                            " on symbol " + readSymb + " using line: " + transitionLine
                        );
                    }
                }
            }

            // If the file contains a final line after all transitions thats is the input str
            // If not present the input is epsilon/empty str
            String input = "";
            if (transitionLineIndex < lines.size()) {
                input = lines.get(transitionLineIndex);
            }

            Tape resultTape = tm.accepts(input);
            System.out.println(resultTape);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Simulation error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reads all lines from a file while preserving blank lines
     * @param fileName the input file name
     * @return a list of lines from the file
     * @throws IOException if the file cannot be read
     * @author Cesar Ochoa, Melody de Villiers
     */
    private static List<String> readAllLines(String fileName) throws IOException {
        List<String> lines = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            reader.close();
        }

        return lines;
    }
}