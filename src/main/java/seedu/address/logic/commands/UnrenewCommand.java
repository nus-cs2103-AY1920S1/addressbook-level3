package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.areAllSameSize;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Unrenews a {@code Book}.
 * This class is meant to be the reverse of {@code RenewCommand} and is used only for the purpose of
 * a undo/redo Command.
 */
public class UnrenewCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Book: %1$s\nunrenewed for\nBorrower: %2$s\nDue date: %3$s\n\n";

    private ArrayList<Book> bookToBeUnrenewedList;
    private ArrayList<Book> unrenewedBookList;
    private ArrayList<Loan> loanToBeUnrenewedList;
    private ArrayList<Loan> unrenewedLoanList;

    /**
     * Creates an UnrenewCommand to unrenew the currently served Borrower's {@code Book}(s).
     *
     * @param bookToBeUnrenewedList books which loans are renewed.
     * @param unrenewedBookList resultant books after loans are unrenewed.
     * @param loanToBeUnrenewedList loans that are to be unrenewed.
     * @param unrenewedLoanList previous loans before renew.
     */
    public UnrenewCommand(ArrayList<Book> bookToBeUnrenewedList, ArrayList<Book> unrenewedBookList,
                          ArrayList<Loan> loanToBeUnrenewedList, ArrayList<Loan> unrenewedLoanList) {
        requireAllNonNull(bookToBeUnrenewedList, unrenewedBookList, loanToBeUnrenewedList,
                unrenewedLoanList);
        assert areAllSameSize(bookToBeUnrenewedList, unrenewedBookList, loanToBeUnrenewedList,
                unrenewedBookList);

        this.bookToBeUnrenewedList = bookToBeUnrenewedList;
        this.unrenewedBookList = unrenewedBookList;
        this.loanToBeUnrenewedList = loanToBeUnrenewedList;
        this.unrenewedLoanList = unrenewedLoanList;
    }

    /**
     * Executes the UnrenewCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Borrower servingBorrower = model.getServingBorrower();
        String feedbackMessage = "";

        for (int i = 0; i < bookToBeUnrenewedList.size(); i++) {
            Book bookToBeUnrenewed = bookToBeUnrenewedList.get(i);
            Book unrenewedBook = unrenewedBookList.get(i);
            Loan loanToBeUnrenewed = loanToBeUnrenewedList.get(i);
            Loan unrenewedLoan = unrenewedLoanList.get(i);

            // update Book in model to have Loan due date return to previous due date
            model.setBook(bookToBeUnrenewed, unrenewedBook);

            // update Loan in Borrower's currentLoanList
            model.servingBorrowerUnrenewLoan(loanToBeUnrenewed, unrenewedLoan);

            // update Loan in LoanRecords to previous due date
            model.updateLoan(loanToBeUnrenewed, unrenewedLoan);

            LoanSlipUtil.unmountSpecificLoan(loanToBeUnrenewed, bookToBeUnrenewed);

            feedbackMessage += String.format(MESSAGE_SUCCESS, unrenewedBook, servingBorrower,
                    DateUtil.formatDate(unrenewedLoan.getDueDate()));
        }

        return new CommandResult(feedbackMessage.trim());
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
        return this.bookToBeUnrenewedList.equals(otherUnrenewCommand.bookToBeUnrenewedList)
                && this.unrenewedBookList.equals(otherUnrenewCommand.unrenewedBookList)
                && this.loanToBeUnrenewedList.equals(otherUnrenewCommand.loanToBeUnrenewedList)
                && this.unrenewedLoanList.equals(otherUnrenewCommand.unrenewedLoanList);
    }
}
