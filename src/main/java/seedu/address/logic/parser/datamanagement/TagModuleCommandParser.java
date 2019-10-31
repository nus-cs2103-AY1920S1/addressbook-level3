package seedu.address.logic.parser.datamanagement;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.MODULE_PATTERN;
import static seedu.address.logic.parser.CliSyntax.TAG_PATTERN;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TagModuleCommand object
 */
public class TagModuleCommandParser implements Parser<TagModuleCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePatternsPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the TagModuleCommand
     * and returns an TagModuleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MODULE_PATTERN, TAG_PATTERN);

        if (!arePatternsPresent(argMultimap, MODULE_PATTERN, TAG_PATTERN)
                || argMultimap.getNumberOfArgsForPattern(MODULE_PATTERN) != 1
                || argMultimap.getNumberOfArgsForPattern(TAG_PATTERN) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagModuleCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModule(argMultimap.getValue(MODULE_PATTERN).get());
        String tagName = ParserUtil.parseTag(argMultimap.getValue(TAG_PATTERN).get());
        return new TagModuleCommand(tagName, moduleCode);
    }

}
