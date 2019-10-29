package sugarmummy.recmfood.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddFoodCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import sugarmummy.recmfood.exception.FoodNotSuitableException;
import sugarmummy.recmfood.model.Calorie;
import sugarmummy.recmfood.model.Fat;
import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.FoodName;
import sugarmummy.recmfood.model.FoodType;
import sugarmummy.recmfood.model.Gi;
import sugarmummy.recmfood.model.NutritionValue;
import sugarmummy.recmfood.model.Sugar;

/**
 * Parses input arguments and creates a new AddFoodCommand object
 */
public class AddFoodCommandParser implements Parser<AddFoodCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks whether the input food has suitable calorie, gi, sugar, and fat values for type II diabetes patients.
     *
     * @throws FoodNotSuitableException if any of the nutrition value is in dangerous range.
     */
    private void checkValueRange(NutritionValue... values) throws FoodNotSuitableException {
        for (NutritionValue value : values) {
            if (value.isInDangerousRange()) {
                throw new FoodNotSuitableException(value.getWarningMessage());
            }
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddFoodCommand and returns an AddFoodCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFoodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_FOOD,
            CliSyntax.PREFIX_FOOD_TYPE, CliSyntax.PREFIX_CALORIE,
            CliSyntax.PREFIX_GI, CliSyntax.PREFIX_SUGAR, CliSyntax.PREFIX_FAT);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_FOOD, CliSyntax.PREFIX_FOOD_TYPE,
            CliSyntax.PREFIX_CALORIE, CliSyntax.PREFIX_GI, CliSyntax.PREFIX_SUGAR,
            CliSyntax.PREFIX_FAT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
        }

        FoodName name = ParserUtil.parseFoodName(argMultimap.getValue(CliSyntax.PREFIX_FOOD).get());
        FoodType foodType = FoodType.getFrom(argMultimap.getValue(CliSyntax.PREFIX_FOOD_TYPE).get());

        Calorie calorie = ParserUtil.parseCalorieValue(argMultimap.getValue(CliSyntax.PREFIX_CALORIE).get());
        Gi gi = ParserUtil.parseGiValue(argMultimap.getValue(CliSyntax.PREFIX_GI).get());
        Sugar sugar = ParserUtil.parseSugarValue(argMultimap.getValue(CliSyntax.PREFIX_SUGAR).get());
        Fat fat = ParserUtil.parseFatValue(argMultimap.getValue(CliSyntax.PREFIX_FAT).get());

        checkValueRange(calorie, gi, sugar, fat);

        Food newFood = new Food(name, calorie, gi, sugar, fat, foodType);

        return new AddFoodCommand(newFood);
    }

}
