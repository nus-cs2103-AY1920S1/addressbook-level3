package seedu.address.logic.commands;

import seedu.address.model.person.Person;

/**
 * Serves the next patient in queue.
 */
public class NextCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been allocated";
    public static final String MESSAGE_UNDO_NEXT_SUCCESS = "Allocation has been undone";
    public static final String MESSAGE_UNDO_NEXT_ERROR = "Changes cannot be undone!";

    private Person personToDelete;

    public NextCommand() {
        this.personToDelete = null;
    }

}
