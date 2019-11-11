package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Deletes a book identified using it's displayed index from the catalog.
 */
public class DeleteByIndexCommand extends DeleteCommand {

    private final Index targetIndex;

    /**
     * Constructor for DeleteByIndexCommand.
     * @param targetIndex index of book to be deleted.
     */
    public DeleteByIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the DeleteByIndexCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // delete by index in list
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        Book bookToDelete = lastShownList.get(targetIndex.getZeroBased());

        returnBook(model, bookToDelete); // return before deleting

        model.deleteBook(bookToDelete);

        redoCommand = new DeleteBySerialNumberCommand(bookToDelete.getSerialNumber());
        commandResult = new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteByIndexCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteByIndexCommand) other).targetIndex)); //state
    }
}
