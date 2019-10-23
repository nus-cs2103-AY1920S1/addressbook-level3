package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandHistory;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the action of an {@code UndoableCommand} command.
 */
public class UndoCommand implements Command {

    public static final String COMMAND_WORD = "undo";
    private final CommandHistory history;

    public UndoCommand(CommandHistory history) {
        requireNonNull(history);
        this.history = history;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return history.performUndo(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof UndoCommand; // instanceof handles nulls
    }
}
