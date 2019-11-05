package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;

import java.time.LocalDate;

import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanIdGenerator;

/**
 * Loans a Book with the given Serial Number to a Borrower.
 */
public class LoanCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "loan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loans a book to a borrower.\n"
            + "Command can only be used in Serve mode.\n"
            + "Parameters: " + PREFIX_SERIAL_NUMBER + "SERIAL_NUMBER\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SERIAL_NUMBER + "B00001";

    public static final String MESSAGE_SUCCESS = "Book: %1$s\nloaned to\nBorrower: %2$s";

    private final SerialNumber toLoan;

    /**
     * Creates an LoanCommand to loan the specified {@code Book} to the Borrower currently served.
     *
     * @param bookSn Serial number of Book to be loaned.
     */
    public LoanCommand(SerialNumber bookSn) {
        requireNonNull(bookSn);
        this.toLoan = bookSn;
    }

    /**
     * Executes the LoanCommand and returns the result message.
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

        if (!model.hasBook(this.toLoan)) {
            throw new CommandException(MESSAGE_NO_SUCH_BOOK);
        }

        Book bookToBeLoaned = model.getBook(toLoan);
        if (bookToBeLoaned.isCurrentlyLoanedOut()) {
            throw new CommandException(String.format(MESSAGE_BOOK_ON_LOAN, bookToBeLoaned));
        }

        Borrower servingBorrower = model.getServingBorrower();
        LocalDate dueDate = DateUtil.getTodayPlusDays(model.getUserSettings().getLoanPeriod());
        Loan loan = new Loan(LoanIdGenerator.generateLoanId(), toLoan, servingBorrower.getBorrowerId(),
                DateUtil.getTodayDate(), dueDate);
        Book loanedOutBook = bookToBeLoaned.loanOut(loan);
        Book updatedLoanedOutBook = loanedOutBook.addToLoanHistory(loan);

        // replace the previous Book object with a new Book object that has a Loan
        model.setBook(bookToBeLoaned, updatedLoanedOutBook);
        model.addLoan(loan); // add Loan object to LoanRecords in model
        model.servingBorrowerNewLoan(loan); // add Loan object to Borrower's currentLoanList

        undoCommand = new UnloanCommand(updatedLoanedOutBook, bookToBeLoaned, loan);
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, updatedLoanedOutBook, servingBorrower));

        try {
            LoanSlipUtil.mountLoan(loan, updatedLoanedOutBook, servingBorrower);
        } catch (LoanSlipException e) {
            e.printStackTrace(); // Unable to generate loan slip, does not affect loan functionality
        }

        return commandResult;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof LoanCommand)) {
            return false;
        }

        LoanCommand otherLoanCommand = (LoanCommand) o;

        return this.toLoan.equals(otherLoanCommand.toLoan);
    }
}
