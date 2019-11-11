package seedu.sugarmummy.model.recmf.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.recmf.TypicalFoods.FOODS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodType;
import seedu.sugarmummy.testutil.recmf.FoodBuilder;

class FoodTypeIsWantedPredicateTest {

    private static final Food NSV_FOOD = new FoodBuilder().withFoodType("nsv").build();
    private static final Food SV_FOOD = new FoodBuilder().withFoodType("sv").build();
    private static final Food P_FOOD = new FoodBuilder().withFoodType("p").build();
    private static final Food F_FOOD = new FoodBuilder().withFoodType("f").build();
    private static final Food S_FOOD = new FoodBuilder().withFoodType("s").build();
    private static final Food M_FOOD = new FoodBuilder().withFoodType("m").build();

    private Set<FoodType> types = new HashSet<>(Arrays.asList(FoodType.PROTEIN, FoodType.FRUIT, FoodType.MEAL));
    private FoodTypeIsWantedPredicate predicateWithEmptySet = new FoodTypeIsWantedPredicate(new HashSet<>());
    private FoodTypeIsWantedPredicate predicateWithNormalSet = new FoodTypeIsWantedPredicate(types);

    @Test
    void test_foodsWithWantedType_returnsTrue() {
        assertTrue(predicateWithNormalSet.test(P_FOOD));
        assertTrue(predicateWithNormalSet.test(F_FOOD));
        assertTrue(predicateWithNormalSet.test(M_FOOD));
    }

    @Test
    void test_foodsWithNotWantedType_returnsFalse() {
        assertFalse(predicateWithNormalSet.test(NSV_FOOD));
        assertFalse(predicateWithNormalSet.test(SV_FOOD));
        assertFalse(predicateWithNormalSet.test(S_FOOD));
    }

    @Test
    void test_emptyTypeList_returnsTrue() {
        assertTrue(predicateWithEmptySet.test(P_FOOD));
        assertTrue(predicateWithEmptySet.test(SV_FOOD));

        assertTrue(FOODS.stream().allMatch(food -> predicateWithEmptySet.test(food)));
    }
}
