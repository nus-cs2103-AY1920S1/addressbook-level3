package budgetbuddy.model.transaction;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import budgetbuddy.model.attributes.Category;

/**
 * Tests that {@code Transaction}'s attributes matches any of the attributes given.
 */
public class TransactionMatchesConditionsPredicate implements Predicate<Transaction> {
    private final Optional<Category> categoryOptional;
    private final Optional<LocalDate> fromOptional;
    private final Optional<LocalDate> untilOptional;

    public TransactionMatchesConditionsPredicate(Optional<Category> categoryOptional,
                                                 Optional<LocalDate> fromOptional,
                                                 Optional<LocalDate> untilOptional) {
        this.categoryOptional = categoryOptional;
        this.fromOptional = fromOptional;
        this.untilOptional = untilOptional;
    }

    @Override
    public boolean test(Transaction transaction) {
        if (categoryOptional.isPresent()) {
            if (!transaction.getCategories().contains(categoryOptional.get())) {
                return false;
            }
        }
        if (fromOptional.isPresent()) {
            if (!transaction.getLocalDate().isAfter(fromOptional.get())) {
                return false;
            }
        }
        if (untilOptional.isPresent()) {
            if (!transaction.getLocalDate().isBefore(fromOptional.get())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionMatchesConditionsPredicate // instanceof handles nulls
                && categoryOptional.equals(((TransactionMatchesConditionsPredicate) other).categoryOptional)
                && fromOptional.equals(((TransactionMatchesConditionsPredicate) other).fromOptional)
                && untilOptional.equals(((TransactionMatchesConditionsPredicate) other).untilOptional)); // state check
    }
}
