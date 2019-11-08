package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EARNINGS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Earnings;

/**
 * Changes the status of the earnings in the address book.
 */
public class ClaimEarningsCommand extends Command {

    public static final String COMMAND_WORD = "claim_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows that the earnings has been claimed by the user. \n"

            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CLAIM + "CLAIM_STATUS \n"

            + "Example: " + COMMAND_WORD + " 3 "
            + PREFIX_CLAIM + "approved";

    public static final String MESSAGE_SUCCESS = "Earnings claimed: %1$s";

    private final Index index;
    private final Claim claim;

    public ClaimEarningsCommand(Index index, Claim claim) {
        requireAllNonNull(index, claim);

        this.index = index;
        this.claim = claim;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Earnings> lastShownList = model.getFilteredEarningsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
        }

        Earnings earningsToEdit = lastShownList.get(index.getZeroBased());
        earningsToEdit.setClaim(claim);

        model.updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, earningsToEdit),
                false, false, true, false, false,
                false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClaimEarningsCommand)) {
            return false;
        }

        // state check
        ClaimEarningsCommand e = (ClaimEarningsCommand) other;
        return index.equals(e.index)
                && claim.equals(e.claim);
    }

}
