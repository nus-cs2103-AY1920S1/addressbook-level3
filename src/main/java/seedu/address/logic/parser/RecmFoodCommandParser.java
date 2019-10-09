package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;

import java.util.Arrays;

import seedu.address.logic.commands.RecmFoodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.sgm.model.food.FoodNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new RecmFoodCommand object
 */
public class RecmFoodCommandParser implements Parser<RecmFoodCommand> {

    @Override
    public RecmFoodCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_FOOD);
        String[] flags = argumentMultimap.getPreamble().split("\\s+");
        String[] foodKeywords = argumentMultimap.getValue(PREFIX_FOOD).orElse("").trim().split("\\s+");
        return new RecmFoodCommand(new FoodNameContainsKeywordsPredicate(Arrays.asList(foodKeywords)));
    }
}
