package seedu.address.model.transaction;

import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.util.Date;

/**
 * Interface to differentiate between operations on BankAccount and Ledger
 */
public interface BankAccountOperation {
    Description getDescription();

    Amount handleBalance(Amount balance);

    Amount getAmount();

    Set<Category> getCategories();

    boolean isSameTransaction(BankAccountOperation transaction);

    Date getDate();

    public boolean isGeneral();
}
