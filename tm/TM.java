package tm;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import tm.Tape;
import tm.TMState;

public class TM implements TMInterface{

    // Store sigma/ all TM states/ start/final states/ the working tape/ deterministic table
    private final Set<Character> sigma;
    private final Map<String, TMState> states;
    private final Map<TMState, Map<Character, Transition>> transitions;
    private TMState startState;
    private TMState finalState;
    private Tape tape;

    // Stores one transition rule for a state on a single read symbol
    private static class Transition {
        private final TMState toState;
        private final char writeSymb;
        private final boolean move;

        private Transition(TMState toState, char writeSymb, boolean move) {
            this.toState = toState;
            this.writeSymb = writeSymb;
            this.move = move;
        }
    }

    public TM() {
        this.sigma = new LinkedHashSet<Character>();
        this.states = new LinkedHashMap<String, TMState>();
        this.transitions = new LinkedHashMap<TMState, Map<Character, Transition>>();
        this.startState = null;
        this.finalState = null;
        this.tape = null;
    }

    /**
     * Adds a state to the TM instance
     *
     * @param name is the label of the state
     * @return true if a new state created successfully and false if there is already state with such name
     */
    @Override
    public boolean addState(String name) {
        if (name == null || states.containsKey(name)) {
            return false;
        }

        TMState state = new TMState(name);
        states.put(name, state);
        transitions.put(state, new LinkedHashMap<Character, Transition>());
        return true;
    }

    /**
     * Marks an existing state as the accepting state
     *
     * @param name is the label of the state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setFinal(String name) {
        TMState state = states.get(name);
        if (state == null) {
            return false;
        }

        finalState = state;
        return true;
    }

    /**
     * Adds the initial state to the TM instance
     *
     * @param name is the label of the start state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setStart(String name) {
        TMState state = states.get(name);
        if (state == null) {
            return false;
        }

        startState = state;
        return true;
    }

    /**
     * Adds a symbol to Sigma
     *
     * @param symbol to add to the alphabet set
     */
    @Override
    public void addSigma(char symbol) {
        if (symbol != '0') {
            sigma.add(symbol);
        }
    }

    /**
     * Simulates the TM checking if the given string is a member of the the language
     *
     * @param s - the input string
     * @return returns the tape after the machine has halted
     */
    @Override
    public Tape accepts(String s) {
        if (startState == null) {
            return null;
        }

        tape = new Tape(s == null ? "" : s);
        TMState current = startState;

        // Run transitions until the halting/final state is reached.
        while (current != null && current != finalState) {
            current = transition(current);
        }

        return tape;
    }

    /**
     * Getter for Sigma
     *
     * @return the alphabet of TM
     */
    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<Character>(sigma);
    }

    /**
     * Returns state with the given name, or null if none exists
     *
     * @param name of a state
     * @return state object or null
     */
    @Override
    public TMState getState(String name) {
        return states.get(name);
    }

    /**
     * Determines if a state with a given name is final
     *
     * @param name the name of the state
     * @return true if a state with that name exists and it is final
     */
    @Override
    public boolean isFinal(String name) {
        TMState state = states.get(name);
        return state != null && state == finalState;
    }

    /**
     * Determines if a state with name is final
     *
     * @param name the name of the state
     * @return true if a state with that name exists and it is the start state
     */
    @Override
    public boolean isStart(String name) {
        TMState state = states.get(name);
        return state != null && state == startState;
    }

    /**
     * performs a transition from one state to another
     * does reading and writing on the tape for the specified transition
     *
     * @param from - the source state
     * @return the tostate if successful and null if failed
     */
    @Override
    public TMState transition(TMState from) {
        if (from == null || tape == null) {
            return null;
        }

        // Tape.readTape() uses numeric 0 for an uninitialized blank cell
        // normalize that into the blank symbol character 0
        int rawRead = tape.readTape();
        char readSymb = (rawRead == 0) ? '0' : (char) rawRead;

        Map<Character, Transition> stateTransitions = transitions.get(from);
        if (stateTransitions == null) {
            return null;
        }

        Transition next = stateTransitions.get(readSymb);
        if (next == null) {
            return null;
        }

        tape.writeMove(next.writeSymb, next.move);
        return next.toState;
    }

    /**
     * Adds the transition to the TM's data structure
     *
     * @param fromState is the label of the state where the transition starts
     * @param toState   is the label of the states where the transition ends
     * @param readSymb  is the symbol from the TM's alphabet.
     * @param writeSymb is the symbol from the TM's alphabet.
     * @param move      false for backwards/Left and true for forwards/Right.
     * @return true if successful and false if one of the states don't exist or the symbol in not in the alphabet
     */
    @Override
    public boolean addTransition(String fromState, String toState, char readSymb, char writeSymb, boolean move) {
        TMState source = states.get(fromState);
        TMState destination = states.get(toState);

        if (source == null || destination == null) {
            return false;
        }

        // valid tape symbols are blank 0 plus anything already added to sigma
        if ((readSymb != '0' && !sigma.contains(readSymb)) ||
            (writeSymb != '0' && !sigma.contains(writeSymb))) {
            return false;
        }

        Map<Character, Transition> stateTransitions = transitions.get(source);
        if (stateTransitions == null) {
            stateTransitions = new LinkedHashMap<Character, Transition>();
            transitions.put(source, stateTransitions);
        }

        stateTransitions.put(readSymb, new Transition(destination, writeSymb, move));
        return true;
    }
}