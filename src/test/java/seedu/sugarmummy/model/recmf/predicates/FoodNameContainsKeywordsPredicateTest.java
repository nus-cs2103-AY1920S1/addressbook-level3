package seedu.sugarmummy.model.recmf.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.BANANA;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.BROCCOLI;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.CHICKEN;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.FOODS;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.POTATO;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class FoodNameContainsKeywordsPredicateTest {

    private FoodNameContainsKeywordsPredicate predicateWithEmptyList =
            new FoodNameContainsKeywordsPredicate(new ArrayList<>());
    private FoodNameContainsKeywordsPredicate predicateWithNormalList =
            new FoodNameContainsKeywordsPredicate(Arrays.asList("Chicken", "Broccoli"));

    @Test
    void test_foodsWithWantedName_returnsTrue() {
        assertTrue(predicateWithNormalList.test(CHICKEN));
        assertTrue(predicateWithNormalList.test(BROCCOLI));
    }

    @Test
    void test_foodsWithNotWantedName_returnsFalse() {
        assertFalse(predicateWithNormalList.test(POTATO));
        assertFalse(predicateWithNormalList.test(BANANA));
    }

    @Test
    void test_emptyTypeList_returnsTrue() {
        assertTrue(predicateWithEmptyList.test(POTATO));
        assertTrue(predicateWithEmptyList.test(BANANA));

        assertTrue(FOODS.stream().allMatch(food -> predicateWithEmptyList.test(food)));
    }
}
