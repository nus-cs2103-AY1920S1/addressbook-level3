package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;


/**
 * Adds a task to the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the most recent redo command";

    public static final String MESSAGE_SUCCESS = "Redo successful";
    public static final String MESSAGE_CANNOT_REDO = "There is no command to redo";

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public RedoCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_CANNOT_REDO);
        }

        model.redo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand); // instanceof handles nulls
    }
}
