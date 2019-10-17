package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_NOT_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_LOANED_BY_BORROWER;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Returns a Book with the given Index.
 */
public class ReturnCommand extends Command {
    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loans a book to a borrower.\n"
            + "Command can only be used in Serve mode.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Book: %1$s returned by Borrower: %2$s";

    private final Index index;

    /**
     * Creates an ReturnCommand to return the currently served Borrower's {@code Book}.
     *
     * @param index Index of book to be returned.
     */
    public ReturnCommand(Index index) {
        requireNonNull(index);
        this.index = index;
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

        List<Book> lastShownList = model.getFilteredBookList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToBeReturned = lastShownList.get(index.getZeroBased()); // TODO change to second list
        Optional<Loan> returningLoanOptional = bookToBeReturned.getLoan();
        if (returningLoanOptional.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_BOOK_NOT_ON_LOAN, bookToBeReturned));
        }
        Loan returningLoan = returningLoanOptional.get();

        Borrower servingBorrower = model.getServingBorrower();
        // check if servingBorrower has this Book loaned
        if (!servingBorrower.hasCurrentLoan(returningLoan)) {
            throw new CommandException(String.format(MESSAGE_NOT_LOANED_BY_BORROWER,
                    servingBorrower, bookToBeReturned));
        }

        Book returnedBook = new Book(bookToBeReturned.getTitle(), bookToBeReturned.getSerialNumber(),
                bookToBeReturned.getAuthor(), null, bookToBeReturned.getGenres());

        // update Book in model to have Loan removed
        model.setBook(bookToBeReturned, returnedBook);
        // remove Loan from Borrower's currentLoanList and move to Borrower's returnedLoanList
        servingBorrower.returnLoan(returningLoan);

        return new CommandResult(String.format(MESSAGE_SUCCESS, returnedBook, servingBorrower));
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
        return this.index.equals(otherReturnCommand.index);
    }
}
