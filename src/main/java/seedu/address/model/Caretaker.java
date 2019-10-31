package seedu.address.model;

import java.util.ArrayList;

/**
 * Manages a list of all the previous Notebooks.
 */
public class Caretaker extends Notebook {

    private ArrayList<Memento> mementos = new ArrayList<>();
    private int statePointer;
    private final Notebook notebook;

    /**
     * Initializes the list of mementos.
     * @param notebook the notebook where changes are updated in
     */
    public Caretaker(Memento start, Notebook notebook) {
        statePointer = 0;
        mementos.add(start);
        this.notebook = notebook;
        //Notebook toAdd = new Notebook(this.notebook);
        //toAdd.setClassrooms(notebook.getClassroomList());
        //mementos.add(new Memento(toAdd));
        //mementos.add(new Memento(new Notebook(this.notebook)));
        //System.out.println("START: ");
        //mementos.get(statePointer).getState().getClassroomList().get(0).getAssignmentList()
        // .forEach(assignment -> System.out.println(assignment));
    }

    /**
     * Saves the current state of notebook into the list of mementos.
     */
    public void saveState() {
        //System.out.println("FIRST");
        //mementos.get(0).getState().getClassroomList().get(0).getAssignmentList()
        // .forEach(assignment -> System.out.println(assignment));
        mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        Memento mementoToAdd = new Memento(new Notebook(this.notebook));
        mementos.add(mementoToAdd);
        //System.out.println("SIZE::  " + mementos.size());
        //System.out.println(mementos.get(0).getState().equals(mementos.get(1).getState()));
        statePointer++;
    }

    /**
     * Undoes the previous command.
     * @return ReadOnlyNotebook that is the previous state of the notebook.
     */
    public ReadOnlyNotebook undo() {
        statePointer--;
        ReadOnlyNotebook previousCopy = mementos.get(statePointer).getState();
        resetData(previousCopy);
        return previousCopy;
    }

    /**
     * Redoes the previously undone command.
     * @return ReadOnlyNotebook that is the previous state of the notebook.
     */
    public ReadOnlyNotebook redo() {
        statePointer++;
        ReadOnlyNotebook previousCopy = mementos.get(statePointer).getState();
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
