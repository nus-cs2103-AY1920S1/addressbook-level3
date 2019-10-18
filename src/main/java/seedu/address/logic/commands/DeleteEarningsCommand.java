package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.earnings.Earnings;

/**
 * Deletes an earning identified using it's displayed index from the address book.
 */
public class DeleteEarningsCommand extends Command {
    public static final String COMMAND_WORD = "delete_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the earnings identified by the index number used in the displayed earnings list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_DELETE_EARNINGS_SUCCESS = "Deleted Earnings: %1$s";

    private final Index targetIndex;

    public DeleteEarningsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Earnings> lastShownList = model.getFilteredEarningsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
        }

        Earnings earningsToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEarnings(earningsToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EARNINGS_SUCCESS, earningsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEarningsCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteEarningsCommand) other).targetIndex)); // state check
    }
}
