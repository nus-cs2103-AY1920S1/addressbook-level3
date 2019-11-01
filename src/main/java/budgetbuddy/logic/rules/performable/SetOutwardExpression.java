package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set outward expression.
 */
public class SetOutwardExpression extends PerformableExpression {

    /**
     * Constructs a SetOutwardExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetOutwardExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn, Account account) {
        requireAllNonNull(model, model.getAccountsManager(), txn);

        AccountsManager accountsManager = model.getAccountsManager();
        Direction updatedDirection = Direction.OUT;

        Transaction updatedTransaction = new Transaction(txn.getDate(), txn.getAmount(), updatedDirection,
                txn.getDescription(), txn.getCategories());

        accountsManager.getActiveAccount().deleteTransaction(txn);
        accountsManager.getAccount(account.getName()).addTransaction(updatedTransaction);

        logger.info("Rule Execution———Direction updated in " + updatedTransaction);
    }
}
