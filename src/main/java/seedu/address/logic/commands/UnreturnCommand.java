package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;

/**
 * Unreturns a {@code Book}.
 * This class is meant to be the reverse of {@code ReturnCommand} and is used only for the purpose of
 * a undo/redo Command.
 */
public class UnreturnCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Book: %1$s\nunreturned from\nBorrower: %2$s";

    private final Book bookToBeUnreturned;
    private final Book unreturnedBook;
    private final Loan loanToBeUnreturned;
    private final Loan unreturnedLoan;

    /**
     * Creates an UnreturnCommand to unreturn the currently served Borrower's {@code Book}.
     *
     * @param
     */
    public UnreturnCommand(Book bookToBeUnreturned, Book unreturnedBook, Loan loanToBeUnreturned, Loan unreturnedLoan) {
        requireAllNonNull(bookToBeUnreturned, unreturnedBook, loanToBeUnreturned, unreturnedLoan);

        this.bookToBeUnreturned = bookToBeUnreturned;
        this.unreturnedBook = unreturnedBook;
        this.loanToBeUnreturned = loanToBeUnreturned;
        this.unreturnedLoan = unreturnedLoan;
    }

    /**
     * Executes the ReturnCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // update Book in model to have Loan removed
        model.setBook(bookToBeUnreturned, unreturnedBook);

        // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
        model.servingBorrowerUnreturnLoan(loanToBeUnreturned, unreturnedLoan);

        // update Loan in LoanRecords with returnDate and remainingFineAmount
        model.updateLoan(loanToBeUnreturned, unreturnedLoan);

        return new CommandResult(String.format(MESSAGE_SUCCESS, unreturnedBook, model.getServingBorrower()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UnreturnCommand)) {
            return false;
        }

        UnreturnCommand otherUnreturnCommand = (UnreturnCommand) o;
        return this.bookToBeUnreturned.equals(otherUnreturnCommand.bookToBeUnreturned)
                && this.unreturnedBook.equals(otherUnreturnCommand.unreturnedBook)
                && this.loanToBeUnreturned.equals(otherUnreturnCommand.loanToBeUnreturned)
                && this.unreturnedLoan.equals(otherUnreturnCommand.unreturnedLoan);
    }
}
