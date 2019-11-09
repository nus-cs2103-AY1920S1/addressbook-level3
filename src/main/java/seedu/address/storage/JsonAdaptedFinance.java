package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Jackson-friendly version of {@link Finance}.
 */
public class JsonAdaptedFinance {

    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFinance} with the given {@code budgets}.
     */
    @JsonCreator
    public JsonAdaptedFinance(@JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        requireAllNonNull(budgets);
        this.budgets.addAll(budgets);
    }

    /**
     * Converts a given {@code Finance} into this class for Jackson use.
     */
    public JsonAdaptedFinance(Finance source) {
        for (Budget budget : source.getBudgets()) {
            budgets.add(new JsonAdaptedBudget(budget));
        }
    }

    public List<JsonAdaptedBudget> getBudgets() {
        return budgets;
    }

    /**
     * Converts this Jackson-friendly adapted Finance object into the model's {@code Finance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Finance.
     */
    public Finance toModelType() throws IllegalValueException, ParseException {
        List<Budget> resultBudget = new ArrayList<>();
        for (JsonAdaptedBudget budget : budgets) {
            resultBudget.add(budget.toModelType());
        }

        return new Finance(resultBudget);
    }

}
