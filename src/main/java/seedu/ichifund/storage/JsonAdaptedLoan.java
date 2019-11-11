package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.loan.Name;

/**
 * Jackson-friendly version of {@link Loan}.
 */
public class JsonAdaptedLoan {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Loan's %s field is missing!";

    private final String loanId;
    private final String amount;
    private final String name;
    private final JsonAdaptedDate startDate;
    private final JsonAdaptedDate endDate;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given loan details.
     */
    @JsonCreator
    public JsonAdaptedLoan(@JsonProperty("loanId") String loanId,
                           @JsonProperty("amount") String amount,
                           @JsonProperty("name") String name,
                           @JsonProperty("startDate") JsonAdaptedDate startDate,
                           @JsonProperty("endDate") JsonAdaptedDate endDate,
                           @JsonProperty("description") String description) {
        this.loanId = loanId;
        this.amount = amount;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;;
    }

    /**
     * Converts a given {@code Loan} into this class for Jackson use.
     */
    public JsonAdaptedLoan(Loan source) {
        this.loanId = source.getLoanId().toString();
        this.amount = source.getAmount().toString();
        this.name = source.getName().toString();
        this.startDate = new JsonAdaptedDate(source.getStartDate());
        this.endDate = new JsonAdaptedDate(source.getEndDate());
        this.description = source.getDescription().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Loan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted loan.
     */
    public Loan toModelType() throws IllegalValueException {
        if (startDate == null || endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        final Date modelStartDate = startDate.toModelType();
        final Date modelEndDate = endDate.toModelType();

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        } else if (Amount.isNegative(amount)) {
            throw new IllegalValueException(Amount.POSITIVE_AMOUNT_CONSTRAINT);
        }
        final Amount modelAmount = new Amount(amount);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (loanId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanId.class.getSimpleName()));
        }
        if (!LoanId.isValidLoanId(loanId)) {
            throw new IllegalValueException(LoanId.MESSAGE_CONSTRAINTS);
        }
        final LoanId modelLoanId = new LoanId(loanId);

        return new Loan(modelLoanId, modelAmount, modelName, modelStartDate, modelEndDate, modelDescription);
    }
}
