package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_NOT_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_LOANED_BY_BORROWER;

import java.time.LocalDate;
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
 * Returns a Book with the given Index.
 */
public class ReturnCommand extends Command implements ReversibleCommand {
    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a book borrowed by a borrower.\n"
            + "Command can only be used in Serve mode.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Book: %1$s\nreturned by\nBorrower: %2$s\nFine incurred: %3$s";

    private final Index index;
    private final boolean isUndoRedo;
    private Command undoCommand;
    private Command redoCommand;

    /**
     * Creates an ReturnCommand to return the currently served Borrower's {@code Book}.
     *
     * @param index Index of book to be returned.
     */
    public ReturnCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.isUndoRedo = false;
    }

    /**
     * Creates an ReturnCommand to return the currently served Borrower's {@code Book}.
     *
     * @param index Index of book to be returned.
     * @param isUndoRedo used to check whether the ReturnCommand is an undo/redo command.
     */
    public ReturnCommand(Index index, boolean isUndoRedo) {
        requireNonNull(index);
        this.index = index;
        this.isUndoRedo = isUndoRedo;
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

        List<Book> lastShownBorrowerBooksList = model.getBorrowerBooks();
        if (index.getZeroBased() >= lastShownBorrowerBooksList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToBeReturned = lastShownBorrowerBooksList.get(index.getZeroBased());
        if (!bookToBeReturned.isCurrentlyLoanedOut()) {
            throw new CommandException(String.format(MESSAGE_BOOK_NOT_ON_LOAN, bookToBeReturned));
        }

        Loan loanToBeReturned = bookToBeReturned.getLoan().get();
        Borrower servingBorrower = model.getServingBorrower();
        // check if servingBorrower has this Book loaned
        if (!servingBorrower.hasCurrentLoan(loanToBeReturned)) {
            throw new CommandException(String.format(MESSAGE_NOT_LOANED_BY_BORROWER,
                    servingBorrower, bookToBeReturned));
        }

        LocalDate returnDate = DateUtil.getTodayDate();
        int fineAmount = DateUtil.getNumOfDaysOverdue(loanToBeReturned.getDueDate(), returnDate)
                * model.getUserSettings().getFineIncrement();
        Loan returnedLoan = loanToBeReturned.returnLoan(returnDate, fineAmount);

        Book returnedBook = bookToBeReturned.returnBook();

        // update Book in model to have Loan removed
        model.setBook(bookToBeReturned, returnedBook);

        // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
        model.servingBorrowerReturnLoan(loanToBeReturned, returnedLoan);

        // update Loan in LoanRecords with returnDate and remainingFineAmount
        model.updateLoan(loanToBeReturned, returnedLoan);

        // unmount this book in LoanSlipUtil if it is mounted
        LoanSlipUtil.unmountSpecificLoan(loanToBeReturned, bookToBeReturned);

        undoCommand = new UnreturnCommand(returnedBook, bookToBeReturned, returnedLoan, loanToBeReturned);
        redoCommand = new ReturnCommand(index, true);

        return new CommandResult(String.format(MESSAGE_SUCCESS, returnedBook, servingBorrower,
                FineUtil.centsToDollarString(fineAmount)));
    }

    @Override
    public Command getUndoCommand() {
        return undoCommand;
    }

    @Override
    public Command getRedoCommand() {
        return redoCommand;
    }

    @Override
    public boolean isUndoRedoCommand() {
        return isUndoRedo;
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
        return this.index.equals(otherReturnCommand.index)
                && this.isUndoRedo == otherReturnCommand.isUndoRedo;
    }
}
