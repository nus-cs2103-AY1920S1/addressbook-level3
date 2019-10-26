package seedu.address.testutil;

import static seedu.address.testutil.BookBuilder.DEFAULT_SERIAL_NUMBER;
import static seedu.address.testutil.BorrowerBuilder.DEFAULT_BORROWER_ID;

import java.time.LocalDate;

import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

/**
 * A utility class to help with building Loan objects.
 */
public class LoanBuilder {

    public static final String DEFAULT_LOAN_ID = "L000001";
    public static final String DEFAULT_START_DATE = "2019-10-13";
    public static final String DEFAULT_DUE_DATE = "2019-10-27";

    private LoanId loanId;
    private SerialNumber bookSerialNumber;
    private BorrowerId borrowerId;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int renewCount;
    private int remainingFineAmount; // in cents
    private int paidFineAmount; // in cents

    /**
     * Empty argument constructor to initialise LoanBuilder with default values for the fields.
     */
    public LoanBuilder() {
        loanId = new LoanId(DEFAULT_LOAN_ID);
        bookSerialNumber = new SerialNumber(DEFAULT_SERIAL_NUMBER);
        borrowerId = new BorrowerId(DEFAULT_BORROWER_ID);
        startDate = LocalDate.parse(DEFAULT_START_DATE);
        dueDate = LocalDate.parse(DEFAULT_DUE_DATE);
        returnDate = null;
        renewCount = 0;
        remainingFineAmount = 0;
        paidFineAmount = 0;
    }

    /**
     * Constructor that uses another Loan object to copy its values from.
     *
     * @param loanToCopy {@code Loan} object to copy values from.
     */
    public LoanBuilder(Loan loanToCopy) {
        loanId = loanToCopy.getLoanId();
        bookSerialNumber = loanToCopy.getBookSerialNumber();
        borrowerId = loanToCopy.getBorrowerId();
        startDate = loanToCopy.getStartDate();
        dueDate = loanToCopy.getDueDate();
        returnDate = loanToCopy.getReturnDate();
        renewCount = loanToCopy.getRenewCount();
        remainingFineAmount = loanToCopy.getRemainingFineAmount();
        paidFineAmount = loanToCopy.getPaidFineAmount();
    }

    /**
     * Sets the {@code LoanId} of the {@code Loan} that we are building.
     */
    public LoanBuilder withLoanId(String loanId) {
        this.loanId = new LoanId(loanId);
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code Loan} we are building.
     */
    public LoanBuilder withSerialNumber(String serialNumber) {
        this.bookSerialNumber = new SerialNumber(serialNumber);
        return this;
    }

    /**
     * Sets the {@code BorrowerId} of the {@code Loan} we are building.
     */
    public LoanBuilder withBorrowerId(String borrowerId) {
        this.borrowerId = new BorrowerId(borrowerId);
        return this;
    }

    /**
     * Sets the starting date of the {@code Loan} we are building.
     */
    public LoanBuilder withStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
        return this;
    }
    /**
     * Sets the starting date of the {@code Loan} we are building. Accepts a {@code LocalDate}
     */
    public LoanBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the due date of the {@code Loan} we are building.
     */
    public LoanBuilder withDueDate(String dueDate) {
        this.dueDate = LocalDate.parse(dueDate);
        return this;
    }
    /**
     * Sets the due date of the {@code Loan} we are building. Accepts a {@code LocalDate}
     */
    public LoanBuilder withDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Sets the return date of the {@code Loan} we are building.
     */
    public LoanBuilder withReturnDate(String returnDate) {
        this.returnDate = LocalDate.parse(returnDate);
        return this;
    }

    /**
     * Sets the number of renews the {@code Loan} we are building has.

     */
    public LoanBuilder withRenewCount(int renewCount) {
        this.renewCount = renewCount;
        return this;
    }

    /**
     * Sets the remaining fine amount of the {@code Loan} we are building.
     */
    public LoanBuilder withRemainingFineAmount(int remainingFineAmount) {
        this.remainingFineAmount = remainingFineAmount;
        return this;
    }

    /**
     * Sets the paid fine amount of the {@code Loan} we are building.
     */
    public LoanBuilder withPaidFineAmount(int paidFineAmount) {
        this.paidFineAmount = paidFineAmount;
        return this;
    }

    /**
     * Returns a Loan object based on specified fields.
     * @return {@code Loan} object.
     */
    public Loan build() {
        return new Loan(loanId, bookSerialNumber, borrowerId, startDate, dueDate, returnDate,
                renewCount, remainingFineAmount, paidFineAmount);
    }
}
