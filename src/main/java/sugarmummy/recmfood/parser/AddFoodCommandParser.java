package sugarmummy.recmfood.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GI;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUGAR;

import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import sugarmummy.recmfood.commands.AddFoodCommand;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_FOOD_NAME, PREFIX_FOOD_TYPE, PREFIX_CALORIE, PREFIX_GI, PREFIX_SUGAR, PREFIX_FAT);

        if (arePrefixesPresent(argMultimap,
                PREFIX_FOOD_NAME, PREFIX_FOOD_TYPE, PREFIX_CALORIE, PREFIX_GI, PREFIX_SUGAR, PREFIX_FAT)
                && argMultimap.getPreamble().isEmpty()) {

            FoodName name = RecmFoodParserUtil.parseFoodName(argMultimap.getValue(PREFIX_FOOD_NAME).get());
            FoodType type = RecmFoodParserUtil.parseFoodType(argMultimap.getValue(PREFIX_FOOD_TYPE).get());
            Calorie calorie = RecmFoodParserUtil.parseCalorieValue(argMultimap.getValue(PREFIX_CALORIE).get());
            Gi gi = RecmFoodParserUtil.parseGiValue(argMultimap.getValue(PREFIX_GI).get());
            Sugar sugar = RecmFoodParserUtil.parseSugarValue(argMultimap.getValue(PREFIX_SUGAR).get());
            Fat fat = RecmFoodParserUtil.parseFatValue(argMultimap.getValue(PREFIX_FAT).get());

            checkValueRange(calorie, gi, sugar, fat);

            return new AddFoodCommand(new Food(name, calorie, gi, sugar, fat, type));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
        }


    }

}
