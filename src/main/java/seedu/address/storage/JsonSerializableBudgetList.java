package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BudgetList;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.budget.Budget;


/**
 * An Immutable BudgetList that is serializable to JSON format.
 */
@JsonRootName(value = "budgetlist")
class JsonSerializableBudgetList {

    public static final String MESSAGE_DUPLICATE_BUDGET = "Budgets list contains duplicate budget(s).";

    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBudgetList} with the given budgets.
     */
    @JsonCreator
    public JsonSerializableBudgetList(@JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        this.budgets.addAll(budgets);
    }

    /**
     * Converts a given {@code ReadOnlyBudgetList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBudgetList}.
     */
    public JsonSerializableBudgetList(ReadOnlyBudgetList source) {
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
    }

    /**
     * Converts this budget list into the model's {@code BudgetList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BudgetList toModelType() throws IllegalValueException {
        BudgetList budgetList = new BudgetList();
        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (budgetList.hasBudget(budget)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BUDGET);
            }
            budgetList.addBudget(budget);
        }
        return budgetList;
    }
}
