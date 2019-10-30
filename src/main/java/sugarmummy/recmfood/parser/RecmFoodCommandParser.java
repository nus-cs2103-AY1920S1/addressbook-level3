package sugarmummy.recmfood.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import sugarmummy.recmfood.commands.RecmFoodCommand;
import sugarmummy.recmfood.model.FoodNameContainsKeywordsPredicate;
import sugarmummy.recmfood.model.FoodType;
import sugarmummy.recmfood.model.FoodTypeIsWantedPredicate;

/**
 * Parses input arguments and creates a new RecmFoodCommand object
 */
public class RecmFoodCommandParser implements Parser<RecmFoodCommand> {

    @Override
    public RecmFoodCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_FOOD);
        List<FoodType> foodTypes = getWantedFoodTypes(argumentMultimap.getPreamble());
        Optional<String> foodWords = argumentMultimap.getValue(PREFIX_FOOD);

        if (foodWords.isEmpty()) {
            return new RecmFoodCommand(new FoodTypeIsWantedPredicate(foodTypes), x -> true);
        }

        String[] foodKeywords = foodWords.get().trim().split("\\s+");
        return new RecmFoodCommand(new FoodTypeIsWantedPredicate(foodTypes),
                new FoodNameContainsKeywordsPredicate(Arrays.asList(foodKeywords)));
    }

    /**
     * Returns a list of specified food types, or all food types if no specification.
     *
     * @throws ParseException if one or more food types are invalid.
     */
    private List<FoodType> getWantedFoodTypes(String preamble) throws ParseException {

        String[] typeStrings = preamble.split("\\s+");

        /*If no flag entered, return all flags to show the full list.*/
        if (preamble.length() == 0) {
            return Arrays.asList(FoodType.values());
        }

        List<FoodType> validTypes = new ArrayList<>();

        for (String typeString : typeStrings) {
            if (FoodType.isValidType(typeString)) {
                validTypes.add(FoodType.getFrom(typeString));
            }
        }

        int inputTypeNum = typeStrings.length;
        if (inputTypeNum == validTypes.size()) {
            return validTypes;
        } else {
            throw new ParseException("One or more food types are invalid.");
        }
    }
}
