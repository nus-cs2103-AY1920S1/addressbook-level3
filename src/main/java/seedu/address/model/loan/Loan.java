package seedu.address.model.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerID;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan {

    private final SerialNumber bookSerialNumber;
    private final BorrowerID borrowerID;
    private final LocalDate startDate;
    private final LocalDate dueDate;

    public Loan(SerialNumber bookSerialNumber, BorrowerID borrowerID, LocalDate startDate, LocalDate dueDate) {
        requireAllNonNull(bookSerialNumber, borrowerID, startDate, dueDate);
        this.bookSerialNumber = bookSerialNumber;
        this.borrowerID = borrowerID;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public SerialNumber getBookSerialNumber() {
        return bookSerialNumber;
    }

    public BorrowerID getBorrowerID() {
        return borrowerID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public boolean equals(Object other) {
        return true; // TODO
    }

    @Override
    public int hashCode() {
        return 0; // TODO
    }

    @Override
    public String toString() {
        return ""; // TODO
    }
}
