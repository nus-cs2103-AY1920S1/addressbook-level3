package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.RecmFoodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodNameContainsKeywordsPredicate;
import seedu.sgm.model.food.FoodType;
import seedu.sgm.model.food.FoodTypeIsWantedPredicate;

/**
 * Parses input arguments and creates a new RecmFoodCommand object
 */
public class RecmFoodCommandParser implements Parser<RecmFoodCommand> {

    @Override
    public RecmFoodCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_FOOD);
        List<FoodType> foodTypes = getWantedFoodTypes(getParsedFlags(argumentMultimap.getPreamble()));
        Optional<String> foodWords = argumentMultimap.getValue(PREFIX_FOOD);

        if (foodWords.isEmpty()) {
            return new RecmFoodCommand(new FoodTypeIsWantedPredicate(foodTypes), x->true);
        }

        String[] foodKeywords = foodWords.get().trim().split("\\s+");
        return new RecmFoodCommand(new FoodTypeIsWantedPredicate(foodTypes),
            new FoodNameContainsKeywordsPredicate(Arrays.asList(foodKeywords)));
    }

    private List<Flag> getParsedFlags(String preamble) throws ParseException {
        int expectedFlagNumber = preamble.split("\\s+").length;
        List<Flag> validFlags = Arrays.stream(preamble.split("\\s+"))
            .map(f -> new Flag(f))
            .filter(flag -> FLAGS.contains(flag))
            .collect(Collectors.toList());

        /*If no flag entered, return all flags to show the full list.*/
        if (preamble.length() == 0) {
            return FLAGS;
        } else if (expectedFlagNumber != validFlags.size()) {
            throw new ParseException("One or more flags are invalid.");
        } else {
            return validFlags;
        }
    }

    private List<FoodType> getWantedFoodTypes(List<Flag> flags) throws ParseException {

        List<FoodType> foodTypes = new ArrayList<>();
        for (Flag flag : flags) {
           foodTypes.add(FoodType.getFrom(flag.toString()));
        }
        return foodTypes;
    }
}
