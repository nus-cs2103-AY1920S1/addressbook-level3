package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.customer.NameContainsKeywordsPredicate;

/**
 * Finds and lists all customers in customer book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCustomerCommand extends Command {

    public static final String COMMAND_WORD = "find -c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all customers whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCustomerCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW,
                        model.getFilteredCustomerList().size()), PanelType.CUSTOMER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCustomerCommand // instanceof handles nulls
                && predicate.equals(((FindCustomerCommand) other).predicate)); // state check
    }
}
