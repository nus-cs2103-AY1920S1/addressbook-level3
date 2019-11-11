package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.PolicyEligibleForPersonPredicate;

/**
 * Finds and lists all policies in address book a selected person is eligible for.
 */

public class EligiblePoliciesCommand extends Command {

    public static final String COMMAND_WORD = "eligiblepolicies";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all policies that a selected person "
            + "is eligible for and displays them as a list with index numbers.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1";

    private PolicyEligibleForPersonPredicate predicate;

    private final Index targetIndex;

    public EligiblePoliciesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        predicate = new PolicyEligibleForPersonPredicate(lastShownList.get(targetIndex.getZeroBased()));
        requireNonNull(model);
        model.updateFilteredPolicyList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_POLICIES_LISTED_OVERVIEW, model.getFilteredPolicyList().size()),
                false,
                false,
                true,
                false,
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
                || (other instanceof EligiblePoliciesCommand // instanceof handles nulls
                && targetIndex.equals(((EligiblePoliciesCommand) other).targetIndex)); // state check
    }
}
