package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.sgm.model.food.Calorie;
import seedu.sgm.model.food.Fat;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodName;
import seedu.sgm.model.food.FoodType;
import seedu.sgm.model.food.Gi;
import seedu.sgm.model.food.Sugar;

/**
 * Represents Jackson-friendly version of {@link Food}.
 */
class JsonAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    private final String foodName;
    private final String foodType;
    private final String calorie;
    private final String gi;
    private final String sugar;
    private final String fat;

    /**
     * Constructs a {@code JsonAdaptedFood} with the given food details.
     */
    @JsonCreator
    public JsonAdaptedFood(@JsonProperty("foodName") String foodName, @JsonProperty("foodType") String foodType,
                           @JsonProperty("calorie") String calorie, @JsonProperty("gi") String gi,
                           @JsonProperty("sugar") String sugar, @JsonProperty("fat") String fat) {
        this.foodName = foodName;
        this.foodType = foodType;
        this.calorie = calorie;
        this.gi = gi;
        this.sugar = sugar;
        this.fat = fat;
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedFood(Food source) {
        foodName = source.getFoodName().foodName;
        foodType = source.getFoodType().getShortHand();
        calorie = source.getCalorie().value;
        gi = source.getGi().value;
        sugar = source.getSugar().value;
        fat = source.getFat().value;
    }

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Food toModelType() throws IllegalValueException {

        if (foodName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                FoodName.class.getSimpleName()));
        }
        if (!FoodName.isValidName(foodName)) {
            throw new IllegalValueException(FoodName.MESSAGE_CONSTRAINTS);
        }
        final FoodName modelFoodName = new FoodName(foodName);

        if (foodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                FoodType.class.getSimpleName()));
        }

        final FoodType modelFoodType;
        try {
            modelFoodType = FoodType.getFrom(foodType);
        } catch (ParseException pe) {
            throw new IllegalValueException(FoodType.MESSAGE_CONSTRAINTS);
        }

        if (calorie == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Calorie.class.getSimpleName()));
        }
        if (!Calorie.isValidValue(calorie)) {
            throw new IllegalValueException(Calorie.MESSAGE_CONSTRAINTS);
        }
        final Calorie modelCalorie = new Calorie(calorie);

        if (gi == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gi.class.getSimpleName()));
        }
        if (!Gi.isValidValue(gi)) {
            throw new IllegalValueException(Gi.MESSAGE_CONSTRAINTS);
        }
        final Gi modelGi = new Gi(gi);

        if (sugar == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sugar.class.getSimpleName()));
        }
        if (!Sugar.isValidValue(sugar)) {
            throw new IllegalValueException(Sugar.MESSAGE_CONSTRAINTS);
        }
        final Sugar modelSugar = new Sugar(sugar);

        if (fat == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Fat.class.getSimpleName()));
        }
        if (!Fat.isValidValue(fat)) {
            throw new IllegalValueException(Fat.MESSAGE_CONSTRAINTS);
        }
        final Fat modelFat = new Fat(fat);


        return new Food(modelFoodName, modelCalorie, modelGi, modelSugar, modelFat, modelFoodType);
    }

}
