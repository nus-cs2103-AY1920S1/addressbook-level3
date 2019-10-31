package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;

/**
 * Unrenews a {@code Book}.
 * This class is meant to be the reverse of {@code RenewCommand} and is used only for the purpose of
 * a undo/redo Command.
 */
public class UnrenewCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Book: %1$s\nunrenewed for\nBorrower: %2$s\nDue date: %3$s";

    private Book bookToBeUnrenewed;
    private Book unrenewedBook;
    private Loan loanToBeUnrenewed;
    private Loan unrenewedLoan;

    /**
     * Creates an RenewCommand to renew the currently served Borrower's {@code Book}.
     *
     * @param bookToBeUnrenewed book which loan is renewed.
     * @param unrenewedBook resultant book after loan is unrenewed.
     * @param loanToBeUnrenewed loan that is to be unrenewed.
     * @param unrenewedLoan previous loan before renew.
     */
    public UnrenewCommand(Book bookToBeUnrenewed, Book unrenewedBook, Loan loanToBeUnrenewed, Loan unrenewedLoan) {
        requireAllNonNull(bookToBeUnrenewed, unrenewedBook, loanToBeUnrenewed, unrenewedLoan);

        this.bookToBeUnrenewed = bookToBeUnrenewed;
        this.unrenewedBook = unrenewedBook;
        this.loanToBeUnrenewed = loanToBeUnrenewed;
        this.unrenewedLoan = unrenewedLoan;
    }

    /**
     * Executes the RenewCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // update Book in model to have Loan due date return to previous due date
        model.setBook(bookToBeUnrenewed, unrenewedBook);

        // update Loan in Borrower's currentLoanList
        model.servingBorrowerUnrenewLoan(loanToBeUnrenewed, unrenewedLoan);

        // update Loan in LoanRecords to previous due date
        model.updateLoan(loanToBeUnrenewed, unrenewedLoan);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, unrenewedBook, model.getServingBorrower(),
                        unrenewedLoan.getDueDate()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UnrenewCommand)) {
            return false;
        }

        UnrenewCommand otherUnrenewCommand = (UnrenewCommand) o;
        return this.bookToBeUnrenewed.equals(otherUnrenewCommand.bookToBeUnrenewed)
                && this.unrenewedBook.equals(otherUnrenewCommand.unrenewedBook)
                && this.loanToBeUnrenewed.equals(otherUnrenewCommand.loanToBeUnrenewed)
                && this.unrenewedLoan.equals(otherUnrenewCommand.unrenewedLoan);
    }
}
