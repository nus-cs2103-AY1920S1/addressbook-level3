package seedu.address.model.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.category.Category;

/**
 * Tests that a {@code Transaction}'s {@code Category} matches the {@code Category} given.
 */
public class TransactionContainsCategoriesPredicate implements Predicate<BankAccountOperation> {

    private final List<Category> keyCategories;

    public TransactionContainsCategoriesPredicate(List<String> keyCategories) {
        this.keyCategories = new ArrayList<>();
        keyCategories.forEach(category -> this.keyCategories.add(new Category(category)));
    }

    @Override
    public boolean test(BankAccountOperation transaction) {
        return keyCategories.stream()
            .anyMatch(keyCategory -> isCategoryInsideCategories(transaction, keyCategory));
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
            && keyCategories.equals(((TransactionContainsCategoriesPredicate) other).keyCategories)); // state check
    }
}
