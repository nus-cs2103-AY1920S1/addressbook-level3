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
    public static final String INTEGER_VALUE_CONSTRAINTS = "%s should be an integer!";
    public static final String START_DATE = "Start Date";
    public static final String DUE_DATE = "Due Date";
    public static final String RETURN_DATE = "Return Date";
    public static final String RENEW_COUNT = "Renew Count";
    public static final String REMAINING_FINE_AMOUNT = "Remaining Fine Amount";
    public static final String PAID_FINE_AMOUNT = "Paid Fine Amount";

    private final String loanId;
    private final String bookSerialNumber;
    private final String borrowerId;
    private final String startDate;
    private final String dueDate;
    private final String returnDate;
    private final String renewCount;
    private final String remainingFineAmount;
    private final String paidFineAmount;

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given Loan details.
     */
    @JsonCreator
    public JsonAdaptedLoan(@JsonProperty("loanId") String loanId,
                           @JsonProperty("bookSerialNumber") String bookSerialNumber,
                           @JsonProperty("borrowerId") String borrowerId,
                           @JsonProperty("startDate") String startDate,
                           @JsonProperty("dueDate") String dueDate,
                           @JsonProperty("returnDate") String returnDate,
                           @JsonProperty("renewCount") String renewCount,
                           @JsonProperty("remainingFineAmount") String remainingFineAmount,
                           @JsonProperty("paidFineAmount") String paidFineAmount) {
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

    /**
     * Converts a given {@code Loan} into this class for Jackson use.
     */
    public JsonAdaptedLoan(Loan source) {
        loanId = source.getLoanId().value;
        bookSerialNumber = source.getBookSerialNumber().value;
        borrowerId = source.getBorrowerId().value;
        startDate = source.getStartDate().toString();
        dueDate = source.getDueDate().toString();
        returnDate = source.getReturnDate() == null ? "" : source.getReturnDate().toString();
        renewCount = String.valueOf(source.getRenewCount());
        remainingFineAmount = String.valueOf(source.getRemainingFineAmount());
        paidFineAmount = String.valueOf(source.getPaidFineAmount());
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

        final LocalDate modelStartDate;
        final LocalDate modelDueDate;
        final LocalDate modelReturnDate;

        if (startDate == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, START_DATE)));
        }
        if (dueDate == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, DUE_DATE)));
        }
        if (returnDate == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, RETURN_DATE)));
        }

        try {
            modelStartDate = LocalDate.parse(startDate);
            modelDueDate = LocalDate.parse(dueDate);
            modelReturnDate = returnDate.equals("") ? null : LocalDate.parse(returnDate);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(DATE_MESSAGE_CONSTRAINTS);
        }

        final int modelRenewCount = getModelInt(renewCount, RENEW_COUNT);
        final int modelRemainingFineAmount = getModelInt(remainingFineAmount, REMAINING_FINE_AMOUNT);
        final int modelPaidFineAmount = getModelInt(paidFineAmount, PAID_FINE_AMOUNT);

        return new Loan(modelBookLoanId, modelBookSerialNumber, modelBorrowerId, modelStartDate, modelDueDate,
                modelReturnDate, modelRenewCount, modelRemainingFineAmount, modelPaidFineAmount);
    }

    private static int getModelInt(String field, String fieldName) throws IllegalValueException {
        final int modelInt;
        if (field == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName)));
        }
        try {
            modelInt = Integer.parseInt(field);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(String.format(INTEGER_VALUE_CONSTRAINTS, fieldName));
        }
        return modelInt;
    }
}
