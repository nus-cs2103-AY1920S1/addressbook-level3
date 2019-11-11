package seedu.sugarmummy.logic.parser.recmf;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_GI;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SUGAR;

import seedu.sugarmummy.logic.commands.recmf.AddFoodCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.recmf.Calorie;
import seedu.sugarmummy.model.recmf.Fat;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodName;
import seedu.sugarmummy.model.recmf.FoodType;
import seedu.sugarmummy.model.recmf.Gi;
import seedu.sugarmummy.model.recmf.NutritionValue;
import seedu.sugarmummy.model.recmf.Sugar;
import seedu.sugarmummy.model.recmf.exceptions.FoodNotSuitableException;

/**
 * Parses input arguments and creates a new AddFoodCommand object.
 */
public class AddFoodCommandParser implements Parser<AddFoodCommand> {

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

        if (Parser.arePrefixesPresent(argMultimap,
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
