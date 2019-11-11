package seedu.sugarmummy.testutil.recmf;

import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_GI;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SUGAR;
import static seedu.sugarmummy.testutil.recmf.FoodBuilder.DEFAULT_CALORIE;
import static seedu.sugarmummy.testutil.recmf.FoodBuilder.DEFAULT_FAT;
import static seedu.sugarmummy.testutil.recmf.FoodBuilder.DEFAULT_GI;
import static seedu.sugarmummy.testutil.recmf.FoodBuilder.DEFAULT_SUGAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.sugarmummy.model.recmf.Food;

/**
 * This is a utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFoods {

    public static final String WHITESPACE = " ";
    public static final String NAME_DESC_CHICKEN = PREFIX_FOOD_NAME + "Chicken" + WHITESPACE;
    public static final String TYPE_DESC_CHICKEN = PREFIX_FOOD_TYPE + "p" + WHITESPACE;
    public static final String CALORIE_DESC_CHICKEN = PREFIX_CALORIE + DEFAULT_CALORIE + WHITESPACE;
    public static final String GI_DESC_CHICKEN = PREFIX_GI + DEFAULT_GI + WHITESPACE;
    public static final String SUGAR_DESC_CHICKEN = PREFIX_SUGAR + DEFAULT_SUGAR + WHITESPACE;
    public static final String FAT_DESC_CHICKEN = PREFIX_FAT + DEFAULT_FAT + WHITESPACE;

    public static final String NAME_DESC_CORN = PREFIX_FOOD_NAME + "Corn" + WHITESPACE;
    public static final String TYPE_DESC_CORN = PREFIX_FOOD_TYPE + "sv" + WHITESPACE;
    public static final String CALORIE_DESC_CORN = PREFIX_CALORIE + DEFAULT_CALORIE + WHITESPACE;
    public static final String GI_DESC_CORN = PREFIX_GI + DEFAULT_GI + WHITESPACE;
    public static final String SUGAR_DESC_CORN = PREFIX_SUGAR + DEFAULT_SUGAR + WHITESPACE;
    public static final String FAT_DESC_CORN = PREFIX_FAT + DEFAULT_FAT + WHITESPACE;


    public static final Food BROCCOLI = new FoodBuilder().withFoodName("Broccoli").withFoodType("nsv").build();
    public static final Food POTATO = new FoodBuilder().withFoodName("Potato").withFoodType("sv").build();
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

    public static final List<Food> FOODS = new ArrayList<>(Arrays.asList(BROCCOLI, POTATO, BANANA, CHICKEN,
            POTATO_CHIPS, CHICKEN_RICE, BROCCOLI2, POTATO2, BANANA2, CHICKEN2, POTATO_CHIPS2, CHICKEN_RICE2, BROCCOLI3,
            POTATO3, BANANA3, CHICKEN3, POTATO_CHIPS3, CHICKEN_RICE3));

}
