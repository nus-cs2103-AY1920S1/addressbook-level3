package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String description;
    private final String amount;
    private final String month;
    private final String year;
    private final String category;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("month") String month,
                             @JsonProperty("year") String year,
                             @JsonProperty("category") String category) {
        this.description = description;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.category = category;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        description = source.getDescription().toString();
        amount = source.getAmount().toString().substring(1);
        month = source.getMonth() != null ? source.getMonth().toString() : null;
        year = source.getYear() != null ? source.getYear().toString() : null;
        category = source.getCategory() != null ? source.getCategory().toString() : null;
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted budget.
     */
    public Budget toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        final Month modelMonth = month != null ? new Month(month) : null;
        final Year modelYear = year != null ? new Year(year) : null;
        final Category modelCategory = category != null ? new Category(category) : null;

        return new Budget(modelDescription, modelAmount, modelMonth, modelYear, modelCategory);
    }

}
