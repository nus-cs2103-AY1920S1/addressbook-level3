package seedu.address.logic.parser.datamanagement;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.MODULE_PATTERN;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewModuleTagsCommand object
 */
public class ViewModuleTagsCommandParser implements Parser<ViewModuleTagsCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePatternsPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewModuleTagsCommand
     * and returns an ViewModuleTagsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewModuleTagsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MODULE_PATTERN);

        if (!arePatternsPresent(argMultimap, MODULE_PATTERN)
                || argMultimap.getNumberOfArgsForPattern(MODULE_PATTERN) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewModuleTagsCommand.MESSAGE_USAGE));
        }
        String module = ParserUtil.parseModule(argMultimap.getValue(MODULE_PATTERN).get());
        return new ViewModuleTagsCommand(module);
    }

}
