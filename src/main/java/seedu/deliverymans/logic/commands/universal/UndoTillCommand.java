package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Undoes or redoes multiple actions till an index.
 */
public class UndoTillCommand extends Command {
    public static final String COMMAND_WORD = "-undo_till";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes or redoes actions till the index obtained from "
            + UndoListCommand.COMMAND_WORD + ".\n"
            + "Parameters: INDEX (index numbers are positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public UndoTillCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.undoSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTION_INDEX);
        }
        return new CommandResult(model.undoTill(targetIndex.getZeroBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoTillCommand // instanceof handles nulls
                && targetIndex.equals(((UndoTillCommand) other).targetIndex)); // state check
    }
}
