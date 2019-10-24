package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.commons.core.Messages;
import thrift.model.Model;
import thrift.model.transaction.DescriptionOrRemarkContainsKeywordsPredicate;

/**
 * Finds and lists all transactions in THRIFT whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "find";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Finds all transactions whose descriptions contain any "
            + "of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Format: " + COMMAND_WORD + " KEYWORD [MORE_KEYWORDS]...\n"
            + "Possible usage of " + COMMAND_WORD + ": \n"
            + "To find all transactions that contain the words 'alice', 'bob' and 'charlie': "
            + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions whose descriptions contain any "
            + "of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DescriptionOrRemarkContainsKeywordsPredicate predicate;

    public FindCommand(DescriptionOrRemarkContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
