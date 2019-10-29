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
public class DeleteByIndexCommand extends DeleteCommand implements ReversibleCommand {


    private final Index targetIndex;
    private final boolean isUndoRedo;
    private Command undoCommand;
    private Command redoCommand;

    public DeleteByIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.isUndoRedo = false;
    }

    public DeleteByIndexCommand(Index targetIndex, boolean isUndoRedo) {
        this.targetIndex = targetIndex;
        this.isUndoRedo = isUndoRedo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // delete by index in list
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        Book bookToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (bookToDelete.isCurrentlyLoanedOut()) {
            // mark book as returned
            super.markBookAsReturned(model, bookToDelete);
        }

        undoCommand = new AddCommand(bookToDelete, true);
        redoCommand = new DeleteBySerialNumberCommand(bookToDelete.getSerialNumber(), true);
        model.deleteBook(bookToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
    }

    @Override
    public Command getUndoCommand() {
        return undoCommand;
    }

    @Override
    public Command getRedoCommand() {
        return redoCommand;
    }

    @Override
    public boolean isUndoRedoCommand() {
        return isUndoRedo;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteByIndexCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteByIndexCommand) other).targetIndex)) //state
                && isUndoRedo == ((DeleteByIndexCommand) other).isUndoRedo;
    }
}
