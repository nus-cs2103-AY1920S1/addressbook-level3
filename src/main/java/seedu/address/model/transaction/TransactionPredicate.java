package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.category.Category;

/**
 * Tests that a {@code BankAccountOperation} matches the {@code keyCategories}, {@code month}, {@code year}
 * and {@code description} given.
 */
public class TransactionPredicate implements Predicate<BankAccountOperation> {

    private final Optional<List<Category>> keyCategories;
    private final Optional<Integer> month;
    private final Optional<Integer> year;
    private final Optional<Description> description;

    public TransactionPredicate(Optional<Set<Category>> keyCategories,
                                Optional<Integer> month,
                                Optional<Integer> year,
                                Optional<Description> description) {
        requireAllNonNull(keyCategories, month, year, description);
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
        requireNonNull(transaction);
        return testForCategories(transaction)
            && testForMonth(transaction)
            && testForYear(transaction)
            && testForDescription(transaction);
    }

    /**
     * Checks if at least one of the categories in {@code keyCategories} is in {@code transaction}.
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
     * Checks if {@code transaction} has {@code description}.
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
            || (other instanceof TransactionPredicate // instanceof handles nulls
            && keyCategories.equals(((TransactionPredicate) other).keyCategories))
            && month.equals(((TransactionPredicate) other).month)
            && year.equals(((TransactionPredicate) other).year)
            && description.equals(((TransactionPredicate) other).description); // state check
    }
}
