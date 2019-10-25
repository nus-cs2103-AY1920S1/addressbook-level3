package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.model.property.PropertyBook.getCustomProperties;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import seedu.exercise.logic.commands.SuggestBasicCommand;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.Muscle;

/**
 * Parses input arguments and creates a new SuggestCommand object
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    public static final String SUGGEST_TYPE_BASIC = "basic";
    public static final String SUGGEST_TYPE_POSSIBLE = "possible";

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestCommand
     * and returns a SuggestCommand object for execution.
     *
     * @throws ParseException if the user does not conform to the expected format
     */
    public SuggestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, getPrefixes());

        if (!argMultimap.arePrefixesPresent(PREFIX_SUGGEST_TYPE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
        }

        String suggestType = ParserUtil.parseSuggestType(argMultimap.getValue(PREFIX_SUGGEST_TYPE).get());

        if (suggestType.equals(SUGGEST_TYPE_BASIC)) {
            return new SuggestBasicCommand();
        }

        if (suggestType.equals(SUGGEST_TYPE_POSSIBLE)) {
            return parsePossible(argMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
    }

    private Prefix[] getPrefixes() {
        ArrayList<Prefix> prefixes = new ArrayList<>();
        prefixes.add(PREFIX_SUGGEST_TYPE);
        prefixes.add(PREFIX_MUSCLE);
        for (CustomProperty cp : getCustomProperties()) {
            prefixes.add(cp.getPrefix());
        }
        return prefixes.toArray(new Prefix[prefixes.size() - 1]);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    //private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
    //    return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    //}

    /**
     * Parses arguments and returns SuggestPossibleCommand for execution
     */
    private static SuggestCommand parsePossible(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SuggestCommand.MESSAGE_USAGE));
        }
        Set<Muscle> muscles = ParserUtil.parseMuscles(argMultimap.getAllValues(PREFIX_MUSCLE));
        Map<String, String> customPropertiesMap =
                ParserUtil.parseCustomProperties(argMultimap.getAllCustomProperties());
        return new SuggestPossibleCommand(muscles, customPropertiesMap);
    }

}
