package mams.logic.parser;

import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import mams.commons.core.index.Index;
import mams.logic.commands.ViewCommand;
import mams.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {

    // TODO standardize prefixes with rest of the group. leaving these here as local statics for now.
    public static final Prefix PREFIX_MODS = new Prefix("mod/");
    public static final Prefix PREFIX_STUDENTS = new Prefix("stu/");
    public static final Prefix PREFIX_APPEALS = new Prefix("app/");
    public static final Prefix PREFIX_ALL = new Prefix("all/");

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPEALS, PREFIX_MODS, PREFIX_STUDENTS);

        if (areAllPrefixesAbsent(argMultimap, PREFIX_APPEALS, PREFIX_MODS, PREFIX_STUDENTS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        try {
            if (argMultimap.getValue(PREFIX_APPEALS).isPresent()) {
                Index appealIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APPEALS).get());
            }

            if (argMultimap.getValue(PREFIX_MODS).isPresent()) {
                Index moduleIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MODS).get());
            }

            if (argMultimap.getValue(PREFIX_STUDENTS).isPresent()) {
                Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENTS).get());
            }
            return new ViewCommand();
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns true if all of the prefixes are not present in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAllPrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
