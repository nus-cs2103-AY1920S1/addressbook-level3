package seedu.guilttrip.model.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.TypicalEntries.FOOD_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.SALARY_INCOME;
import static seedu.guilttrip.testutil.TypicalEntries.TRAVEL_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.testutil.ExpenseBuilder;

public class EntryContainsCategoryPredicateTest {

    @Test
    public void equals() {
        Category firstPredicateCategory = TRAVEL_EXPENSE.getCategory();
        Category secondPredicateCategory = SALARY_INCOME.getCategory();

        EntryContainsCategoryPredicate firstPredicate =
                new EntryContainsCategoryPredicate(firstPredicateCategory);
        EntryContainsCategoryPredicate secondPredicate =
                new EntryContainsCategoryPredicate(secondPredicateCategory);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntryContainsCategoryPredicate firstPredicateCopy =
                new EntryContainsCategoryPredicate(firstPredicateCategory);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("1"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different entry -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categorySameCategory_returnsTrue() {
        Category firstPredicateCategory = TRAVEL_EXPENSE.getCategory();
        // Contains Same Category
        EntryContainsCategoryPredicate firstPredicate =
                new EntryContainsCategoryPredicate(firstPredicateCategory);
        assertTrue(firstPredicate.test(new ExpenseBuilder().withCategory(firstPredicateCategory.getCategoryName())
                .build()));
    }

    @Test
    public void test_categoryNotSameCategory_returnsFalse() {
        Category firstPredicateCategory = TRAVEL_EXPENSE.getCategory();
        // Contains Different Category
        EntryContainsCategoryPredicate firstPredicate =
                new EntryContainsCategoryPredicate(firstPredicateCategory);
        assertFalse(firstPredicate.test(new ExpenseBuilder().withCategory(FOOD_EXPENSE.getCategory().getCategoryName())
                .build()));
    }
}
