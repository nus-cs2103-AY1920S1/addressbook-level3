package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set inward expression.
 */
public class SetInwardExpression extends PerformableExpression {

    /**
     * Constructs a SetInwardExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetInwardExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn, Account account) {
        requireAllNonNull(model, model.getAccountsManager(), txn);

        AccountsManager accountsManager = model.getAccountsManager();
        Direction updatedDirection = Direction.IN;

        Transaction updatedTransaction = new Transaction(txn.getDate(), txn.getAmount(), updatedDirection,
                txn.getDescription(), txn.getCategories());

        accountsManager.getActiveAccount().deleteTransaction(txn);
        accountsManager.getAccount(account.getName()).addTransaction(updatedTransaction);

        logger.info("Rule Execution———Direction updated in " + updatedTransaction);
    }
}
