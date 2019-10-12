package seedu.sgm.model.food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalFoods {

    public static final Food BROCCOLI = new FoodBuilder().build();
    public static final Food POTATO = new FoodBuilder().withFoodName("Potatodfafadfafadf").withFoodType("sv").build();
    public static final Food BANANA = new FoodBuilder().withFoodName("Banana").withFoodType("f").build();
    public static final Food CHICKEN = new FoodBuilder().withFoodName("Chicken").withFoodType("p").build();
    public static final Food POTATO_CHIPS = new FoodBuilder().withFoodName("Potato chips").withFoodType("s").build();
    public static final Food CHICKEN_RICE = new FoodBuilder().withFoodName("Chicken Rice").withFoodType("m").build();
    public static final Food BROCCOLI2 = new FoodBuilder().withFoodName("Broccoli2").build();
    public static final Food POTATO2 = new FoodBuilder().withFoodName("Potato2").withFoodType("sv").build();
    public static final Food BANANA2 = new FoodBuilder().withFoodName("Banana2").withFoodType("f").build();
    public static final Food CHICKEN2 = new FoodBuilder().withFoodName("Chicken2").withFoodType("p").build();
    public static final Food POTATO_CHIPS2 = new FoodBuilder().withFoodName("Potato chips2").withFoodType("s").build();
    public static final Food CHICKEN_RICE2 = new FoodBuilder().withFoodName("Chicken Rice2").withFoodType("m").build();
    public static final Food BROCCOLI3 = new FoodBuilder().withFoodName("Broccoli3").build();
    public static final Food POTATO3 = new FoodBuilder().withFoodName("Potato3").withFoodType("sv").build();
    public static final Food BANANA3 = new FoodBuilder().withFoodName("Banana3").withFoodType("f").build();
    public static final Food CHICKEN3 = new FoodBuilder().withFoodName("Chicken3").withFoodType("p").build();
    public static final Food POTATO_CHIPS3 = new FoodBuilder().withFoodName("Potato chips3").withFoodType("s").build();
    public static final Food CHICKEN_RICE3 = new FoodBuilder().withFoodName("Chicken Rice3").withFoodType("m").build();
    public static final List<Food> foods = new ArrayList<>(Arrays.asList(new Food[]{BROCCOLI, POTATO, BANANA, CHICKEN,
        POTATO_CHIPS, CHICKEN_RICE, BROCCOLI2, POTATO2, BANANA2, CHICKEN2, POTATO_CHIPS2, CHICKEN_RICE2, BROCCOLI3,
        POTATO3, BANANA3, CHICKEN3, POTATO_CHIPS3, CHICKEN_RICE3}));

}
