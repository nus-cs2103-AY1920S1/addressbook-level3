package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.Messages;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.LogEntryContainsCategoriesPredicate;
import seedu.address.model.finance.logentry.LogEntryContainsKeywordsPredicate;
import seedu.address.model.finance.logentry.LogEntryMatchesLogEntryTypesPredicate;
import seedu.address.model.finance.logentry.LogEntryMatchesPredicate;

/**
 * Finds and lists all log entries in finance log with fields containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all log entries whose description contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers. "
            + "Filtering by entry type (Spend, Income, Borrow, Lend) is also possible. "
            + "At least one field must be specified.\n"
            + "Parameters: " + PREFIX_TYPE + " spend/income/borrow/lend "
            + PREFIX_KEYWORD + "KEYWORD [MORE_KEYWORDS]... "
            + PREFIX_CATEGORY + "CATEGORY_NAME [MORE_CATEGORY_NAMES]\n"
            + "Example: " + COMMAND_WORD + "<filter> borrow";

    private final LogEntryMatchesPredicate predicate;

    public FindCommand(LogEntryMatchesLogEntryTypesPredicate logEntryTypesPredicate,
                       LogEntryContainsKeywordsPredicate keywordsPredicate,
                       LogEntryContainsCategoriesPredicate categoriesPredicate) {
        this.predicate = new LogEntryMatchesPredicate(
                logEntryTypesPredicate,
                keywordsPredicate, categoriesPredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLogEntryList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_LOG_ENTRIES_LISTED_OVERVIEW, model.getFilteredLogEntryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
