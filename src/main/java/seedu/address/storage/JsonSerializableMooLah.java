package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.DataInconsistencyException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MooLah;
import seedu.address.model.ReadOnlyMooLah;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;

/**
 * An Immutable MooLah that is serializable to JSON format.
 */
@JsonRootName(value = "moolah")
class JsonSerializableMooLah {

    public static final String MESSAGE_DUPLICATE_EXPENSE = "Expenses list contains duplicate expense(s).";

    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    public static final String MESSAGE_DUPLICATE_BUDGET = "Budgets list contains duplicate budget(s).";

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMooLah} with the given expenses.
     */
    @JsonCreator
    public JsonSerializableMooLah(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses,
                                  @JsonProperty("events") List<JsonAdaptedEvent> events,
                                  @JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        this.expenses.addAll(expenses);
        this.events.addAll(events);
        this.budgets.addAll(budgets);
    }

    /**
     * Converts a given {@code ReadOnlyMooLah} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMooLah}.
     */
    public JsonSerializableMooLah(ReadOnlyMooLah source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
    }

    /**
     * Converts this MooLah into the model's {@code MooLah} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MooLah toModelType() throws IllegalValueException, DataInconsistencyException {
        MooLah mooLah = new MooLah();
        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType(expenses);
            boolean isDefaultBudget = budget.isSameBudget(Budget.createDefaultBudget());
            if (!isDefaultBudget) {
                if (mooLah.hasBudget(budget)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_BUDGET);
                }
                mooLah.addBudgetFromStorage(budget);
            }
        }

        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (mooLah.hasExpense(expense)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXPENSE);
            }
            mooLah.addExpense(expense);
        }

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (mooLah.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            mooLah.addEvent(event);
        }
        return mooLah;
    }

}
