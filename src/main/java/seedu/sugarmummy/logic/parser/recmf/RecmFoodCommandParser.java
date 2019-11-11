package seedu.sugarmummy.logic.parser.recmf;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SORT_ASC;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SORT_DES;
import static seedu.sugarmummy.model.recmf.FoodComparator.DEFAULT_SORT_ORDER_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.sugarmummy.logic.commands.recmf.RecmFoodCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.recmf.Flag;
import seedu.sugarmummy.model.recmf.FoodComparator;
import seedu.sugarmummy.model.recmf.FoodType;
import seedu.sugarmummy.model.recmf.predicates.FoodNameContainsKeywordsPredicate;
import seedu.sugarmummy.model.recmf.predicates.FoodTypeIsWantedPredicate;

/**
 * Parses input arguments and creates a new RecmFoodCommand object.
 */
public class RecmFoodCommandParser implements Parser<RecmFoodCommand> {

    @Override
    public RecmFoodCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_FOOD_NAME, PREFIX_SORT_ASC, PREFIX_SORT_DES);

        if (Parser.arePrefixesPresent(argumentMultimap, PREFIX_SORT_ASC, PREFIX_SORT_DES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FoodComparator.MESSAGE_PREFIX_CONSTRAINTS));
        }

        String flagsStr = argumentMultimap.getPreamble().trim();
        String namesStr = argumentMultimap.getValue(PREFIX_FOOD_NAME).orElse("");
        Optional<String> sortOrderAscStrOpt = argumentMultimap.getValue(PREFIX_SORT_ASC);
        Optional<String> sortOrderDesStrOpt = argumentMultimap.getValue(PREFIX_SORT_DES);

        Set<FoodType> foodTypes = flagsStr.length() == 0 ? new HashSet<>() : getWantedFoodTypes(flagsStr);
        List<String> foodKeywords = namesStr.length() == 0 ? new ArrayList<>() : getWantedFoodKeywords(namesStr);
        FoodComparator foodComparator = getComparator(sortOrderAscStrOpt, sortOrderDesStrOpt);

        return new RecmFoodCommand(new FoodTypeIsWantedPredicate(foodTypes),
                new FoodNameContainsKeywordsPredicate(foodKeywords), foodComparator);
    }

    /**
     * Returns a list of specified food types, or all food types if no specification.
     *
     * @throws ParseException if one or more food types are invalid.
     */
    private Set<FoodType> getWantedFoodTypes(String flagsStr) throws ParseException {

        Set<FoodType> validTypes = new HashSet<>();
        String[] flagStrs = flagsStr.split("\\s+");

        if (Arrays.stream(flagStrs).anyMatch(str -> !Flag.isValidFlag(str))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecmFoodCommand.MESSAGE_USAGE));
        }

        List<String> uniqueTypeStrs = Arrays.stream(flagStrs).map(str -> new Flag(str))
                .map(flag -> flag.getFlagContent())
                .distinct()
                .collect(Collectors.toList());

        for (String typeStr : uniqueTypeStrs) {
            if (FoodType.isValidType(typeStr)) {
                validTypes.add(FoodType.getFrom(typeStr));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecmFoodCommand.MESSAGE_USAGE));
            }
        }
        return validTypes;
    }

    private List<String> getWantedFoodKeywords(String foodWords) {
        return Arrays.asList(foodWords.trim().split("\\s+"));
    }

    private FoodComparator getComparator(Optional<String> ascStrOpt, Optional<String> desStrOpt) throws ParseException {

        try {
            if (ascStrOpt.isPresent()) {
                return new FoodComparator(ascStrOpt.get());
            } else if (desStrOpt.isPresent()) {
                return new FoodComparator(desStrOpt.get()).reversed();
            } else {
                return new FoodComparator(DEFAULT_SORT_ORDER_STRING);
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(FoodComparator.MESSAGE_CONSTRAINTS);
        }
    }
}
