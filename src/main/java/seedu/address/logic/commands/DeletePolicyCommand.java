package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Deletes a policy identified using it's displayed index from the address book.
 */
public class DeletePolicyCommand extends Command {

    public static final String COMMAND_WORD = "deletepolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the policy identified by the index number used in the displayed policy list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_POLICY_SUCCESS = "Deleted Policy: %1$s";

    private final Index targetIndex;

    public DeletePolicyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        Policy policyToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePolicy(policyToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_POLICY_SUCCESS, policyToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePolicyCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePolicyCommand) other).targetIndex)); // state check
    }
}
