package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

//@@author weikiat97
/**
 * Manages a list of all the previous Notebooks.
 */
public class Caretaker extends Notebook {

    private ArrayList<Memento> mementos = new ArrayList<>();
    private int statePointer;
    private final Notebook notebook;

    /**
     * Initializes the list of mementos.
     * @param notebook the notebook where changes are updated in.
     */
    public Caretaker(Notebook notebook) {
        requireNonNull(notebook);
        statePointer = 0;
        mementos.add(new Memento(new Notebook(notebook)));
        this.notebook = notebook;
    }

    /**
     * Saves the current state of notebook into the list of mementos.
     */
    public void saveState() {
        mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        Notebook notebookToAdd = new Notebook(this.notebook);
        notebookToAdd.setCurrentClassroom(this.notebook.getCurrentClassroom());
        Memento mementoToAdd = new Memento(notebookToAdd);
        mementos.add(mementoToAdd);
        statePointer++;
    }

    /**
     * Gets the first ReadOnlyNotebook in the mementos, which is the initial state of the Notebook.
     */
    public ReadOnlyNotebook getInitialState() {
        return mementos.get(0).getState();
    }

    /**
     * Undoes the previous command.
     * @return ReadOnlyNotebook that is the previous state of the notebook.
     */
    public ReadOnlyNotebook undo() {
        statePointer--;
        ReadOnlyNotebook previousCopy = mementos.get(statePointer).getState();
        return previousCopy;
    }

    /**
     * Redoes the previously undone command.
     * @return ReadOnlyNotebook that is the previous state of the notebook.
     */
    public ReadOnlyNotebook redo() {
        statePointer++;
        ReadOnlyNotebook previousCopy = mementos.get(statePointer).getState();
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
