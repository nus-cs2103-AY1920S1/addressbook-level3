package seedu.address.logic.commands;

import java.time.LocalDate;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;

/**
 * Abstract parent class for DeleteBySerialNumberCommand and DeleteByIndexCommand.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the \n"
            + "index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "or \n"
            + "SERIAL_NUMBER (must be valid serial number)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " sn/B00001";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    private static final int FINE_AMOUNT_ZERO = 0;

    public abstract CommandResult execute(Model model) throws CommandException;

    /** Helper method to assist in marking a book as returned before deletion */
    protected void markBookAsReturned(Model model, Book target) {
        Loan loanToBeReturned = target.getLoan().get();
        LocalDate returnDate = DateUtil.getTodayDate();
        Loan returnedLoan = loanToBeReturned.returnLoan(returnDate, FINE_AMOUNT_ZERO);

        Book returnedBook = target.returnBook();

        // update Book in model to have Loan removed
        model.setBook(target, returnedBook);

        // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
        model.servingBorrowerReturnLoan(loanToBeReturned, returnedLoan);

        // update Loan in LoanRecords with returnDate and remainingFineAmount
        model.updateLoan(loanToBeReturned, returnedLoan);
    }

}
