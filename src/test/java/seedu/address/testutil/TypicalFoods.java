package seedu.address.testutil;

import seedu.sgm.model.food.Food;

public class TypicalFoods {

    public static final Food BROCCOLI = new FoodBuilder().build();
    public static final Food POTATO = new FoodBuilder().withFoodName("Potato").withFoodType("sv").build();
    public static final Food Banana = new FoodBuilder().withFoodName("Banana").withFoodType("f").build();
    public static final Food Chicken = new FoodBuilder().withFoodName("Chicken").withFoodType("p").build();
    public static final Food PotatoChips = new FoodBuilder().withFoodName("Potato chips").withFoodType("s").build();
    public static final Food ChickenRice = new FoodBuilder().withFoodName("Chicken Rice").withFoodType("m").build();

}
