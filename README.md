# CS361_p3_S26

****************
* Project 3: Turing Machine simulator
* Class: CS361
* 18 August 2026
* Sherry de Villiers & Cesar Ochoa
****************

### OVERVIEW:
The program simulates the encoding of a deterministic bi infinite TM and its input string from a file.
It then simulates how the machine runs on that input. Once the machine reaches its halting state, it prints 
the contents of the traversed tape.

### INCLUDED FILES:
test_files - included test files
file0.txt
file2.txt
file5.txt

tm
State.java - abstract state class used by TMState
Tape.java - handles the reading, writing, and moving of tape
TM.java - implements and simulates the Turing Machine
TMInterface.java - lists the required TM methods
TMSimulator.java - main class that reads the input file, builds the machine, and the runs it
TMState.java - stores the transitions for one Turing Machine state

### COMPILING AND RUNNING:

javac -d out tm/*.java
java -cp out tm.TMSimulator <input file path>

Run and count the character in the output (excluding newlines)
java -cp out tm.TMSimulator <input file path> | tr -d '\n' | wc -c

Run and add up output
java -cp out tm.TMSimulator <input file path> | awk '{for(i=1;i<=length($0);i++) sum += substr($0,i,1)} END {print sum}'

TODO ADD ONYX INSTRUCTIONS (AND OPTIONALLY TERMINAL/PROJECT OUTPUT)

### PROGRAM DESIGN AND IMPORTANT CONCEPTS:
This program needs to simulate a Turing Machine with a bi infinite tape. It reads the machine description from
a file, starts in state 0, halts in state n-1, and prints the contents of the tape after the machine stops. TMSimulator
is the main driver. It reads the input file, parses the machine format, creates the states, sets the start and final 
states, loads sigma, adds transitions in the required order, and runs the simulation. TM is the controller for the machine.
It stores sigma, all states by name, the start state, the final state, and the working tape. The accepts method runs the
simulation loop until the machine reaches the halting state. TMState handles the transition behavior for one state.
It stores the transition rules for that state, applies the correct read, write, move operations on the tape, and
returns the next state. Tape handles the bi infinite tape structure. It reads the current symbol, writes a new symbol,
moves left or right, and outputs the final output string.

### TESTING:
We tested the program by running the three provided test files, file0.txt, file1.txt, and file2.txt.
Making sure the simulator worked correctly both in the IDE and on onyx.

### DISCUSSION:
The main difficulty of the project was adapting the logic from the last project to fit a Turing Machine instead of an NFA.
A lot of the overall structure stayed similar, especially keeping the machine logic in one main class and using a simulator
to build the machine from the file. But the actual simulation had to change from tracking state behavior in an automaton to
handling the read, write, and move process on a tape. The toughest parts were parsing the transition lines in the exact order
required by the brief and making sure the blank tape cells are handled as the character 0.
The tape also had some challenges since a char array was used to try and increase efficiency. In order to make
the char array bi infinite the tape was implemented using two char arrays one to extend in each direction with a unified
head. This way even though the head starts at zero it extended in both the positive and negative directions, because
one array handles positive indexes while the other deals with the negative ones.

--------------------------------------------------------------------------

