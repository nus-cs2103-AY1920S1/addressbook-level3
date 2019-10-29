package sugarmummy.recmfood.storage;

import static seedu.address.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import sugarmummy.recmfood.model.Calorie;
import sugarmummy.recmfood.model.Fat;
import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.FoodName;
import sugarmummy.recmfood.model.FoodType;
import sugarmummy.recmfood.model.Gi;
import sugarmummy.recmfood.model.Sugar;

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
        foodType = source.getFoodType().getAbbr();
        calorie = source.getCalorie().value;
        gi = source.getGi().value;
        sugar = source.getSugar().value;
        fat = source.getFat().value;
    }

    private void checkIsNull(String input, String className) throws IllegalValueException {
        if (input == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, className));
        }
    }

    /**
     * Converts this Jackson-friendly adapted food object into the sugarmummy.recmfood.model's {@code food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Food toModelType() throws IllegalValueException {

        checkIsNull(foodName, FoodName.class.getSimpleName());
        checkArgument(FoodName.isValidName(foodName), FoodName.MESSAGE_CONSTRAINTS);
        final FoodName modelFoodName = new FoodName(foodName);

        checkIsNull(foodType, FoodType.class.getSimpleName());
        checkArgument(FoodType.isValidType(foodType), FoodType.MESSAGE_CONSTRAINTS);
        final FoodType modelFoodType = FoodType.getFrom(foodType);

        checkIsNull(calorie, Calorie.class.getSimpleName());
        checkArgument(Calorie.isValidValue(calorie), Calorie.MESSAGE_CONSTRAINTS);
        final Calorie modelCalorie = new Calorie(calorie);

        checkIsNull(gi, Gi.class.getSimpleName());
        checkArgument(Gi.isValidValue(gi), Gi.MESSAGE_CONSTRAINTS);
        final Gi modelGi = new Gi(gi);

        checkIsNull(sugar, Sugar.class.getSimpleName());
        checkArgument(Sugar.isValidValue(sugar), Sugar.MESSAGE_CONSTRAINTS);
        final Sugar modelSugar = new Sugar(sugar);

        checkIsNull(fat, Fat.class.getSimpleName());
        checkArgument(Fat.isValidValue(fat), Fat.MESSAGE_CONSTRAINTS);
        final Fat modelFat = new Fat(fat);

        return new Food(modelFoodName, modelCalorie, modelGi, modelSugar, modelFat, modelFoodType);
    }

}
