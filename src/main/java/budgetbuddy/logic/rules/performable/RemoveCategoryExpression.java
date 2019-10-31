package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a remove category expression.
 */
public class RemoveCategoryExpression extends PerformableExpression {

    /**
     * Constructs a RemoveCategoryExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public RemoveCategoryExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn, Account account) {
        requireAllNonNull(model, model.getAccountsManager(), txn);

        AccountsManager accountsManager = model.getAccountsManager();
        try {
            Set<Category> categories = new HashSet<>(txn.getCategories());

            Category categoryToRemove = CommandParserUtil.parseCategory(value.toString());
            if (!categories.contains(categoryToRemove)) {
                return;
            }

            categories.remove(categoryToRemove);
            Transaction updatedTransaction = new Transaction(txn.getDate(), txn.getAmount(), txn.getDirection(),
                    txn.getDescription(), categories);

            accountsManager.getActiveAccount().deleteTransaction(txn);
            accountsManager.getAccount(account.getName()).addTransaction(updatedTransaction);

            logger.info("Rule Execution———Category removed in " + updatedTransaction);
        } catch (ParseException e) {
            // Should not happen as value should be parsable by the time this method is called
            // but will exit without completing if it does happen.
        }
    }
}
