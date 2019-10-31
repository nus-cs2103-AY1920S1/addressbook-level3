package sugarmummy.recmfood.parser;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import sugarmummy.recmfood.commands.RecmFoodCommand;
import sugarmummy.recmfood.model.Flag;
import sugarmummy.recmfood.model.FoodType;
import sugarmummy.recmfood.predicates.FoodNameContainsKeywordsPredicate;
import sugarmummy.recmfood.predicates.FoodTypeIsWantedPredicate;

/**
 * Parses input arguments and creates a new RecmFoodCommand object
 */
public class RecmFoodCommandParser implements Parser<RecmFoodCommand> {

    @Override
    public RecmFoodCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_FOOD_NAME);
        String flagsString = argumentMultimap.getPreamble().trim();
        String foodWordsString = argumentMultimap.getValue(PREFIX_FOOD_NAME).orElse("");

        Set<FoodType> foodTypes = flagsString.length() == 0 ? new HashSet<>() : getWantedFoodTypes(flagsString);
        List<String> foodKeywords =
                foodWordsString.length() == 0 ? new ArrayList<>() : getWantedFoodKeywords(foodWordsString);

        return new RecmFoodCommand(new FoodTypeIsWantedPredicate(foodTypes),
                new FoodNameContainsKeywordsPredicate(foodKeywords));
    }

    /**
     * Returns a list of specified food types, or all food types if no specification.
     *
     * @throws ParseException if one or more food types are invalid.
     */
    private Set<FoodType> getWantedFoodTypes(String flagsString) throws ParseException {

        Set<FoodType> validTypes = new HashSet<>();
        String[] flagStrings = flagsString.split("\\s+");

        if (Arrays.stream(flagStrings).anyMatch(str -> !Flag.isValidFlag(str))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecmFoodCommand.MESSAGE_USAGE));
        }

        List<String> uniqueTypeStrings = Arrays.stream(flagStrings).map(str -> new Flag(str))
                .map(flag -> flag.getFlagContent())
                .distinct()
                .collect(Collectors.toList());

        for (String typeString : uniqueTypeStrings) {
            if (FoodType.isValidType(typeString)) {
                validTypes.add(FoodType.getFrom(typeString));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecmFoodCommand.MESSAGE_USAGE));
            }
        }
        return validTypes;
    }

    private List<String> getWantedFoodKeywords(String foodWords) {
        return Arrays.asList(foodWords.trim().split("\\s+"));
    }
}
