package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.core.Command;
import seedu.address.logic.commands.core.CommandHistory;
import seedu.address.logic.commands.core.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the action of an {@code UndoableCommand} command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_NO_UNDO_HISTORY_ERROR = "Nothing to undo!";
    private final CommandHistory history;

    public UndoCommand(CommandHistory history) {
        requireNonNull(history);
        this.history = history;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!history.canUndo()) {
            throw new CommandException(MESSAGE_NO_UNDO_HISTORY_ERROR);
        }

        return history.performUndo(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof UndoCommand; // instanceof handles nulls
    }
}
