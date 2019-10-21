package seedu.revision.model.answerable.predicates;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.category.Category;

/**
 * Tests that a {@code Answerable}'s {@code category} matches the category given.
 */
public class CategoryPredicate implements Predicate<Answerable> {

    private final Category category;

    public CategoryPredicate(Category category) {
        this.category = category;
    }


    @Override
    public boolean test(Answerable answerable) {
        return answerable.getCategories()
                         .stream()
                         .map(category -> category.categoryName)
                         .anyMatch(categoryName -> categoryName.equalsIgnoreCase(this.category.categoryName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryPredicate // instanceof handles nulls
                && category.equals(((CategoryPredicate) other).category)); // state check
    }
}

