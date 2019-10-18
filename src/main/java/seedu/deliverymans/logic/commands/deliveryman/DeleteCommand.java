package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Deletes a deliveryman identified using it's displayed index from the deliverymen book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the deliveryman identified by the index number used in the displayed deliveryman list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DELIVERYMAN_SUCCESS = "Deleted Deliveryman: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Deliveryman> lastShownList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        Deliveryman deliverymanToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteDeliveryman(deliverymanToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERYMAN_SUCCESS, deliverymanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
