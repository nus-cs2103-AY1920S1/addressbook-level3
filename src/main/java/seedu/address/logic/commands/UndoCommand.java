package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;


/**
 * Adds a task to the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the most recent command";

    public static final String MESSAGE_SUCCESS = "Undo successful";
    public static final String MESSAGE_CANNOT_UNDO = "There is no command to undo";

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public UndoCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_CANNOT_UNDO);
        }

        model.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand); // instanceof handles nulls
    }
}
