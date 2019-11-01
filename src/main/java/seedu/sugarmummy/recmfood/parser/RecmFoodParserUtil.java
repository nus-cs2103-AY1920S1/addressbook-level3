package seedu.sugarmummy.recmfood.parser;

import static java.util.Objects.requireNonNull;

import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.recmfood.model.Calorie;
import seedu.sugarmummy.recmfood.model.Fat;
import seedu.sugarmummy.recmfood.model.FoodName;
import seedu.sugarmummy.recmfood.model.FoodType;
import seedu.sugarmummy.recmfood.model.Gi;
import seedu.sugarmummy.recmfood.model.NutritionValue;
import seedu.sugarmummy.recmfood.model.Sugar;

/**
 * Contains utility methods used for parsing strings in food recommendation related classes.
 */
public class RecmFoodParserUtil {

    /**
     * Parses a {@code String nameString} into a {@code FoodName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nameString} is invalid.
     */
    public static FoodName parseFoodName(String nameString) throws ParseException {
        requireNonNull(nameString);
        String trimmedNameString = nameString.trim();
        if (FoodName.isValidName(trimmedNameString)) {
            return new FoodName(trimmedNameString);
        } else {
            throw new ParseException(FoodName.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String typeString} into a {@code FoodType}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static FoodType parseFoodType(String typeString) throws ParseException {
        requireNonNull(typeString);
        String trimmedString = typeString.trim();
        if (FoodType.isValidType(trimmedString)) {
            return FoodType.getFrom(trimmedString);
        } else {
            throw new ParseException(FoodType.MESSAGE_CONSTRAINTS);
        }
    }


    private static String getVerifiedNutritionValue(String nutritionValueString) throws ParseException {
        requireNonNull(nutritionValueString);
        String trimmedValue = nutritionValueString.trim();
        if (NutritionValue.isValidValue(nutritionValueString)) {
            return trimmedValue;
        } else {
            throw new ParseException(NutritionValue.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String valueString} into a {@code Calorie}.
     */
    public static Calorie parseCalorieValue(String valueString) throws ParseException {
        return new Calorie(getVerifiedNutritionValue(valueString));
    }

    /**
     * Parses a {@code String valueString} into a {@code Gi}.
     */
    public static Gi parseGiValue(String valueString) throws ParseException {
        return new Gi(getVerifiedNutritionValue(valueString));
    }

    /**
     * Parses a {@code String valueString} into a {@code Sugar}.
     */
    public static Sugar parseSugarValue(String valueString) throws ParseException {
        return new Sugar(getVerifiedNutritionValue(valueString));
    }

    /**
     * Parses a {@code String value} into a {@code Fat}.
     */
    public static Fat parseFatValue(String valueString) throws ParseException {
        return new Fat(getVerifiedNutritionValue(valueString));
    }
}
