package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;

/**
 * Deletes a book identified using it's serial number from the catalog.
 */
public class DeleteBySerialNumberCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by its serial number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    private final SerialNumber targetSerialNumber;


    public DeleteBySerialNumberCommand(SerialNumber targetSerialNumber) {
        this.targetSerialNumber = targetSerialNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // delete by serial number
        if (!modelContainsBook(model, targetSerialNumber)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_BOOK);
        }
        Book bookToDelete = retrieveBookFromCatalog(model, targetSerialNumber);
        model.deleteBook(bookToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
    }

    /**
     * Returns true if a model contains a target book.
     *
     * @param model Model to check.
     * @param serialNumber Serial number of target book.
     * @return true if target book is found in model.
     */
    private boolean modelContainsBook(Model model, SerialNumber serialNumber) {
        return model.getCatalog()
                .getBookList()
                .stream()
                .filter(book -> book.getSerialNumber().equals(serialNumber))
                .count() == 1;
    }

    /**
     * Returns Book from Catalog under the model.
     *
     * @param model Model to retrieve Book from.
     * @param serialNumber Serial number of target book.
     * @return Book object, the target book.
     */
    private Book retrieveBookFromCatalog(Model model, SerialNumber serialNumber) {
        return (Book) model.getCatalog()
                .getBookList()
                .stream()
                .filter(book -> book.getSerialNumber().equals(serialNumber))
                .toArray()[0];
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteBySerialNumberCommand // instanceof handles nulls
                && targetSerialNumber.equals(((DeleteBySerialNumberCommand) other).targetSerialNumber)); // state
    }
}
