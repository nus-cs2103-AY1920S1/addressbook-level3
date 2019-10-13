package seedu.address.storage.loanrecords;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

/**
 * Jackson-friendly version of {@link Loan}.
 */
class JsonAdaptedLoan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Loan's %s field is missing!";
    public static final String DATE_MESSAGE_CONSTRAINTS = "Date should be in the ISO-8601 format YYYY-MM-DD";
    public static final String START_DATE = "Start Date";
    public static final String DUE_DATE = "Due Date";

    private final String loanId;
    private final String bookSerialNumber;
    private final String borrowerId;
    private final String startDate;
    private final String dueDate;

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given Loan details.
     */
    @JsonCreator
    public JsonAdaptedLoan(@JsonProperty("loanId") String loanId,
                           @JsonProperty("bookSerialNumber") String bookSerialNumber,
                           @JsonProperty("borrowerId") String borrowerId,
                           @JsonProperty("startDate") String startDate,
                           @JsonProperty("dueDate") String dueDate) {
        this.loanId = loanId;
        this.bookSerialNumber = bookSerialNumber;
        this.borrowerId = borrowerId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    /**
     * Converts a given {@code Loan} into this class for Jackson use.
     */
    public JsonAdaptedLoan(Loan source) {
        loanId = source.getLoanId().value;
        bookSerialNumber = source.getBookSerialNumber().value;
        borrowerId = source.getBorrowerId().value;
        startDate = source.getStartDate().toString();
        dueDate = source.getDueDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted Loan object into the model's {@code Loan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Loan.
     */
    public Loan toModelType() throws IllegalValueException {
        if (loanId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanId.class.getSimpleName()));
        }
        if (!LoanId.isValidLoanId(loanId)) {
            throw new IllegalValueException(LoanId.MESSAGE_CONSTRAINTS);
        }
        final LoanId modelBookLoanId = new LoanId(loanId);

        if (bookSerialNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SerialNumber.class.getSimpleName()));
        }
        if (!SerialNumber.isValidSerialNumber(bookSerialNumber)) {
            throw new IllegalValueException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        final SerialNumber modelBookSerialNumber = new SerialNumber(bookSerialNumber);

        if (borrowerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BorrowerId.class.getSimpleName()));
        }
        if (!BorrowerId.isValidBorrowerId(borrowerId)) {
            throw new IllegalValueException(BorrowerId.MESSAGE_CONSTRAINTS);
        }
        final BorrowerId modelBorrowerId = new BorrowerId(borrowerId);

        if (startDate == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, START_DATE)));
        }
        if (dueDate == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, DUE_DATE)));
        }
        try {
            LocalDate.parse(startDate);
            LocalDate.parse(dueDate);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(DATE_MESSAGE_CONSTRAINTS);
        }
        final LocalDate modelStartDate = LocalDate.parse(startDate);
        final LocalDate modelDueDate = LocalDate.parse(dueDate);

        return new Loan(modelBookLoanId, modelBookSerialNumber, modelBorrowerId, modelStartDate, modelDueDate);
    }

}
