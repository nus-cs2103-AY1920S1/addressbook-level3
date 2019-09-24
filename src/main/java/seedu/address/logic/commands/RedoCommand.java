package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandHistory;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes the action of an {@code UndoableCommand} command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_NO_REDO_HISTORY_ERROR = "Nothing to redo!";
    private final CommandHistory history;

    public RedoCommand(CommandHistory history) {
        requireNonNull(history);
        this.history = history;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!history.canRedo()) {
            throw new CommandException(MESSAGE_NO_REDO_HISTORY_ERROR);
        }

        return history.performRedo(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof RedoCommand; // instanceof handles nulls
    }
}
