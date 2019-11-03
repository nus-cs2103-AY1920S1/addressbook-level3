package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;

/**
 * Deletes a book identified using it's displayed index from the catalog.
 */
public class DeleteByIndexCommand extends DeleteCommand {

    private final Index targetIndex;

    public DeleteByIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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
            Loan loanToBeReturned = bookToDelete.getLoan().get();
            LocalDate returnDate = DateUtil.getTodayDate();
            Loan returnedLoan = loanToBeReturned.returnLoan(returnDate, FINE_AMOUNT_ZERO);

            Book returnedBook = bookToDelete.returnBook();

            // mark book as returned
            super.markBookAsReturned(model, bookToDelete, returnedBook, loanToBeReturned, returnedLoan);
            undoCommand = new UndeleteCommand(returnedBook, bookToDelete, returnedLoan, loanToBeReturned);
        } else {
            undoCommand = new AddCommand(bookToDelete);
        }

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
