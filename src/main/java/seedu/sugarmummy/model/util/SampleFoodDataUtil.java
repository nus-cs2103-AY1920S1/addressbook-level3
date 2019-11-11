package seedu.sugarmummy.model.util;

import seedu.sugarmummy.model.recmf.Calorie;
import seedu.sugarmummy.model.recmf.Fat;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodName;
import seedu.sugarmummy.model.recmf.FoodType;
import seedu.sugarmummy.model.recmf.Gi;
import seedu.sugarmummy.model.recmf.Sugar;
import seedu.sugarmummy.model.recmf.UniqueFoodList;

/**
 * Contains utility methods for populating {@code UniqueFoodList} with sample data.
 */
public class SampleFoodDataUtil {

    private static Food buildNewFood(String foodName, String calorie, String gi, String sugar, String fat,
                                     FoodType foodType) {
        return new Food(new FoodName(foodName), new Calorie(calorie), new Gi(gi), new Sugar(sugar), new Fat(fat),
                foodType);
    }

    public static Food[] getSampleFoods() {

        return new Food[]{
                buildNewFood("Asparagus", "20", "8", "2", "0", FoodType.NON_STARCHY_VEGETABLE),
                buildNewFood("Broccoli", "45", "6", "2", "1", FoodType.NON_STARCHY_VEGETABLE),
                buildNewFood("Carrot", "30", "47", "5", "0", FoodType.NON_STARCHY_VEGETABLE),
                buildNewFood("Cabbage", "25", "6", "3", "0", FoodType.NON_STARCHY_VEGETABLE),
                buildNewFood("Tomato", "25", "6", "3", "0", FoodType.NON_STARCHY_VEGETABLE),
                buildNewFood("Mushroom", "20", "10", "0", "0", FoodType.NON_STARCHY_VEGETABLE),

                buildNewFood("Sweet Corn", "90", "52", "5", "4", FoodType.STARCHY_VEGETABLE),
                buildNewFood("Brown Rice", "216", "68", "0.4", "1.8", FoodType.STARCHY_VEGETABLE),
                buildNewFood("Buckwheat", "343", "34", "0", "3.4", FoodType.STARCHY_VEGETABLE),
                buildNewFood("Millet", "378", "44", "1.6", "4.3", FoodType.STARCHY_VEGETABLE),
                buildNewFood("Butternut Squash", "82", "51", "4", "0.2", FoodType.STARCHY_VEGETABLE),
                buildNewFood("Pumpkin", "30", "64", "3.2", "0.1", FoodType.STARCHY_VEGETABLE),

                buildNewFood("Apple", "90", "36", "12", "0", FoodType.FRUIT),
                buildNewFood("Cherry", "100", "22", "16", "0", FoodType.FRUIT),
                buildNewFood("Orange", "80", "43", "14", "0", FoodType.FRUIT),
                buildNewFood("Grapefruit", "60", "25", "11", "0", FoodType.FRUIT),
                buildNewFood("Peach", "60", "42", "13", "0.5", FoodType.FRUIT),
                buildNewFood("Pear", "100", "38", "16", "0", FoodType.FRUIT),
                buildNewFood("Plum", "70", "39", "16", "0", FoodType.FRUIT),

                buildNewFood("Kidney beans", "127", "24", "0.3", "0.5", FoodType.PROTEIN),
                buildNewFood("Lentils", "230", "30", "1.8", "0.8", FoodType.PROTEIN),
                buildNewFood("Egg", "75", "0", "0", "5", FoodType.PROTEIN),
                buildNewFood("Cheese", "106", "0", "0", "8.9", FoodType.PROTEIN),
                buildNewFood("Chicken", "143", "0", "0", "8.1", FoodType.PROTEIN),
                buildNewFood("Turkey", "149", "0", "0", "8.3", FoodType.PROTEIN),
                buildNewFood("Duck", "132", "0", "0", "5.9", FoodType.PROTEIN),
                buildNewFood("Lean Pork", "263", "0", "0", "21", FoodType.PROTEIN),
                buildNewFood("Lean Beef", "254", "0", "0", "20", FoodType.PROTEIN),
                buildNewFood("Lean Lamb", "282", "0", "0", "23.4", FoodType.PROTEIN),

                buildNewFood("Diet Yogurt", "154", "41", "17", "3.8", FoodType.SNACK),
                buildNewFood("Fat free Yogurt", "137", "41", "19", "0.5", FoodType.SNACK),
                buildNewFood("Oat Crispbread", "130", "49", "2", "9", FoodType.SNACK),
                buildNewFood("Ginger Nut Biscuit", "55", "37", "5", "2", FoodType.SNACK),
                buildNewFood("Fig Roll", "65", "53", "5.9", "1.5", FoodType.SNACK),

                buildNewFood("Spanish Omelet", "260", "40", "3", "10", FoodType.MEAL),
                buildNewFood("Beef Stew", "320", "40", "9", "7", FoodType.MEAL),
                buildNewFood("Two Cheese Pizza", "420", "40", "4", "10", FoodType.MEAL),
                buildNewFood("Rice with Chicken", "400", "40", "5", "7", FoodType.MEAL),
                buildNewFood("Avocado Tacos", "270", "40", "4", "8", FoodType.MEAL)
        };
    }

    public static UniqueFoodList getSampleFoodList() {
        UniqueFoodList sampleFl = new UniqueFoodList();
        for (Food sampleFood : getSampleFoods()) {
            sampleFl.add(sampleFood);
        }
        return sampleFl;
    }
}
