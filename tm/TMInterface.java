package tm;

import java.util.Set;

/**
 * Interface detailing methods for TM classes
 * @author Sherry de Villiers and Cesar Ochoa
 */
public interface TMInterface{

    /**
     * Adds a state to the TM instance
     * @param name is the label of the state
     * @return true if a new state created successfully and false if there is already state with such name
     */
    public boolean addState(String name);

    /**
     * Marks an existing state as the accepting state
     * @param name is the label of the state
     * @return true if successful and false if no state with such name exists
     */
    public boolean setFinal(String name);

    /**
     * Adds the initial state to the TM instance
     * @param name is the label of the start state
     * @return true if successful and false if no state with such name exists
     */
    public boolean setStart(String name);

    /**
     * Adds a symbol to Sigma
     * @param symbol to add to the alphabet set
     */
    public void addSigma(char symbol);

    /**
     * Simulates the TM checking if the given string is a member of the the language
     * @param s - the input string
     * @return returns the tape after the machine has halted
     */
    public Tape accepts(String s);

    /**
     * Getter for Sigma
     * @return the alphabet of TM
     */
    public Set<Character> getSigma();

    /**
     * Returns state with the given name, or null if none exists
     * @param name of a state
     * @return state object or null
     */
    public TMState getState(String name);

    /**
     * Determines if a state with a given name is final
     * @param name the name of the state
     * @return true if a state with that name exists and it is final
     */
    public boolean isFinal(String name);

    /**
     * Determines if a state with name is final
     * @param name the name of the state
     * @return true if a state with that name exists and it is the start state
     */
    public boolean isStart(String name);

    /**
     * NOTE: Might not need this one
     * performs a transition from one state to another
     * does reading and writing on the tape for the specified transition
     * @param from - the source state
     * @param onSymb - the label of the transition
     * @return the tostate if successful and null if failed
     */
//    public TMState transition(TMState from, char onSymb);

    /**
     * performs a transition from one state to another
     * does reading and writing on the tape for the specified transition
     * @param from - the source state
     * @return the tostate if successful and null if failed
     */
    public TMState transition(TMState from);

    /**
     * Adds the transition to the TM's data structure
     * @param fromState is the label of the state where the transition starts
     * @param toState is the label of the states where the transition ends
     * @param readSymb is the symbol from the TM's alphabet.
     * @param writeSymb is the symbol from the TM's alphabet.
     * @param move false for backwards/Left and true for forwards/Right.
     * @return true if successful and false if one of the states don't exist or the symbol in not in the alphabet
     */
    public boolean addTransition(String fromState, String toState, char readSymb, char writeSymb, boolean move);
}
