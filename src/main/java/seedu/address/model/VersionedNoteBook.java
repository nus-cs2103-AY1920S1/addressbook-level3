package seedu.address.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import seedu.address.model.note.UniqueNoteList;
import seedu.address.model.note.exceptions.InvalidRedoException;
import seedu.address.model.note.exceptions.InvalidUndoException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class VersionedNoteBook extends NoteBook {
    private List<ReadOnlyNoteBook> noteBookStateList = new LinkedList<>();
    private ListIterator<ReadOnlyNoteBook> currentStatePointer = noteBookStateList.listIterator();

    public VersionedNoteBook(ReadOnlyNoteBook noteBook) {
        super(noteBook);
    }

    public VersionedNoteBook() {
        super();
    }

    /**
     * Commits the current state of the application into the list of state as the latest state.
     */
    public void commit() {
        if (!currentStatePointer.hasPrevious()) {
            UniqueNoteList newUniqueNoteList = new UniqueNoteList();
            newUniqueNoteList.setNotes(getNotes());
            currentStatePointer.add(new NoteBook(newUniqueNoteList, getSortByCond()));
        } else {
            ReadOnlyNoteBook previousNoteBookState = currentStatePointer.previous();
            if (!(previousNoteBookState.getNoteList().equals(getNoteList())
                    && previousNoteBookState.getSortByCond().equals(getSortByCond()))) {
                UniqueNoteList newUniqueNoteList = new UniqueNoteList();
                newUniqueNoteList.setNotes(getNotes());
                currentStatePointer.next();
                currentStatePointer.add(new NoteBook(newUniqueNoteList, getSortByCond()));
            } else {
                currentStatePointer.next();
            }
        }
        while (currentStatePointer.hasNext()) {
            currentStatePointer.next();
            currentStatePointer.remove();
        }
    }

    /**
     * Undo by changing the state of the NoteBook to the previous state.
     */
    public void undo() {
        if (currentStatePointer.hasPrevious()) {
            if (!currentStatePointer.hasNext()) {
                ReadOnlyNoteBook previousNoteBookState = currentStatePointer.previous();
                if (!(previousNoteBookState.getNoteList().equals(getNoteList())
                        && previousNoteBookState.getSortByCond().equals(getSortByCond()))) {
                    UniqueNoteList newUniqueNoteList = new UniqueNoteList();
                    newUniqueNoteList.setNotes(getNotes());
                    currentStatePointer.next();
                    currentStatePointer.add(new NoteBook(newUniqueNoteList, getSortByCond()));
                    currentStatePointer.previous();
                }
            }
            ReadOnlyNoteBook previousStateNoteBook = currentStatePointer.previous();
            setNotes(previousStateNoteBook.getNoteList());
            setSortByCond(previousStateNoteBook.getSortByCond());
        } else {
            throw new InvalidUndoException();
        }
    }

    /**
     * Redo by changing the state of the NoteBook to the next state.
     */
    public void redo() {
        //TODO: refactor this to make it cleaner
        if (currentStatePointer.hasNext()) {
            currentStatePointer.next();
            if (currentStatePointer.hasNext()) {
                ReadOnlyNoteBook nextStateNoteBook = currentStatePointer.next();
                setNotes(nextStateNoteBook.getNoteList());
                setSortByCond(nextStateNoteBook.getSortByCond());
                currentStatePointer.previous();
            } else {
                currentStatePointer.previous();
                throw new InvalidRedoException();
            }
        } else {
            throw new InvalidRedoException();
        }
    }

}
