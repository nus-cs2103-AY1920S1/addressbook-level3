package calofit.logic.parser;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static calofit.logic.parser.CliSyntax.PREFIX_CALORIES;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import calofit.logic.commands.AddCommand;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAny(args);
        if (!argMultimap.getPreamble().isBlank()) {
            argMultimap.checkAllowedPrefixes(unknown -> AddCommand.MESSAGE_USAGE, new Prefix(""));
            String[] argsArr = argMultimap.getPreamble().split(" ");
            ArrayList<Integer> dishIntList = new ArrayList<>();
            if (argsArr.length == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            try {
                for (String dishIndexStr : argsArr) {
                    int dishNumber = Integer.parseInt(dishIndexStr);
                    dishIntList.add(dishNumber);
                }
            } catch (NumberFormatException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            return new AddCommand(dishIntList);
        } else {
            argMultimap.checkAllowedPrefixes(unknown -> AddCommand.MESSAGE_USAGE,
                PREFIX_NAME, PREFIX_CALORIES, PREFIX_TAG);
            if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                    || argMultimap.getAllValues(PREFIX_NAME).size() != 1
                    || argMultimap.getAllValues(PREFIX_CALORIES).size() > 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Calorie calories;
            if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
                calories = ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIES).get());
            } else {
                calories = Calorie.UNKNOWN_CALORIE;
            }

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Dish dish = new Dish(name, calories, tagList);
            return new AddCommand(dish);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
