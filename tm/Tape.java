package tm;


/**
 * Tape class
 * moves writes and reads along the given string
 * uses char[] data type in an attempt to improve efficiency
 * ArrayList's would probably be more suited otherwise
 * The class has a forward and backward tape to back it easier to expand the tape in both directions
 * @author @author Sherry de Villiers and Cesar Ochoa
 */
public class Tape {
    char[] forwardTape;
    char[] backwardTape;
    int tapeHead;
    int forwardLen;
    int backwardLen;
    int increaseBy;

    public Tape(String initialStr) {
        if(initialStr == null || initialStr.isEmpty()){
            this.forwardTape = new char['0'];
            this.forwardLen = 1;
        }else {
            this.forwardTape = initialStr.toCharArray();
            this.forwardLen = initialStr.length();
        }
        this.tapeHead = 0;
        this.backwardLen = 10;
        this.backwardTape = new char[backwardLen];
        this.increaseBy = 50;
    }

    /**
     * writes the given symbol to the current head position and then moves
     * the tape head to the right if move is true and to the left if move is false
     * @param writeSymb is written to the tapeHead
     * @param move indicates the direct the head moves in
     */
    public void writeMove(char writeSymb, boolean move) {
        if(tapeHead >= 0) {
            forwardTape[tapeHead] = writeSymb;
            if (move) {
                //Move right on the tape
                tapeHead++;
                //increase tape size if end reached
                if (tapeHead >= forwardLen) {
                    char[] newArray = new char[forwardLen + increaseBy]; // Increase size by 10
                    System.arraycopy(forwardTape, 0, newArray, 0, forwardLen);
                    forwardLen += increaseBy;
                    forwardTape = newArray;
                }
            }else{
                //Move left on the tape
                tapeHead--;
            }
        }else{
            backwardTape[-1-tapeHead] = writeSymb;
            if (move) {
                //Move right on the tape
                tapeHead++;
            }else{
                //Move left on the tape
                tapeHead--;
                //increase tape size if end reached
                if ( (-1-tapeHead) >= backwardLen) {
                    char[] newArray = new char[backwardLen + increaseBy]; // Increase size by 10
                    System.arraycopy(backwardTape, 0, newArray, 0, backwardLen);
                    backwardLen += increaseBy;
                    backwardTape = newArray;
                }
            }
        }
    }

    /**
     * Returns the character the head is currently pointing to
     * if the char is a black returns '0'
     * @return char at the tapeHead
     */
    public char readTape() {
        if(tapeHead >=0) {
            if(forwardTape[tapeHead]!='\0') {
                return forwardTape[tapeHead];
            }else{
                forwardTape[tapeHead] = '0';
                return '0';
            }
        }else{
            if(backwardTape[-1-tapeHead]!='\0') {
                return backwardTape[-1-tapeHead];
            }else{
                backwardTape[-1-tapeHead] = '0';
                return '0';
            }
        }
    }

    /**
     * get the total length of the tape including white space
     * @return the length
     */
    public int length() {
        return forwardLen + backwardLen;
    }

    /**
     * Returns a string of chars in the tape without leading and trailing null characters
     * @return
     */
    @Override
    public String toString(){
        if (forwardTape == null || forwardTape[0] == '\0') {
            return "";
        }

        //length of backwardTape that I want to include goes from index 0 to start
        int start = backwardLen-1;
        if(backwardTape[0]!='\0') {
            // Find first significant char
//            while (start > -1 && (backwardTape[start] == '\0' || backwardTape[start] == '0')) {
            while (start > -1 && (backwardTape[start] == '\0')) {
                start--;
            }
        }else{
            start = -1;
        }

        //length of forwardTape that I want to include goes from index 0 to end
        // Find last significant char
        int end = forwardLen-1;
//        while (end > 0 && (forwardTape[end] == '\0' || forwardTape[end] == '0')) {
        while (end > 0 && (forwardTape[end] == '\0')) {
            end--;
        }

        if(start < 0) {
            return new String(forwardTape, 0, end + 1);
        }else{
            char[] combineArrays = new char[start + end + 2];
            for (int i = 0; i < start+1; i++) {
                combineArrays[i] = backwardTape[ start - i];
            }
            System.arraycopy(forwardTape, 0, combineArrays, start+1, end+1);
            return new String(combineArrays);
        }
    }
}
