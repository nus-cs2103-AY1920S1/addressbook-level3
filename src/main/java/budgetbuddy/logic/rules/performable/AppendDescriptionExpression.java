package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a append description expression.
 */
public class AppendDescriptionExpression extends PerformableExpression {

    /**
     * Constructs a AppendDescriptionExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public AppendDescriptionExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Index txnIndex, Account account) {
        requireAllNonNull(model, txnIndex);

        try {
            Transaction toEdit = account.getTransaction(txnIndex);
            Description updatedDesc = CommandParserUtil.parseDescription(toEdit.getDescription().toString()
                    + value.toString());

            Transaction updatedTransaction = new Transaction(toEdit.getLocalDate(), toEdit.getAmount(),
                    toEdit.getDirection(), updatedDesc, toEdit.getCategories());

            account.updateTransaction(txnIndex, updatedTransaction);

            logger.info("Rule Execution———Description updated in:\n" + updatedTransaction);
        } catch (ParseException e) {
            // Should not happen as value should be parsable by the time this method is called
            // but will exit without completing if it does happen.
        }
    }
}
