package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String initialAmount;
    private final String amount;
    private final String date;
    private final List<JsonAdaptedCategory> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("initialAmount") String initialAmount,
                             @JsonProperty("amount") String amount, @JsonProperty("date") String date,
                             @JsonProperty("tagged") List<JsonAdaptedCategory> tagged) {
        this.initialAmount = initialAmount;
        this.amount = amount;
        this.date = date;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Budget} into this class for Jason use.
     */
    public JsonAdaptedBudget(Budget source) {
        initialAmount = source.getInitialBudget().toString();
        amount = source.getBudget().toString();
        date = source.getDeadline().toString();
        tagged.addAll(source.getCategories().stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted budgets.
     */
    public Budget toModelType() throws IllegalValueException {
        final List<Category> budgetCategories = new ArrayList<>();
        for (JsonAdaptedCategory category : tagged) {
            budgetCategories.add(category.toModelType());
        }

        if (initialAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!Date.isValid(date)) {
            throw new IllegalValueException(Date.MESSAGE_FORMAT_CONSTRAINTS);
        }

        final Set<Category> modelCategories = new HashSet<>(budgetCategories);

        return new Budget(new Amount(Double.parseDouble(initialAmount)),
            new Amount(Double.parseDouble(amount)), new Date(date), modelCategories);
    }

}
