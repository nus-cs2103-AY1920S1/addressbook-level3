package seedu.address.testutil;

import seedu.sgm.model.food.Calorie;
import seedu.sgm.model.food.Fat;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodName;
import seedu.sgm.model.food.FoodType;
import seedu.sgm.model.food.Gi;
import seedu.sgm.model.food.Sugar;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_FOOD_NAME = "Alice Pauline";
    public static final String DEFAULT_CALORIE = "34.0";
    public static final String DEFAULT_GI = "10.0";
    public static final String DEFAULT_SUGAR = "1.7";
    public static final String DEFAULT_FAT = "0.4";
    public static final String DEFAULT_TYPE = "svf";

    private FoodName foodName;
    private Calorie calorie;
    private Gi gi;
    private Sugar sugar;
    private Fat fat;
    private FoodType foodType;

    /**
     * Initializes the FoodBuilder with the default data.
     */
    public FoodBuilder() {
        this.foodName = new FoodName(DEFAULT_FOOD_NAME);
        this.calorie = new Calorie(DEFAULT_CALORIE);
        this.gi = new Gi(DEFAULT_GI);
        this.sugar = new Sugar(DEFAULT_SUGAR);
        this.fat = new Fat(DEFAULT_FAT);
        this.foodType = FoodType.getFrom(DEFAULT_TYPE);
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        this.foodName = foodToCopy.getFoodName();
        this.calorie = foodToCopy.getCalorie();
        this.gi = foodToCopy.getGi();
        this.sugar = foodToCopy.getSugar();
        this.fat = foodToCopy.getFat();
        this.foodType = foodToCopy.getFoodType();
    }

    /**
     * Sets the {@code FoodName} of the {@code Food} that we are building.
     */
    public FoodBuilder withFoodName(String foodName) {
        this.foodName = new FoodName(foodName);
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code Food} that we are building.
     */
    public FoodBuilder withCalorie(String calorie) {
        this.calorie = new Calorie(calorie);
        return this;
    }

    /**
     * Sets the {@code Gi} of the {@code Food} that we are building.
     */
    public FoodBuilder withGi(String gi) {
        this.gi = new Gi(gi);
        return this;
    }

    /**
     * Sets the {@code Sugar} of the {@code Food} that we are building.
     */
    public FoodBuilder withSugar(String sugar) {
        this.sugar = new Sugar(sugar);
        return this;
    }

    /**
     * Sets the {@code Fat} of the {@code Food} that we are building.
     */
    public FoodBuilder withFat(String fat) {
        this.fat = new Fat(fat);
        return this;
    }

    /**
     * Sets the {@code Fat} of the {@code Food} that we are building.
     */
    public FoodBuilder withFoodType(String foodType) {
        this.foodType = FoodType.getFrom(foodType);
        return this;
    }

    public Food build() {
        return new Food(foodName, calorie, gi, sugar, fat, foodType);
    }

}
