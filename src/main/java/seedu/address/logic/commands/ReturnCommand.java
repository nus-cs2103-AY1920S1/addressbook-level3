package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_RETURNABLE_BOOKS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.FineUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Returns all valid Books or the Book with the given Index.
 */
public class ReturnCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Return book(s) borrowed by a borrower.\n"
            + "Command can only be used in Serve mode.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "or\n"
            + "-all\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " -all";

    public static final String MESSAGE_SUCCESS =
            "Book: %1$s\nreturned by\nBorrower: %2$s\nFine incurred: %3$s\n\n";

    private final Index index;
    private final boolean isReturnAll;

    /**
     * Creates a ReturnCommand to return the currently served Borrower's {@code Book}.
     *
     * @param index Index of book to be returned.
     */
    public ReturnCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.isReturnAll = false;
    }

    /**
     * Creates a ReturnCommand to return all of the currently served Borrower's {@code Book}s.
     */
    public ReturnCommand() {
        this.isReturnAll = true;
        this.index = null;
    }

    /**
     * Executes the ReturnCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isServeMode()) {
            throw new CommandException(MESSAGE_NOT_IN_SERVE_MODE);
        }

        ArrayList<Book> returningBooks = getReturningBooks(model);
        Borrower servingBorrower = model.getServingBorrower();

        StringBuilder feedbackMessage = new StringBuilder();
        ArrayList<Book> returnedBookList = new ArrayList<>();
        ArrayList<Book> bookToBeReturnedList = new ArrayList<>();
        ArrayList<Loan> returnedLoanList = new ArrayList<>();
        ArrayList<Loan> loanToBeReturnedList = new ArrayList<>();
        LocalDate returnDate = DateUtil.getTodayDate();

        for (Book bookToBeReturned : returningBooks) {
            Loan loanToBeReturned = bookToBeReturned.getLoan().get();

            int dailyFineIncrement = model.getUserSettings().getFineIncrement();
            int fineAmount = DateUtil.getNumOfDaysOverdue(loanToBeReturned.getDueDate(), returnDate)
                    * dailyFineIncrement;
            Loan returnedLoan = loanToBeReturned.returnLoan(returnDate, fineAmount);
            Book returnedBook = bookToBeReturned
                    .deleteFromLoanHistory(loanToBeReturned)
                    .addToLoanHistory(returnedLoan)
                    .returnBook();

            // update Book in model to have Loan removed
            model.setBook(bookToBeReturned, returnedBook);

            // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
            model.servingBorrowerReturnLoan(loanToBeReturned, returnedLoan);

            // update Loan in LoanRecords with returnDate and remainingFineAmount
            model.updateLoan(loanToBeReturned, returnedLoan);

            // unmount this book in LoanSlipUtil if it is mounted
            LoanSlipUtil.unmountSpecificLoan(loanToBeReturned, bookToBeReturned);

            feedbackMessage.append(String.format(MESSAGE_SUCCESS, returnedBook, servingBorrower,
                    FineUtil.centsToDollarString(fineAmount)));
            returnedBookList.add(returnedBook);
            bookToBeReturnedList.add(bookToBeReturned);
            returnedLoanList.add(returnedLoan);
            loanToBeReturnedList.add(loanToBeReturned);
        }

        undoCommand = new UnreturnCommand(returnedBookList, bookToBeReturnedList, returnedLoanList,
                loanToBeReturnedList);
        redoCommand = this;
        commandResult = new CommandResult(feedbackMessage.toString().trim());

        return commandResult;
    }

    /**
     * Retrieves the books that we are returning in an ArrayList.
     *
     * @param model {@code Model} which the command should operate on.
     * @return ArrayList of returningBooks.
     * @throws CommandException If an error occurs during command execution.
     */
    private ArrayList<Book> getReturningBooks(Model model) throws CommandException {
        List<Book> lastShownBorrowerBooksList = model.getBorrowerBooks();

        if (isReturnAll) { // return all valid books
            if (lastShownBorrowerBooksList.isEmpty()) {
                throw new CommandException(MESSAGE_NO_RETURNABLE_BOOKS);
            }

            return new ArrayList<>(lastShownBorrowerBooksList);
        } else { // return book corresponding to index
            if (index.getZeroBased() >= lastShownBorrowerBooksList.size()) {
                throw new CommandException(MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
            }

            Book bookToBeReturned = lastShownBorrowerBooksList.get(index.getZeroBased());
            ArrayList<Book> returningBooks = new ArrayList<>();
            returningBooks.add(bookToBeReturned);
            return returningBooks;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ReturnCommand)) {
            return false;
        }

        ReturnCommand otherReturnCommand = (ReturnCommand) o;
        return ((this.index == null) || (this.index.equals(otherReturnCommand.index)))
                && this.isReturnAll == otherReturnCommand.isReturnAll;
    }
}
