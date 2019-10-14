package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.expense.Expense;

/**
 * An Immutable ExpenseList that is serializable to JSON format.
 */
@JsonRootName(value = "expenselist")
class JsonSerializableExpenseList {

    public static final String MESSAGE_DUPLICATE_EXPENSE = "Expenses list contains duplicate expense(s).";

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExpenseList} with the given expenses.
     */
    @JsonCreator
    public JsonSerializableExpenseList(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        this.expenses.addAll(expenses);
    }

    /**
     * Converts a given {@code ReadOnlyExpenseList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExpenseList}.
     */
    public JsonSerializableExpenseList(ReadOnlyExpenseList source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code ExpenseList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExpenseList toModelType() throws IllegalValueException {
        ExpenseList expenseList = new ExpenseList();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (expenseList.hasExpense(expense)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXPENSE);
            }
            expenseList.addExpense(expense);
        }
        return expenseList;
    }
}
