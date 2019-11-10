package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;

/**
 * Unloans a {@code Book}.
 * This class is meant to be the reverse of {@code LoanCommand} and is used only for the purpose of
 * a undo/redo Command.
 */
public class UnloanCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Book: %1$s\nunloaned from\nBorrower: %2$s";

    private final Book bookToBeUnloaned;
    private final Book unloanedBook;
    private final Loan loanToBeRemoved;

    /**
     * Creates an UnloanCommand to unloan the specified {@code Book} to the Borrower currently served.
     * This is only used as an undo command for {@code LoanCommand}.
     *
     * @param bookToBeUnloaned {@code Book} to be unloaned.
     * @param unloanedBook resultant {@code Book} when unloaned.
     * @param loanToBeRemoved loan to be removed from {@code LoanRecords} and borrower's loans.
     */
    public UnloanCommand(Book bookToBeUnloaned, Book unloanedBook, Loan loanToBeRemoved) {
        requireAllNonNull(bookToBeUnloaned, unloanedBook, loanToBeRemoved);

        this.bookToBeUnloaned = bookToBeUnloaned;
        this.unloanedBook = unloanedBook;
        this.loanToBeRemoved = loanToBeRemoved;
    }

    /**
     * Executes the UnloanCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert(model.isServeMode());
        assert(model.hasBook(bookToBeUnloaned));
        assert(bookToBeUnloaned.isCurrentlyLoanedOut());

        Book updatedBookToBeUnloaned = bookToBeUnloaned.deleteFromLoanHistory(loanToBeRemoved);
        // replace the previous Book object with a new Book object that does not have the loan
        model.setBook(updatedBookToBeUnloaned, unloanedBook);
        model.removeLoan(loanToBeRemoved); // remove Loan object from LoanRecords in model
        model.servingBorrowerRemoveLoan(loanToBeRemoved); // remove Loan object from Borrower's currentLoanList

        LoanSlipUtil.unmountSpecificLoan(loanToBeRemoved, updatedBookToBeUnloaned);
        LoanSlipUtil.removeLoanFromSession(loanToBeRemoved);


        return new CommandResult(String.format(MESSAGE_SUCCESS, unloanedBook, model.getServingBorrower()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UnloanCommand)) {
            return false;
        }

        UnloanCommand otherUnloanCommand = (UnloanCommand) o;
        return this.bookToBeUnloaned.equals(otherUnloanCommand.bookToBeUnloaned)
                && this.unloanedBook.equals(otherUnloanCommand.unloanedBook)
                && this.loanToBeRemoved.equals(otherUnloanCommand.loanToBeRemoved);
    }
}
