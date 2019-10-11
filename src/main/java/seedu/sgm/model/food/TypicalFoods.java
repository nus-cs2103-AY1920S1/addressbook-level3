package seedu.sgm.model.food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalFoods {

    public static final Food BROCCOLI = new FoodBuilder().build();
    public static final Food POTATO = new FoodBuilder().withFoodName("Potato").withFoodType("sv").build();
    public static final Food BANANA = new FoodBuilder().withFoodName("Banana").withFoodType("f").build();
    public static final Food CHICKEN = new FoodBuilder().withFoodName("Chicken").withFoodType("p").build();
    public static final Food POTATO_CHIPS = new FoodBuilder().withFoodName("Potato chips").withFoodType("s").build();
    public static final Food CHICKEN_RICE = new FoodBuilder().withFoodName("Chicken Rice").withFoodType("m").build();
    public static final List<Food> foods = new ArrayList<>(Arrays.asList(new Food[]{BROCCOLI, POTATO, BANANA, CHICKEN,
        POTATO_CHIPS, CHICKEN_RICE}));

}
