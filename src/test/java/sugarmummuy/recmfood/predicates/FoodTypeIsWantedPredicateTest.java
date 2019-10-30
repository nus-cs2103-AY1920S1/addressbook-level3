package sugarmummuy.recmfood.predicates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.FoodType;
import sugarmummy.recmfood.predicates.FoodTypeIsWantedPredicate;
import sugarmummy.recmfood.testutil.FoodBuilder;

class FoodTypeIsWantedPredicateTest {

    private static final Food NSV_FOOD = new FoodBuilder().withFoodType("nsv").build();
    private static final Food SV_FOOD = new FoodBuilder().withFoodType("sv").build();
    private static final Food P_FOOD = new FoodBuilder().withFoodType("p").build();
    private static final Food F_FOOD = new FoodBuilder().withFoodType("f").build();
    private static final Food S_FOOD = new FoodBuilder().withFoodType("s").build();
    private static final Food M_FOOD = new FoodBuilder().withFoodType("m").build();

    private List<FoodType> types = Arrays.asList(FoodType.PROTEIN, FoodType.FRUIT, FoodType.MEAL);
    private FoodTypeIsWantedPredicate predicateWithEmptyList = new FoodTypeIsWantedPredicate(new ArrayList<>());
    private FoodTypeIsWantedPredicate predicateWithNormalList = new FoodTypeIsWantedPredicate(types);

    @Test
    void test_wantedTypeFoods_true() {
        assertTrue(predicateWithNormalList.test(P_FOOD));
        assertTrue(predicateWithNormalList.test(F_FOOD));
        assertTrue(predicateWithNormalList.test(M_FOOD));
    }

    @Test
    void test_notWantedTypeFoods_false() {
        assertFalse(predicateWithNormalList.test(NSV_FOOD));
        assertFalse(predicateWithNormalList.test(SV_FOOD));
        assertFalse(predicateWithNormalList.test(S_FOOD));
    }

    @Test
    void test_emptyTypeList_false() {
        assertFalse(predicateWithEmptyList.test(P_FOOD));
        assertFalse(predicateWithEmptyList.test(SV_FOOD));
    }
}
