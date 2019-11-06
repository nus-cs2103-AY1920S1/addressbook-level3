package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.areAllSameSize;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Unreturns a {@code Book}.
 * This class is meant to be the reverse of {@code ReturnCommand} and is used only for the purpose of
 * a undo/redo Command.
 */
public class UnreturnCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Book: %1$s\nunreturned from\nBorrower: %2$s\n\n";

    private final ArrayList<Book> bookToBeUnreturnedList;
    private final ArrayList<Book> unreturnedBookList;
    private final ArrayList<Loan> loanToBeUnreturnedList;
    private final ArrayList<Loan> unreturnedLoanList;

    /**
     * Creates an UnreturnCommand to unreturn the currently served Borrower's {@code Book}(s).
     *
     * @param bookToBeUnreturnedList books which loans are returned.
     * @param unreturnedBookList resultant books after loans are unreturned.
     * @param loanToBeUnreturnedList loans that are to be unreturned.
     * @param unreturnedLoanList previous loans before return.
     */
    public UnreturnCommand(ArrayList<Book> bookToBeUnreturnedList, ArrayList<Book> unreturnedBookList,
                           ArrayList<Loan> loanToBeUnreturnedList, ArrayList<Loan> unreturnedLoanList) {
        requireAllNonNull(bookToBeUnreturnedList, unreturnedBookList, loanToBeUnreturnedList,
                unreturnedLoanList);
        assert areAllSameSize(bookToBeUnreturnedList, unreturnedBookList, loanToBeUnreturnedList,
                unreturnedLoanList);

        this.bookToBeUnreturnedList = bookToBeUnreturnedList;
        this.unreturnedBookList = unreturnedBookList;
        this.loanToBeUnreturnedList = loanToBeUnreturnedList;
        this.unreturnedLoanList = unreturnedLoanList;
    }

    /**
     * Executes the UneturnCommand and returns the result message.
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

        for (int i = 0; i < bookToBeUnreturnedList.size(); i++) {
            Book bookToBeUnreturned = bookToBeUnreturnedList.get(i);
            Book unreturnedBook = unreturnedBookList.get(i);
            Loan loanToBeUnreturned = loanToBeUnreturnedList.get(i);
            Loan unreturnedLoan = unreturnedLoanList.get(i);

            // update Book in model to have Loan removed
            model.setBook(bookToBeUnreturned, unreturnedBook);

            // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
            model.servingBorrowerUnreturnLoan(loanToBeUnreturned, unreturnedLoan);

            // update Loan in LoanRecords with returnDate and remainingFineAmount
            model.updateLoan(loanToBeUnreturned, unreturnedLoan);

            try {
                if (LoanSlipUtil.loanIsInSession(unreturnedLoan)) {
                    LoanSlipUtil.mountLoan(unreturnedLoan, unreturnedBook, model.getServingBorrower());
                }
            } catch (LoanSlipException e) {
                e.printStackTrace(); // Unable to generate loan slip, does not affect loan functionality
            }

            feedbackMessage += String.format(MESSAGE_SUCCESS, unreturnedBook, servingBorrower);
        }

        return new CommandResult(feedbackMessage.trim());
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
        return this.bookToBeUnreturnedList.equals(otherUnreturnCommand.bookToBeUnreturnedList)
                && this.unreturnedBookList.equals(otherUnreturnCommand.unreturnedBookList)
                && this.loanToBeUnreturnedList.equals(otherUnreturnCommand.loanToBeUnreturnedList)
                && this.unreturnedLoanList.equals(otherUnreturnCommand.unreturnedLoanList);
    }
}
