package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.DisplayAttendanceCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

/**
 * Parses input arguments and creates a new DisplayAttendanceCommand object
 */
public class DisplayAttendanceCommandParser extends CommandParser<DisplayAttendanceCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DisplayAttendanceCommand
     * and returns a DisplayAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_INDEX);

        // If all prefixes are present, the format is ambiguous
        if (arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_INDEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayAttendanceCommand.MESSAGE_USAGE));
        }

        // Parse first format
        if (arePrefixesPresent(argMultimap, PREFIX_INDEX)) {
            Index index = retrieveIndex(argMultimap);
            return new DisplayAttendanceCommand(index);
        }

        // Parse second format
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayAttendanceCommand.MESSAGE_USAGE));
        }

        TutName tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
        ModCode modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
        return new DisplayAttendanceCommand(modCode, tutName);
    }

    /**
     * Retrieves index entered by user and parses through the Indexing format
     */
    private Index retrieveIndex(ArgumentMultimap argumentMultimap) throws ParseException {
        try {
            return Index.fromOneBased(Integer.valueOf(argumentMultimap.getValue(PREFIX_INDEX).get()));
        } catch (RuntimeException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT,
                    DisplayAttendanceCommand.MESSAGE_USAGE));
        }
    }
}
