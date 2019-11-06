package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_CANNOT_BE_RENEWED_ANYMORE;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_IS_OVERDUE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_RENEWABLE_BOOKS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Renews all valid Books or the Book with the given Index.
 */
public class RenewCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "renew";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renew book(s) borrowed by a borrower.\n"
            + "Command can only be used in Serve mode.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "or\n"
            + "-all\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " -all";

    public static final String MESSAGE_SUCCESS =
            "Book: %1$s\nrenewed by\nBorrower: %2$s\nDue date: %3$s\n\n";

    private final Index index;
    private final boolean isRenewAll;

    /**
     * Creates a RenewCommand to renew the currently served Borrower's {@code Book}.
     *
     * @param index Index of book to be renewed.
     */
    public RenewCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.isRenewAll = false;
    }

    /**
     * Creates a RenewCommand to renew all of the currently served Borrower's renewable {@code Book}s.
     */
    public RenewCommand() {
        this.isRenewAll = true;
        this.index = null;
    }

    /**
     * Executes the RenewCommand and returns the result message.
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

        ArrayList<Book> renewingBooks = getRenewingBooks(model);
        Borrower servingBorrower = model.getServingBorrower();

        String feedbackMessage = "";
        ArrayList<Book> renewedBookList = new ArrayList<>();
        ArrayList<Book> bookToBeRenewedList = new ArrayList<>();
        ArrayList<Loan> renewedLoanList = new ArrayList<>();
        ArrayList<Loan> loanToBeRenewedList = new ArrayList<>();

        for (Book bookToBeRenewed : renewingBooks) {
            Loan loanToBeRenewed = bookToBeRenewed.getLoan().get();

            if (!isRenewAll) { // only check if renew is used with an index
                if (loanToBeRenewed.getRenewCount() >= model.getUserSettings().getMaxRenews()) {
                    throw new CommandException(String.format(MESSAGE_BOOK_CANNOT_BE_RENEWED_ANYMORE, bookToBeRenewed));
                }

                if (DateUtil.isDateBeforeToday(loanToBeRenewed.getDueDate())) {
                    throw new CommandException(String.format(MESSAGE_BOOK_IS_OVERDUE, bookToBeRenewed));
                }
            }

            LocalDate extendedDueDate = DateUtil.extendDate(loanToBeRenewed.getDueDate(),
                    model.getUserSettings().getRenewPeriod());
            Loan renewedLoan = loanToBeRenewed.renewLoan(extendedDueDate);

            Book renewedBook = new Book(bookToBeRenewed.getTitle(), bookToBeRenewed.getSerialNumber(),
                    bookToBeRenewed.getAuthor(), renewedLoan, bookToBeRenewed.getGenres());

            // update Book in model to have Loan due date extended
            model.setBook(bookToBeRenewed, renewedBook);

            // update Loan in Borrower's currentLoanList
            model.servingBorrowerRenewLoan(loanToBeRenewed, renewedLoan);

            // update Loan in LoanRecords with extended due date
            model.updateLoan(loanToBeRenewed, renewedLoan);

            // mount renewed loan
            try {
                LoanSlipUtil.mountLoan(renewedLoan, renewedBook, servingBorrower);
            } catch (LoanSlipException e) {
                e.printStackTrace(); // Unable to generate loan slip, does not affect loan functionality
            }

            feedbackMessage += String.format(MESSAGE_SUCCESS, renewedBook,
                    servingBorrower, DateUtil.formatDate(extendedDueDate));
            renewedBookList.add(renewedBook);
            bookToBeRenewedList.add(bookToBeRenewed);
            renewedLoanList.add(renewedLoan);
            loanToBeRenewedList.add(loanToBeRenewed);
        }

        undoCommand = new UnrenewCommand(renewedBookList, bookToBeRenewedList, renewedLoanList,
                loanToBeRenewedList);
        redoCommand = this;
        commandResult = new CommandResult(feedbackMessage.trim());

        return commandResult;
    }

    private ArrayList<Book> getRenewingBooks(Model model) throws CommandException {
        List<Book> lastShownBorrowerBooksList = model.getBorrowerBooks();
        ArrayList<Book> renewingBooks = new ArrayList<>();

        if (isRenewAll) { // renew all valid books
            for (Book book : lastShownBorrowerBooksList) {
                Loan loan = book.getLoan().get();
                if (loan.getRenewCount() >= model.getUserSettings().getMaxRenews()) { // hit max renews already
                    continue;
                }
                if (DateUtil.isDateBeforeToday(loan.getDueDate())) { // overdue
                    continue;
                }

                renewingBooks.add(book);
            }

            if (renewingBooks.isEmpty()) {
                throw new CommandException(MESSAGE_NO_RENEWABLE_BOOKS);
            }
        } else { // renew book corresponding to index
            if (index.getZeroBased() >= lastShownBorrowerBooksList.size()) {
                throw new CommandException(MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
            }

            Book bookToBeRenewed = lastShownBorrowerBooksList.get(index.getZeroBased());
            renewingBooks.add(bookToBeRenewed);
        }

        return renewingBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof RenewCommand)) {
            return false;
        }

        RenewCommand otherRenewCommand = (RenewCommand) o;
        return ((this.index == null) || (this.index.equals(otherRenewCommand.index)))
                && this.isRenewAll == otherRenewCommand.isRenewAll;
    }
}
