package thrift.storage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import thrift.commons.exceptions.IllegalValueException;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;

/**
 * Jackson-friendly version of {@link Budget}.
 */
public class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String period;
    private final String value;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("period") String period, @JsonProperty("value") String value) {
        this.period = period;
        this.value = value;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        period = source.getBudgetDateString();
        value = source.getBudgetValue().getUnformattedString();
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there are any data constraints violated in the adapted budget.
     */
    public Budget toModelType() throws IllegalValueException {
        final Calendar modelPeriod;
        final BudgetValue modelValue;

        if (period == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "month String"));
        }
        try {
            Date temp = Budget.BUDGET_DATE_FORMAT.parse(period);
            modelPeriod = Calendar.getInstance();
            modelPeriod.setTime(temp);
        } catch (ParseException pe) {
            throw new IllegalValueException(Budget.DATE_CONSTRAINTS);
        }

        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BudgetValue.class.getSimpleName()));
        }
        if (!BudgetValue.isValidValue(value)) {
            throw new IllegalValueException(BudgetValue.VALUE_CONSTRAINTS);
        }
        modelValue = new BudgetValue(value);

        return new Budget(modelPeriod, modelValue);
    }
}
