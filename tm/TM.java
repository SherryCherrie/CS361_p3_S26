package tm;

import java.util.Set;
import tm.Tape;
import tm.TMState;

public class TM implements TMInterface{

    /**
     * Adds a state to the TM instance
     *
     * @param name is the label of the state
     * @return true if a new state created successfully and false if there is already state with such name
     */
    @Override
    public boolean addState(String name) {
        return false;
    }

    /**
     * Marks an existing state as the accepting state
     *
     * @param name is the label of the state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setFinal(String name) {
        return false;
    }

    /**
     * Adds the initial state to the TM instance
     *
     * @param name is the label of the start state
     * @return true if successful and false if no state with such name exists
     */
    @Override
    public boolean setStart(String name) {
        return false;
    }

    /**
     * Adds a symbol to Sigma
     *
     * @param symbol to add to the alphabet set
     */
    @Override
    public void addSigma(char symbol) {

    }

    /**
     * Simulates the TM checking if the given string is a member of the the language
     *
     * @param s - the input string
     * @return returns the tape after the machine has halted
     */
    @Override
    public Tape accepts(String s) {
        return null;
    }

    /**
     * Getter for Sigma
     *
     * @return the alphabet of TM
     */
    @Override
    public Set<Character> getSigma() {
        return Set.of();
    }

    /**
     * Returns state with the given name, or null if none exists
     *
     * @param name of a state
     * @return state object or null
     */
    @Override
    public TMState getState(String name) {
        return null;
    }

    /**
     * Determines if a state with a given name is final
     *
     * @param name the name of the state
     * @return true if a state with that name exists and it is final
     */
    @Override
    public boolean isFinal(String name) {
        return false;
    }

    /**
     * Determines if a state with name is final
     *
     * @param name the name of the state
     * @return true if a state with that name exists and it is the start state
     */
    @Override
    public boolean isStart(String name) {
        return false;
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
        return null;
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
        return false;
    }
}