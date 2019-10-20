package seedu.address.model.transaction;

import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.account.Account;
import seedu.address.model.attributes.Category;

/**
 * Tests that {@code Transaction}'s attributes matches any of the attributes given.
 */
public class TransactionMatchesConditionsPredicate implements Predicate<Transaction> {
    private final Optional<Account> accountOptional;
    private final Optional<Category> categoryOptional;
    private final Optional<Date> fromOptional;
    private final Optional<Date> untilOptional;

    public TransactionMatchesConditionsPredicate(Optional<Account> accountOptional,
                                                 Optional<Category> categoryOptional,
                                                 Optional<Date> fromOptional,
                                                 Optional<Date> untilOptional) {
        this.accountOptional = accountOptional;
        this.categoryOptional = categoryOptional;
        this.fromOptional = fromOptional;
        this.untilOptional = untilOptional;
    }

    @Override
    public boolean test(Transaction transaction) {
        if (accountOptional.isPresent()) {
            if (!transaction.getAccount().equals(accountOptional.get())) {
                return false;
            }
        }
        if (categoryOptional.isPresent()) {
            if (!transaction.getCategories().contains(categoryOptional.get())) {
                return false;
            }
        }
        if (fromOptional.isPresent()) {
            if (!transaction.getDate().after(fromOptional.get())) {
                return false;
            }
        }
        if (untilOptional.isPresent()) {
            if (!transaction.getDate().before(fromOptional.get())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionMatchesConditionsPredicate // instanceof handles nulls
                && accountOptional.equals(((TransactionMatchesConditionsPredicate) other).accountOptional)
                && categoryOptional.equals(((TransactionMatchesConditionsPredicate) other).categoryOptional)
                && fromOptional.equals(((TransactionMatchesConditionsPredicate) other).fromOptional)
                && untilOptional.equals(((TransactionMatchesConditionsPredicate) other).untilOptional)); // state check
    }
}
