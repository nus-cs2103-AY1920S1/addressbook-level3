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

    /**
     * Constructor for InfoCommand.
     *
     * @param index index of book to display information on.
     */
    public InfoCommand(Index index) {
        this.index = index;
    }

    /**
     * Executes the loan command to retrieve and display information about the target book and
     * returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
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

    /**
     * Returns a string representation of the itle of a book.
     *
     * @param target Book title to be retrieved from.
     * @return String representation of the title of a book.
     */
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
