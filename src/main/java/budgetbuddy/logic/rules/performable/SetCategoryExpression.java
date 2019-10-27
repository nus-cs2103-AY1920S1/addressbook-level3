package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.Model;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set category expression.
 */
public class SetCategoryExpression extends PerformableExpression {

    /**
     * Constructs a SetCategoryExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetCategoryExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn) {
        requireAllNonNull(model, txn);
        Category toSet = new Category(value.toString());
        // TODO: Set category
        System.out.println("ADDED CATEGORY!!! " + toSet.toString());
    }
}
