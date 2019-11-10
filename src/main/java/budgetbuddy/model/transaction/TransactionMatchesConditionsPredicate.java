package budgetbuddy.model.transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;

/**
 * Tests that {@code Transaction}'s attributes matches any of the attributes given.
 */
public class TransactionMatchesConditionsPredicate implements Predicate<Transaction> {
    private final List<Category> categoryList;
    private final Optional<LocalDate> fromOptional;
    private final Optional<LocalDate> untilOptional;
    private final Optional<Amount> fromAmtOptional;
    private final Optional<Amount> untilAmtOptional;
    private final Optional<Description> descriptionOptional;

    public TransactionMatchesConditionsPredicate(List<Category> categoryList,
                                                 Optional<LocalDate> fromOptional,
                                                 Optional<LocalDate> untilOptional,
                                                 Optional<Amount> fromAmtOptional,
                                                 Optional<Amount> untilAmtOptional,
                                                 Optional<Description> descriptionOptional) {
        this.categoryList = categoryList;
        this.fromOptional = fromOptional;
        this.untilOptional = untilOptional;
        this.fromAmtOptional = fromAmtOptional;
        this.untilAmtOptional = untilAmtOptional;
        this.descriptionOptional = descriptionOptional;
    }

    @Override
    public boolean test(Transaction transaction) {
        if (!categoryList.isEmpty()) {
            if (!transaction.getCategories().stream().anyMatch(categoryList::contains)) {
                return false;
            }
        }
        if (fromOptional.isPresent()) {
            //isBefore is used here to include the specified day
            if (transaction.getLocalDate().isBefore(fromOptional.get())) {
                return false;
            }
        }
        if (untilOptional.isPresent()) {
            //isAfter is used here to include the specified day
            if (transaction.getLocalDate().isAfter(untilOptional.get())) {
                return false;
            }
        }
        if (fromAmtOptional.isPresent()) {
            if (!transaction.getAmount().moreThanEquals(fromAmtOptional.get())) {
                return false;
            }
        }
        if (untilAmtOptional.isPresent()) {
            if (!transaction.getAmount().lessThanEquals(untilAmtOptional.get())) {
                return false;
            }
        }
        if (descriptionOptional.isPresent()) {
            if (!transaction.getDescription().contains(descriptionOptional.get())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionMatchesConditionsPredicate // instanceof handles nulls
                && categoryList.equals(((TransactionMatchesConditionsPredicate) other).categoryList)
                && fromOptional.equals(((TransactionMatchesConditionsPredicate) other).fromOptional)
                && untilOptional.equals(((TransactionMatchesConditionsPredicate) other).untilOptional)
                && fromAmtOptional.equals((((TransactionMatchesConditionsPredicate) other).fromAmtOptional))
                && untilAmtOptional.equals((((TransactionMatchesConditionsPredicate) other).untilAmtOptional))
                && descriptionOptional.equals((((TransactionMatchesConditionsPredicate) other).descriptionOptional))); // state check
    }
}
