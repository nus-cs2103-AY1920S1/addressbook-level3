package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_OPERATION_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.logic.parser.ParserUtil.parsePredicate;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.SuggestBasicCommand;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.model.resource.Exercise;

/**
 * Parses input arguments and creates a new SuggestCommand object
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    public static final String SUGGEST_TYPE_BASIC = "basic";
    public static final String SUGGEST_TYPE_POSSIBLE = "possible";
    private static final Logger logger = LogsCenter.getLogger(SuggestCommandParser.class);

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
        prefixes.add(PREFIX_OPERATION_TYPE);
        prefixes.add(PREFIX_SUGGEST_TYPE);
        prefixes.add(PREFIX_MUSCLE);
        Set<CustomProperty> customProperties = PropertyBook.getInstance().getCustomProperties();
        for (CustomProperty cp : customProperties) {
            prefixes.add(cp.getPrefix());
        }
        return prefixes.toArray(new Prefix[prefixes.size() - 1]);
    }

    /**
     * Parses arguments and returns SuggestPossibleCommand for execution
     */
    private static SuggestCommand parsePossible(ArgumentMultimap argMultimap) throws ParseException {
        Set<Muscle> muscles = ParserUtil.parseMuscles(argMultimap.getAllValues(PREFIX_MUSCLE));
        Map<String, String> customPropertiesMap =
            ParserUtil.parseCustomProperties(argMultimap.getAllCustomProperties());
        int numberOfPredicateTags = getNumberOfPredicateTags(muscles, customPropertiesMap);

        if (numberOfPredicateTags == 0) {
            logger.info("Invalid suggest possible command - no predicate tags");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
        }

        if (numberOfPredicateTags > 1) {
            if ((!argMultimap.arePrefixesPresent(PREFIX_OPERATION_TYPE) || !argMultimap.getPreamble().isEmpty())) {
                logger.info("Invalid suggest possible command - no operation type");
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
            }
        }

        if (argMultimap.arePrefixesPresent(PREFIX_OPERATION_TYPE)) {
            boolean operationType = ParserUtil.parseOperationType(argMultimap.getValue(PREFIX_OPERATION_TYPE).get());
            Predicate<Exercise> predicate = parsePredicate(muscles, customPropertiesMap, operationType);
            return new SuggestPossibleCommand(predicate);
        }

        Predicate<Exercise> predicate = parsePredicate(muscles, customPropertiesMap, true);
        return new SuggestPossibleCommand(predicate);
    }

    private static int getNumberOfPredicateTags(Set<Muscle> muscles, Map<String, String> customPropertiesMap) {
        return muscles.size() + customPropertiesMap.size();
    }
}
