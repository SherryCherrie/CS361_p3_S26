package tm;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import tm.Tape;
import tm.TMState;

/**
 * A turing machine
 * Given the information for a valid turing machine this class can simulate its behaviour
 * This class contains the language alphabet the states and tape for the machine
 * @author Sherry de Villiers and Cesar Ochoa
 */
public class TM implements TMInterface{

    // Store sigma/ all TM states/ start/final states/ the working tape
    private final Set<Character> sigma;
    private final Map<String, TMState> states;
    private TMState startState;
    private TMState finalState;
    private Tape tape;


    public TM() {
        this.sigma = new LinkedHashSet<Character>();
        this.states = new LinkedHashMap<String, TMState>();
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
     * Simulates the TM checking if the given string is a member of the language
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
        return from.transition(tape);
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

        return source.addTransition(readSymb, writeSymb, destination, move);
    }
}