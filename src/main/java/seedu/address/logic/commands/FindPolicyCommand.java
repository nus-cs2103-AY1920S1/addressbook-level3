package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.policy.PolicyNameContainsKeywordsPredicate;

/**
 * Finds and lists all policies in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPolicyCommand extends Command {

    public static final String COMMAND_WORD = "findpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all policies whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " life accident";

    private final PolicyNameContainsKeywordsPredicate predicate;

    public FindPolicyCommand(PolicyNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPolicyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_POLICIES_LISTED_OVERVIEW, model.getFilteredPolicyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPolicyCommand // instanceof handles nulls
                && predicate.equals(((FindPolicyCommand) other).predicate)); // state check
    }
}
