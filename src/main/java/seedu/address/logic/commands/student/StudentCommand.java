package seedu.address.logic.commands.student;

import seedu.address.logic.commands.Command;

/**
 * Represents a student command.
 */
public abstract class StudentCommand extends Command {

    public static final String COMMAND_WORD = "student";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the student storage.";
}
