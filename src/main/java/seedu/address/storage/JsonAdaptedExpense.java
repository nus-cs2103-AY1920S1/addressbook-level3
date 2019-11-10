package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.DayNumber;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.MiscExpense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Jackson friendly version of {@code Expense}.
 */
public class JsonAdaptedExpense {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";

    private final String name;
    private final Double budget;
    private final String dayNumber;
    private final String type;

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("name") String name,
                                  @JsonProperty("budget") Double budget,
                                  @JsonProperty("dayNumber") String dayNumber,
                                  @JsonProperty("type") String type) {
        this.name = name;
        this.budget = budget;
        this.dayNumber = dayNumber;
        this.type = type;

    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        this.name = source.getName().toString();
        this.budget = source.getBudget().getValue();
        this.dayNumber = source.getDayNumber().toString();
        if (source instanceof MiscExpense) {
            this.type = "misc";
        } else if (source instanceof PlannedExpense) {
            this.type = "planned";
        } else {
            throw new AssertionError("Unsupported expense type");
        }

    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (budget == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }

        if (!Budget.isValidBudget(budget.toString())) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final DayNumber modelDayNumber = new DayNumber(dayNumber);
        final Name modelName = new Name(name);
        final Budget modelTotalBudget = new Budget(budget);

        if (type.equals("misc")) {
            return new MiscExpense(modelName, modelTotalBudget, modelDayNumber);
        } else if (type.equals("planned")) {
            return new PlannedExpense(modelName, modelTotalBudget, modelDayNumber);
        } else {
            throw new IllegalValueException("Invalid expense type");
        }
    }
}
