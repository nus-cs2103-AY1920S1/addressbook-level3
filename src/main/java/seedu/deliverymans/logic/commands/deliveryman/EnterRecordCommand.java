package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

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
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.DeliveryRecord;

/**
 * Views the delivery record of a deliveryman.
 */
public class EnterRecordCommand extends Command {
    public static final String COMMAND_WORD = "enter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the delivery record of the deliveryman identified "
            + "by the index number used in the displayed deliverymen database.\n "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ENTER_RECORD_SUCCESS = "Entered the delivery record of deliveryman: %1$s ";

    private final Index targetIndex;

    public EnterRecordCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);
        List<Deliveryman> lastShownList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        Deliveryman deliverymanToView = lastShownList.get(targetIndex.getZeroBased());
        DeliveryRecord targetRecord = model.getDeliverymanRecord(deliverymanToView);
        model.setToShowDeliverymanRecord(targetRecord);

        return new CommandResult(String.format(MESSAGE_ENTER_RECORD_SUCCESS, deliverymanToView),
                Context.DELIVERYMANRECORD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EnterRecordCommand // instanceof handles nulls
                && targetIndex.equals(((EnterRecordCommand) other).targetIndex)); // state check
    }
}
