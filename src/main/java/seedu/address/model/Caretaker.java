package seedu.address.model;

import java.util.ArrayList;

/**
 * Manages a list of all the previous Classrooms.
 */
public class Caretaker extends Classroom {

    private ArrayList<Memento> mementos = new ArrayList<>();
    private int statePointer;
    private final Classroom classroom;

    /**
     * Initializes the list of mementos.
     * @param start the first state of the classroom.
     * @param classroom the classroom where changes are updated in
     */
    public Caretaker(Memento start, Classroom classroom) {
        statePointer = 0;
        mementos.add(start);
        this.classroom = classroom;
    }

    /**
     * Saves the current state of classroom into the list of mementos.
     */
    public void saveState() {
        mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        Memento mementoToAdd = new Memento(new Classroom(this.classroom));
        mementos.add(mementoToAdd);
        statePointer++;
    }

    /**
     * Undoes the previous command.
     * @return ReadOnlyClassroom that is the previous state of the classroom.
     */
    public ReadOnlyClassroom undo() {
        statePointer--;
        ReadOnlyClassroom previousCopy = mementos.get(statePointer).getState();
        resetData(previousCopy);
        return previousCopy;
    }

    /**
     * Redoes the previously undone command.
     * @return ReadOnlyClassroom that is the previous state of the classroom.
     */
    public ReadOnlyClassroom redo() {
        statePointer++;
        ReadOnlyClassroom previousCopy = mementos.get(statePointer).getState();
        resetData(previousCopy);
        return previousCopy;
    }

    /**
     * Checks if there is anything to undo.
     * @return true if there are commands to undo.
     */
    public boolean canUndo() {
        return statePointer > 0;
    }

    /**
     * Checks if there is anything to redo.
     * @return true if there are commands to redo.
     */
    public boolean canRedo() {
        return statePointer < (mementos.size() - 1);
    }
}
