package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;

/**
 * Undeletes a {@code Book}.
 * This class is meant to be the reverse of {@code DeleteCommand} and is used only for the purpose of
 * a undo/redo Command.
 */
public class UndeleteCommand extends Command {

    public static final String MESSAGE_UNDELETE_BOOK_SUCCESS = "Undeleted Book: %1$s";

    private Book bookToBeLoaned;
    private Book loanedBook;
    private Loan loanToBeAdded;
    private Loan addedLoan;

    public UndeleteCommand(Book bookToBeLoaned, Book loanedBook, Loan loanToBeAdded, Loan addedLoan) {
        this.bookToBeLoaned = bookToBeLoaned;
        this.loanedBook = loanedBook;
        this.loanToBeAdded = loanToBeAdded;
        this.addedLoan = addedLoan;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addBook(bookToBeLoaned);

        // update Book in model to have Loan removed
        model.setBook(bookToBeLoaned, loanedBook);

        boolean wasServeMode = model.isServeMode();

        if (!wasServeMode) {
            model.setServingBorrower(loanToBeAdded.getBorrowerId());
        }
        // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
        model.servingBorrowerUnreturnLoan(loanToBeAdded, addedLoan);

        if (!wasServeMode) {
            model.exitsServeMode();
        }

        // update Loan in LoanRecords with returnDate and remainingFineAmount
        model.updateLoan(loanToBeAdded, addedLoan);

        return new CommandResult(String.format(MESSAGE_UNDELETE_BOOK_SUCCESS, bookToBeLoaned));
    }
}
