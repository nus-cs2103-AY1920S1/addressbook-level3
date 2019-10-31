package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set description expression.
 */
public class SetDescriptionExpression extends PerformableExpression {

    /**
     * Constructs a SetDescriptionExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetDescriptionExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn, Account account) {
        requireAllNonNull(model, model.getAccountsManager(), txn);

        AccountsManager accountsManager = model.getAccountsManager();
        try {
            Description updatedDesc = CommandParserUtil.parseDescription(value.toString());

            Transaction updatedTransaction = new Transaction(txn.getDate(), txn.getAmount(), txn.getDirection(),
                    updatedDesc, txn.getCategories());

            accountsManager.getActiveAccount().deleteTransaction(txn);
            accountsManager.getAccount(account.getName()).addTransaction(updatedTransaction);

            logger.info("Rule Execution———Description updated in " + updatedTransaction);
        } catch (ParseException e) {
            // Should not happen as value should be parsable by the time this method is called
            // but will exit without completing if it does happen.
        }
    }
}
