package seedu.address.model.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;

/**
 * Represents a Loan.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan {

    private final LoanId loanId;
    private final SerialNumber bookSerialNumber;
    private final BorrowerId borrowerId;
    private final LocalDate startDate;
    private final LocalDate dueDate;

    public Loan(LoanId loanId, SerialNumber bookSerialNumber, BorrowerId borrowerId,
                LocalDate startDate, LocalDate dueDate) {
        requireAllNonNull(loanId, bookSerialNumber, borrowerId, startDate, dueDate);
        this.loanId = loanId;
        this.bookSerialNumber = bookSerialNumber;
        this.borrowerId = borrowerId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public SerialNumber getBookSerialNumber() {
        return bookSerialNumber;
    }

    public BorrowerId getBorrowerId() {
        return borrowerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return otherLoan.getLoanId().equals(loanId)
                && otherLoan.getBookSerialNumber().equals(bookSerialNumber)
                && otherLoan.getBorrowerId().equals(borrowerId)
                && otherLoan.getStartDate().equals(startDate)
                && otherLoan.getDueDate().equals(dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, bookSerialNumber, borrowerId, startDate, dueDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Loan ID: ")
                .append(loanId)
                .append(" Book Serial Number: ")
                .append(bookSerialNumber)
                .append(" Borrower ID: ")
                .append(borrowerId)
                .append(" Loaned from ")
                .append(startDate)
                .append(" to ")
                .append(dueDate);
        return builder.toString();
    }
}
