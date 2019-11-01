package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_DELIVERYMEN;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.exceptions.InvalidStatusChangeException;

/**
 * Changes the status of a deliveryman from AVAILABLE to UNAVAILABLE or UNAVAILABLE to AVAILABLE
 */
public class StatusSwitchCommand extends Command {
    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status of the deliveryman identified "
            + "by the index number used in the displayed deliverymen database.\n "
            + "If current status is AVAILABLE, status will be changed to UNAVAILABLE. "
            + "If current status is UNAVAILABLE, status will be changed to AVAILABLE.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_CHANGE_STATUS_SUCCESS = "Changed status of deliveryman %1$s ";

    private final Index targetIndex;

    public StatusSwitchCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);
        List<Deliveryman> lastShownList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        Deliveryman deliverymanToEdit = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.switchDeliverymanStatus(deliverymanToEdit);
            model.updateFilteredDeliverymenList(PREDICATE_SHOW_ALL_DELIVERYMEN);
            model.updateAvailableDeliverymenList(PREDICATE_SHOW_ALL_DELIVERYMEN);
        } catch (InvalidStatusChangeException isce) {
            throw new InvalidStatusChangeException();
        }

        return new CommandResult(String.format(MESSAGE_CHANGE_STATUS_SUCCESS, deliverymanToEdit),
                StatusSwitchCommand.class);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatusSwitchCommand // instanceof handles nulls
                && targetIndex.equals(((StatusSwitchCommand) other).targetIndex)); // state check
    }
}
