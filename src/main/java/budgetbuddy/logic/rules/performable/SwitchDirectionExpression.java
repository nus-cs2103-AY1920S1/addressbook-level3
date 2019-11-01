package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a switch direction expression.
 */
public class SwitchDirectionExpression extends PerformableExpression {

    /**
     * Constructs a SwitchDirectionExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SwitchDirectionExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn, Account account) {
        requireAllNonNull(model, model.getAccountsManager(), txn);

        AccountsManager accountsManager = model.getAccountsManager();
        Direction updatedDirection = txn.getDirection().equals(Direction.IN) ? Direction.OUT : Direction.IN;

        Transaction updatedTransaction = new Transaction(txn.getDate(), txn.getAmount(), updatedDirection,
                txn.getDescription(), txn.getCategories());

        accountsManager.getActiveAccount().deleteTransaction(txn);
        accountsManager.getAccount(account.getName()).addTransaction(updatedTransaction);

        logger.info("Rule Execution———Direction updated in " + updatedTransaction);
    }
}
