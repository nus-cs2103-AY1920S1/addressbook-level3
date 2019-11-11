package seedu.address.storage.finance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.budget.Budget;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String amount;
    private final String startDate;
    private final String endDate;
    private final String budgetType;
    private final String budgetTypeValue;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("amount") String amount,
                             @JsonProperty("startDate") String startDate,
                             @JsonProperty("endDate") String endDate,
                             @JsonProperty("budgetType") String budgetType,
                             @JsonProperty("budgetTypeValue") String budgetTypeValue) {
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetType = budgetType;
        this.budgetTypeValue = budgetTypeValue;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        amount = source.getAmount().toString();
        startDate = Budget.toStringDate(source.getStartDate());
        endDate = Budget.toStringDate(source.getEndDate());
        budgetType = source.getBudgetType();
        budgetTypeValue = source.getBudgetTypeValue();
    }

    /**
     * Converts this Jackson-friendly adapted budget object
     * into the financeModel's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Budget toModelType() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (startDate == null || endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");
        final Date modelStartDate;
        final Date modelEndDate;
        try {
            modelStartDate = validFormat.parse(startDate);
            modelEndDate = validFormat.parse(endDate);
        } catch (ParseException e) {
            throw new IllegalValueException("Dates should be of format DD-MM-YYYY.");
        }

        if (budgetType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    String.class.getSimpleName()));
        }
        if (!Budget.isValidBudgetType(budgetType)) {
            throw new IllegalValueException(Budget.MESSAGE_BUDGETYPE_CONSTRAINTS);
        }
        final String modelBudgetType = budgetType;

        final String modelBudgetTypeValue = budgetTypeValue;

        return new Budget(modelAmount, modelStartDate, modelEndDate,
                modelBudgetType, modelBudgetTypeValue);
    }

}
