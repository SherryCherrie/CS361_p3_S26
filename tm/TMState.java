package tm;

import java.util.LinkedHashMap;
import java.util.Map;

public class TMState extends State{

    private final Map<Character, Transition> transitions;
    /**
     * give the new state a name
     *
     * @param name of the state, used as a unique identifier
     */
    public TMState(String name) {
        super(name);
        transitions = new LinkedHashMap<>();
    }

    /**
     * adds a valid transition to this state
     * Since state does not have access to sigma and other states
     * whether the transition is valid or not must be checked before using this function
     * @param readSymb character that must be read during the transition
     * @param writeSymb character that must be read during the transition
     * @param destination state moved to on this transition
     * @param move direction the tape moves in during transitioni
     * @return whether the transition was succesfully added or not
     */
    public boolean addTransition(char readSymb, char writeSymb, TMState destination, boolean move){
        transitions.put(readSymb, new Transition(destination, writeSymb, move));
        return transitions.containsKey(readSymb);
    }

    /**
     * Take the tape and performs the next transition on it
     * returns null if there is no valid transition for the symbol read from the tape
     * @param tape
     * @return
     */
    public TMState transition(Tape tape){
        char readSymb = tape.readTape();
        Transition currentTransition = transitions.get(readSymb);
        if(currentTransition == null){
            return null;
        }
        tape.writeMove(currentTransition.writeSymb, currentTransition.move);
        return currentTransition.toState;
    }

    // Stores one transition rule for a state on a single read symbol
        private record Transition(TMState toState, char writeSymb, boolean move) {
    }
}