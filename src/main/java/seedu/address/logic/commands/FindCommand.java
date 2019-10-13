package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.book.BookPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all books by filtering the ones matching "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: { [t/TITLE] [a/AUTHOR] [g/GENRE]… [sn/BOOK_SN] "
            + "[-overdue]/[-loaned]/[-available] } [NUMBER]\n"
            + "Example: " + COMMAND_WORD + " g/mystery g/children -available";

    private final BookPredicate predicate;

    public FindCommand(BookPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBookList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, model.getFilteredBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
