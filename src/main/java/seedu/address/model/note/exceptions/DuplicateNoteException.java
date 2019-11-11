package seedu.address.model.note.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTE;

/**
 * Signals that the operation will result in duplicate Notes (Notes are considered duplicates
 * if they have the same note title).
 */
public class DuplicateNoteException extends RuntimeException {
    public DuplicateNoteException() {
        super(MESSAGE_DUPLICATE_NOTE);
    }
}
