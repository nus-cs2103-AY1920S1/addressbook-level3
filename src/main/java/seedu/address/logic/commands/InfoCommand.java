package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class InfoCommand extends Command {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_BOOK_INFO = "Displaying information for %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get information of a book.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index index;

    public InfoCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        Book target = lastShownList.get(index.getZeroBased());
        return CommandResult.commandResultInfo(
                String.format(MESSAGE_BOOK_INFO, getTitleFromBook(target)), target);
    }


    private String getTitleFromBook(Book target) {
        return target.getTitle().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InfoCommand // instanceof handles nulls
                && index.equals(((InfoCommand) other).index)); // state check
    }
}
