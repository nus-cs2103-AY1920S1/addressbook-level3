package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;

/**
 * Approves a claim identified using its displayed index from the claims list.
 */
public class RejectClaimCommand extends Command {

    public static final String COMMAND_WORD = "reject";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rejects the claim identified by the index number used in the displayed claims list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Rejected FinSec: %1$s";

    private final Index targetIndex;

    public RejectClaimCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Claim> lastShownList = model.getFilteredClaimList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Claim claimToApprove = lastShownList.get(targetIndex.getZeroBased());
        if (!claimToApprove.canChangeStatus()) {
            throw new CommandException(Messages.MESSAGE_CANNOT_BE_REJECTED);
        }
        model.rejectClaim(claimToApprove);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, claimToApprove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RejectClaimCommand // instanceof handles nulls
                && targetIndex.equals(((RejectClaimCommand) other).targetIndex)); // state check
    }
}
