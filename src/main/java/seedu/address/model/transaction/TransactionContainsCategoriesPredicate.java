package seedu.address.model.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.category.Category;

/**
 * Tests that a {@code Transaction}'s {@code Category} matches the {@code Category} given.
 */
public class TransactionContainsCategoriesPredicate implements Predicate<BankAccountOperation> {

    private final Optional<List<Category>> keyCategories;
    private final Optional<Integer> month;
    private final Optional<Integer> year;
    private final Optional<Description> description;

    public TransactionContainsCategoriesPredicate(Optional<Set<Category>> keyCategories,
                                                  Optional<Integer> month,
                                                  Optional<Integer> year,
                                                  Optional<Description> description) {
        if (keyCategories.isPresent()) {
            this.keyCategories = Optional.of(new ArrayList<>(keyCategories.get()));
        } else {
            this.keyCategories = Optional.empty();
        }

        this.month = month;
        this.year = year;
        this.description = description;
    }

    @Override
    public boolean test(BankAccountOperation transaction) {
        return testForCategories(transaction)
            && testForMonth(transaction)
            && testForYear(transaction)
            && testForDescription(transaction);
    }

    /**
     * Checks if at least of of the categories in {@code keyCategories} is in {@code transaction}.
     */
    private boolean testForCategories(BankAccountOperation transaction) {
        if (keyCategories.isPresent()) {
            return keyCategories
                .get()
                .stream()
                .anyMatch(keyCategory -> isCategoryInsideCategories(transaction, keyCategory));
        }

        return true;
    }

    /**
     *
     */
    private boolean testForDescription(BankAccountOperation transaction) {
        if (description.isPresent()) {
            return transaction.getDescription().equals(description.get());
        }

        return true;
    }

    /**
     * Checks if {@code transaction} occurred in {@code month}.
     */
    private boolean testForMonth(BankAccountOperation transaction) {
        if (month.isPresent()) {
            return transaction.getDate().isSameMonth(month.get());
        }

        return true;
    }

    /**
     * Checks if {@code transaction} occurred in {@code year}.
     */
    private boolean testForYear(BankAccountOperation transaction) {
        if (year.isPresent()) {
            return transaction.getDate().isSameYear(year.get());
        }

        return true;
    }

    /**
     * Checks if a {@code Category} exists in {@code Transaction}'s set of {@code Category}s.
     *
     * @param transaction {@code Transaction} to be checked.
     * @param keyCategory Category to be found in {@code Transaction}
     */
    private boolean isCategoryInsideCategories(BankAccountOperation transaction, Category keyCategory) {
        return transaction
            .getCategories()
            .stream()
            .anyMatch(category ->
                StringUtil.containsWordIgnoreCase(category.getCategoryName(), keyCategory.getCategoryName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TransactionContainsCategoriesPredicate // instanceof handles nulls
            && keyCategories.equals(((TransactionContainsCategoriesPredicate) other).keyCategories))
            && month.equals(((TransactionContainsCategoriesPredicate) other).month)
            && year.equals(((TransactionContainsCategoriesPredicate) other).year)
            && description.equals(((TransactionContainsCategoriesPredicate) other).description); // state check
    }
}
