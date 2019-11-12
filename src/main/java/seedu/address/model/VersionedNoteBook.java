package seedu.address.model;

import java.util.Stack;

import seedu.address.model.note.UniqueNoteList;
import seedu.address.model.note.exceptions.InvalidRedoException;
import seedu.address.model.note.exceptions.InvalidUndoException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class VersionedNoteBook extends NoteBook {
    private Stack<NoteBookWithCommand> undoStack = new Stack<>();
    private Stack<NoteBookWithCommand> redoStack = new Stack<>();

    public VersionedNoteBook(ReadOnlyNoteBook noteBook) {
        super(noteBook);
    }

    public VersionedNoteBook() {
        super();
    }

    /**
     * Commits the current state of the application into the list of state as the latest state.
     */
    public void commit(String command) {
        UniqueNoteList currentUniqueNoteList = new UniqueNoteList();
        currentUniqueNoteList.setNotes(getNotes());
        if (undoStack.empty()) {
            ReadOnlyNoteBook currentNoteBookState = new NoteBook(currentUniqueNoteList, getSortByConds());
            undoStack.push(new NoteBookWithCommand(currentNoteBookState, command));
        } else {
            ReadOnlyNoteBook previousNoteBookState = undoStack.peek().getNoteBook();
            if (!isSameNoteBook(previousNoteBookState)) {
                ReadOnlyNoteBook currentNoteBookState = new NoteBook(currentUniqueNoteList, getSortByConds());
                undoStack.push(new NoteBookWithCommand(currentNoteBookState, command));
            }
        }
        while (!redoStack.empty()) {
            redoStack.pop();
        }
    }


    /**
     * Undo by changing the state of the NoteBook to the previous state.
     */
    public String undo() {
        if (!undoStack.empty()) {
            ReadOnlyNoteBook previousNoteBookState = undoStack.peek().getNoteBook();
            String previousCommand = undoStack.peek().getCommand();
            if (!isSameNoteBook(previousNoteBookState)) {
                updateRedoStack(previousCommand);
                updateCurrentState(previousNoteBookState);
                undoStack.pop();
                return previousCommand;
            } else {
                undoStack.pop();
                return undo();
            }
        } else {
            throw new InvalidUndoException();
        }
    }


    /**
     * Redo by changing the state of the NoteBook to the next state.
     */
    public String redo() {
        //TODO: refactor this to make it cleaner
        if (!redoStack.empty()) {
            ReadOnlyNoteBook nextNoteBookState = redoStack.peek().getNoteBook();
            String nextCommand = redoStack.peek().getCommand();
            updateUndoStack(nextCommand);
            updateCurrentState(nextNoteBookState);
            redoStack.pop();
            return nextCommand;
        } else {
            throw new InvalidRedoException();
        }
    }

    /**
     * Couples the undoable command with the Notebook state.
     */
    private static class NoteBookWithCommand {
        private ReadOnlyNoteBook notebook;
        private String command;

        public NoteBookWithCommand(ReadOnlyNoteBook notebook, String command) {
            this.notebook = notebook;
            this.command = command;
        }

        public ReadOnlyNoteBook getNoteBook() {
            return this.notebook;
        }

        public String getCommand() {
            return this.command;
        }
    }

    /**
     * Checks if current NoteBook state is the same as previous NoteBook state.
     */
    private boolean isSameNoteBook(ReadOnlyNoteBook previousNoteBookState) {
        return previousNoteBookState.getNoteList().equals(getNoteList())
                && previousNoteBookState.getSortByConds().equals(getSortByConds());
    }

    /**
     * Updates redo stack on undo command call.
     */
    private void updateRedoStack(String previousCommand) {
        UniqueNoteList currentUniqueNoteList = new UniqueNoteList();
        currentUniqueNoteList.setNotes(getNotes());
        ReadOnlyNoteBook currentNoteBookState = new NoteBook(currentUniqueNoteList, getSortByConds());
        redoStack.push(new NoteBookWithCommand(currentNoteBookState, previousCommand));
    }

    /**
     * Updates undo stack on redo command call.
     */
    private void updateUndoStack(String nextCommand) {
        UniqueNoteList currentUniqueNoteList = new UniqueNoteList();
        currentUniqueNoteList.setNotes(getNotes());
        ReadOnlyNoteBook currentNoteBookState = new NoteBook(currentUniqueNoteList, getSortByConds());
        undoStack.push(new NoteBookWithCommand(currentNoteBookState, nextCommand));
    }

    /**
     * Updates current state of NoteBook.
     * @param noteBookState state of NoteBook to be updated to.
     */
    private void updateCurrentState(ReadOnlyNoteBook noteBookState) {
        setNotes(noteBookState.getNoteList());
        setSortByCond(noteBookState.getSortByConds());
    }
}
