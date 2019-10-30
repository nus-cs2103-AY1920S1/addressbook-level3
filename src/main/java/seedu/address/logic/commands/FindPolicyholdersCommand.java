package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonPossessesPolicyPredicate;
import seedu.address.model.policy.Policy;

/**
 * Finds and lists all persons in address book who are in possession of a certain policy.
 * Keyword matching is case insensitive.
 */
public class FindPolicyholdersCommand extends Command {

    public static final String COMMAND_WORD = "findpolicyholders";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are currently in "
            + "possession of the specified policy and displays them as a list with index numbers.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    public FindPolicyholdersCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        Policy policy = lastShownList.get(index.getZeroBased());
        PersonPossessesPolicyPredicate predicate = new PersonPossessesPolicyPredicate(policy);

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
                || (other instanceof FindPolicyholdersCommand // instanceof handles nulls
                && index.equals(((FindPolicyholdersCommand) other).index)); // state check
    }
}
