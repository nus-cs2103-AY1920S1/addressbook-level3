package seedu.address.financialtracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.financialtracker.model.FinancialTracker;
import seedu.address.financialtracker.model.expense.Expense;

/**
 * An Immutable FinancialTracker that is serializable to JSON format.
 */
@JsonRootName(value = "financialtracker")
public class JsonSerializableFinancialTracker {

    public static final String MESSAGE_DUPLICATE_PERSON = "Expenses list contains duplicate expense(s).";

    //private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablFinancialTracker} with the given expenses.
     */
    @JsonCreator
    public JsonSerializableFinancialTracker(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        //this.expenses.addAll(expenses);
    }

    /**
     * Converts a given {@code FinancialTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablFinancialTracker}.
     */
    public JsonSerializableFinancialTracker(FinancialTracker source) {
        //expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the financialTrackerModel's {@code FinancialTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinancialTracker toModelType() throws IllegalValueException {
        FinancialTracker financialTracker = new FinancialTracker();
        /*
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            financialTracker.addExpense(expense);
        }
        */
        return financialTracker;
    }

}
