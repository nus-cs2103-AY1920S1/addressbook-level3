package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonEligibleForPolicyPredicate;
import seedu.address.model.policy.Policy;

import java.util.List;

/**
 * Finds and lists all persons in address book eligible for a given policy.
 */

public class EligiblePeopleCommand extends Command {

    public static final String COMMAND_WORD = "eligiblepeople";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all people eligible for a "
            + "specified policy and displays them as a list with index numbers.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1";

    private PersonEligibleForPolicyPredicate predicate;

    private final Index targetIndex;

    public EligiblePeopleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        predicate = new PersonEligibleForPolicyPredicate(lastShownList.get(targetIndex.getZeroBased()));
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EligiblePeopleCommand // instanceof handles nulls
                && predicate.equals(((EligiblePeopleCommand) other).predicate)); // state check
    }
}
