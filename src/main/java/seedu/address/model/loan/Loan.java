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
public class Loan implements Comparable<Loan> {

    private final LoanId loanId;
    private final SerialNumber bookSerialNumber;
    private final BorrowerId borrowerId;
    private final LocalDate startDate;
    private final LocalDate dueDate;
    private final LocalDate returnDate;
    private final int renewCount;
    private final int remainingFineAmount; // in cents
    private final int paidFineAmount; // in cents


    public Loan(LoanId loanId, SerialNumber bookSerialNumber, BorrowerId borrowerId,
                LocalDate startDate, LocalDate dueDate) {
        this(loanId, bookSerialNumber, borrowerId, startDate, dueDate, null, 0, 0, 0);
    }

    // returnDate can be null
    public Loan(LoanId loanId, SerialNumber bookSerialNumber, BorrowerId borrowerId,
                LocalDate startDate, LocalDate dueDate, LocalDate returnDate,
                int renewCount, int remainingFineAmount, int paidFineAmount) {
        requireAllNonNull(loanId, bookSerialNumber, borrowerId, startDate, dueDate);
        this.loanId = loanId;
        this.bookSerialNumber = bookSerialNumber;
        this.borrowerId = borrowerId;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.renewCount = renewCount;
        this.remainingFineAmount = remainingFineAmount;
        this.paidFineAmount = paidFineAmount;
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

    // may return null if book is not returned yet
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public int getRemainingFineAmount() {
        return remainingFineAmount;
    }

    public int getPaidFineAmount() {
        return paidFineAmount;
    }

    /**
     * Returns a new copy of the Loan object after its book is returned.
     *
     * @param returnDate Date which loan is returned.
     * @param fineIncurred Fine incurred for returning the loaned book.
     * @return A new copy of the Loan object with returnDate and remainingFineAmount updated.
     */
    public Loan returnLoan(LocalDate returnDate, int fineIncurred) {
        return new Loan(this.loanId, this.bookSerialNumber, this.borrowerId, this.startDate, this.dueDate,
                returnDate, this.renewCount, fineIncurred, 0);
    }

    /**
     * Returns a new copy of the Loan object after it is renewed.
     *
     * @param extendedDueDate New dueDate after being extended.
     * @return A new copy of the Loan object with dueDate and renewCount updated.
     */
    public Loan renewLoan(LocalDate extendedDueDate) {
        return new Loan(this.loanId, this.bookSerialNumber, this.borrowerId, this.startDate, extendedDueDate,
                this.returnDate, this.renewCount + 1, this.remainingFineAmount, this.paidFineAmount);
    }

    /**
     * Returns a new copy of the loan object after any amount of its remaining fine is paid.
     *
     * @param paidAmount Amount given to pay the remainingFineAmount.
     * @return A new copy of the Loan object with remainingFineAmount and paidFineAmount updated.
     */
    public Loan payFine(int paidAmount) {
        return new Loan(this.loanId, this.bookSerialNumber, this.borrowerId, this.startDate, this.dueDate,
                this.returnDate, this.renewCount, this.remainingFineAmount - paidAmount,
                this.paidFineAmount + paidAmount);
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
                && otherLoan.getDueDate().equals(dueDate)
                && (otherLoan.getReturnDate() == null || otherLoan.getReturnDate().equals(returnDate))
                && otherLoan.getRenewCount() == renewCount
                && otherLoan.getRemainingFineAmount() == remainingFineAmount
                && otherLoan.getPaidFineAmount() == paidFineAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, bookSerialNumber, borrowerId, startDate, dueDate, returnDate,
                renewCount, remainingFineAmount, paidFineAmount);
    }

    @Override
    public int compareTo(Loan other) {
        return this.getLoanId().compareTo(other.getLoanId());
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

        if (returnDate != null) {
            builder.append(". Returned on ")
                    .append(returnDate);
        }

        return builder.toString();
    }
}
