package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Abstract parent class for DeleteBySerialNumberCommand and DeleteByIndexCommand.
 */
public abstract class DeleteCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the "
            + "index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "or \n"
            + "SERIAL_NUMBER (must be valid serial number)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " sn/B00001";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    protected static final int FINE_AMOUNT_ZERO = 0;

    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Marks a book as returned.
     */
    private void markBookAsReturned(Model model, Book bookToBeReturned, Book returnedBook,
                                      Loan loanToBeReturned, Loan returnedLoan) {
        requireAllNonNull(model, bookToBeReturned, returnedBook, loanToBeReturned, returnedBook);

        // update Book in model to have Loan removed
        model.setBook(bookToBeReturned, returnedBook);

        // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
        model.servingBorrowerReturnLoan(loanToBeReturned, returnedLoan);

        // update Loan in LoanRecords with returnDate and remainingFineAmount
        model.updateLoan(loanToBeReturned, returnedLoan);
    }

    /**
     * Marks a book as returned with the given loans.
     */
    protected void returnBook(Model model, Book bookToDelete) {
        boolean wasInServeMode = model.isServeMode();
        if (bookToDelete.isCurrentlyLoanedOut()) {
            Borrower borrower = model.getBorrowerFromId(bookToDelete.getLoan().get().getBorrowerId());

            if (!wasInServeMode) {
                model.setServingBorrower(borrower);
            }

            Loan loanToBeReturned = bookToDelete.getLoan().get();
            LocalDate returnDate = DateUtil.getTodayDate();
            Loan returnedLoan = loanToBeReturned.returnLoan(returnDate, FINE_AMOUNT_ZERO);

            Book returnedBook = bookToDelete.returnBook();

            // mark book as returned
            markBookAsReturned(model, bookToDelete, returnedBook, loanToBeReturned, returnedLoan);
            LoanSlipUtil.unmountSpecificLoan(loanToBeReturned, bookToDelete);

            if (!wasInServeMode) {
                model.exitsServeMode();
            }
            undoCommand = new UndeleteCommand(returnedBook, bookToDelete, returnedLoan, loanToBeReturned);
        } else {
            undoCommand = new AddCommand(bookToDelete);
        }
    }


}
