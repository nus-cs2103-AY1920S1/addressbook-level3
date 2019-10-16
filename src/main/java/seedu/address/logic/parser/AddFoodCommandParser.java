package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VALUE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddFoodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.sgm.model.food.Calorie;
import seedu.sgm.model.food.Fat;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodName;
import seedu.sgm.model.food.FoodType;
import seedu.sgm.model.food.Gi;
import seedu.sgm.model.food.Sugar;

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
     * Parses the given {@code String} of arguments in the context of the AddFoodCommand and returns an AddFoodCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFoodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FOOD, PREFIX_FOOD_TYPE, PREFIX_VALUE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FOOD, PREFIX_FOOD_TYPE, PREFIX_VALUE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
        }

        FoodName name = ParserUtil.parseFoodName(argMultimap.getValue(PREFIX_FOOD).get());
        //TODO: Throw exception
        FoodType foodType = FoodType.getFrom(argMultimap.getValue(PREFIX_FOOD_TYPE).get());
        String[] values = argMultimap.getValue(PREFIX_VALUE).get().trim().split(" ");
        Calorie calorie = new Calorie(values[0]);
        Gi gi = new Gi(values[1]);
        Sugar sugar = new Sugar(values[2]);
        Fat fat = new Fat(values[3]);
        Food newFood = new Food(name, calorie, gi, sugar, fat, foodType);

        return new AddFoodCommand(newFood);
    }

}
