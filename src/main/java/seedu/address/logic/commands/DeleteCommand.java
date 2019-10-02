package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;

/**
 * Deletes a book identified using it's displayed index from the catalog.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    private final Index targetIndex;
    private final SerialNumber targetSerialNumber;

    private boolean deleteByIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetSerialNumber = null;
        deleteByIndex = true;
    }

    public DeleteCommand(SerialNumber targetSerialNumber) {
        this.targetSerialNumber = targetSerialNumber;
        this.targetIndex = null;
        deleteByIndex = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (deleteByIndex) {
            // delete by index in list
            List<Book> lastShownList = model.getFilteredBookList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
            }

            Book bookToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteBook(bookToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
        } else {
            // delete by serial number
            if (!modelContainsBook(model, targetSerialNumber)) {
                throw new CommandException(Messages.MESSAGE_NO_SUCH_BOOK);
            }
            Book bookToDelete = retrieveBookFromCatalog(model, targetSerialNumber);
            model.deleteBook(bookToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
        }
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
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        if (targetIndex != null && ((DeleteCommand) other).targetIndex != null) {
            // both are delete by index
            return targetIndex.equals(((DeleteCommand) other).targetIndex); // state check
        }
        if (targetSerialNumber != null && ((DeleteCommand) other).targetSerialNumber != null) {
            // both are delete by serial number
            return targetSerialNumber.equals(((DeleteCommand) other).targetSerialNumber); // state check
        }
        // one is delete by index and one is delete by serial number
        return false;
    }
}
