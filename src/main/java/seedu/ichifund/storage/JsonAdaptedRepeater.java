package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Jackson-friendly version of {@link Repeater}.
 */
class JsonAdaptedRepeater {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Repeater's %s field is missing!";

    private final String description;
    private final String amount;
    private final String category;
    private final String transactionType;
    private final String monthStartOffset;
    private final String monthEndOffset;
    private final JsonAdaptedDate startDate;
    private final JsonAdaptedDate endDate;

    /**
     * Constructs a {@code JsonAdaptedRepeater} with the given repeater details.
     */
    @JsonCreator
    public JsonAdaptedRepeater(@JsonProperty("description") String description,
            @JsonProperty("amount") String amount,
            @JsonProperty("category") String category,
            @JsonProperty("transactionType") String transactionType,
            @JsonProperty("monthStartOffset") String monthStartOffset,
            @JsonProperty("monthEndOffset") String monthEndOffset,
            @JsonProperty("startDate") JsonAdaptedDate startDate,
            @JsonProperty("endDate") JsonAdaptedDate endDate) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.transactionType = transactionType;
        this.monthStartOffset = monthStartOffset;
        this.monthEndOffset = monthEndOffset;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a given {@code Repeater} into this class for Jackson use.
     */
    public JsonAdaptedRepeater(Repeater source) {
        this.description = source.getDescription().toString();
        this.amount = source.getAmount().toString();
        this.category = source.getDescription().toString();
        this.transactionType = source.getTransactionType().toString();
        this.monthStartOffset = source.getMonthStartOffset().toString();
        this.monthEndOffset = source.getMonthEndOffset().toString();
        this.startDate = new JsonAdaptedDate(source.getStartDate());
        this.endDate = new JsonAdaptedDate(source.getEndDate());
    }

    /**
     * Converts this Jackson-friendly adapted repeater object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted repeater.
     */
    public Repeater toModelType() throws IllegalValueException {
        if (this.description == null) {
            throw new IllegalValueException(String.format(
                        MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(this.description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(this.description);

        if (this.amount == null) {
            throw new IllegalValueException(String.format(
                        MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(this.amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        } else if (Amount.isNegative(this.amount)) {
            throw new IllegalValueException(Amount.NEGATIVE_AMOUNT_CONSTRAINT);
        }
        final Amount modelAmount = new Amount(this.amount);

        if (this.category == null) {
            throw new IllegalValueException(String.format(
                        MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(this.category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelCategory = new Category(this.category);

        if (this.transactionType == null) {
            throw new IllegalValueException(String.format(
                        MISSING_FIELD_MESSAGE_FORMAT, TransactionType.class.getSimpleName()));
        }
        if (!TransactionType.isValidTransactionType(this.transactionType)) {
            throw new IllegalValueException(TransactionType.MESSAGE_CONSTRAINTS);
        }
        final TransactionType modelTransactionType = new TransactionType(this.transactionType);

        if (this.monthStartOffset == null) {
            throw new IllegalValueException(String.format(
                        MISSING_FIELD_MESSAGE_FORMAT, MonthOffset.class.getSimpleName()));
        }
        if (!MonthOffset.isValidMonthOffset(this.monthStartOffset)) {
            throw new IllegalValueException(MonthOffset.MESSAGE_CONSTRAINTS);
        }
        final MonthOffset modelMonthStartOffset = new MonthOffset(this.monthStartOffset);

        if (this.monthEndOffset == null) {
            throw new IllegalValueException(String.format(
                        MISSING_FIELD_MESSAGE_FORMAT, MonthOffset.class.getSimpleName()));
        }
        if (!MonthOffset.isValidMonthOffset(this.monthEndOffset)) {
            throw new IllegalValueException(MonthOffset.MESSAGE_CONSTRAINTS);
        }
        final MonthOffset modelMonthEndOffset = new MonthOffset(this.monthEndOffset);

        final Date modelStartDate = this.startDate.toModelType();

        final Date modelEndDate = this.endDate.toModelType();

        return new Repeater(modelDescription, modelAmount, modelCategory, modelTransactionType,
                modelMonthStartOffset, modelMonthEndOffset, modelStartDate, modelEndDate);
    }

}
