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
public class ApproveClaimCommand extends Command {

    public static final String COMMAND_WORD = "approve";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Approves the claim identified by the index number used in the displayed claims list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Approved FinSec: %1$s";

    private final Index targetIndex;

    public ApproveClaimCommand(Index targetIndex) {
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
            throw new CommandException(Messages.MESSAGE_CANNOT_BE_APPROVED);
        }
        model.approveClaim(claimToApprove);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, claimToApprove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApproveClaimCommand // instanceof handles nulls
                && targetIndex.equals(((ApproveClaimCommand) other).targetIndex)); // state check
    }
}
