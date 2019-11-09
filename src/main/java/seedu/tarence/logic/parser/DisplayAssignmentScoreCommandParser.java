package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_DISPLAY_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.DisplayAssignmentScoreCommand;
import seedu.tarence.logic.commands.DisplayAttendanceCommand;
import seedu.tarence.logic.commands.DisplayFormat;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DisplayAssignmentScoreCommand object.
 */
public class DisplayAssignmentScoreCommandParser extends CommandParser<DisplayAssignmentScoreCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DisplayAssignmentScoreCommand
     * and returns a DisplayAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayAssignmentScoreCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_NAME, PREFIX_FORMAT);
        if (arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME)) {
            Index index = retrieveIndex(argMultimap);
            String assignmentName = argMultimap.getValue(PREFIX_NAME).get();
            DisplayFormat displayFormat = retrieveDisplayFormat(argMultimap);
            return new DisplayAssignmentScoreCommand(index, assignmentName, displayFormat);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplayAssignmentScoreCommand.MESSAGE_USAGE));
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

    /**
     * Retrives the format to display based on user input.
     * @throws ParseException if the user input does not match any valid display format.
     */
    private DisplayFormat retrieveDisplayFormat(ArgumentMultimap argumentMultimap) throws ParseException {
        if (!arePrefixesPresent(argumentMultimap, PREFIX_FORMAT)) {
            return DisplayFormat.GRAPH;
        }
        String format = argumentMultimap.getValue(PREFIX_FORMAT).get().toLowerCase();
        if (Arrays.stream(DisplayAssignmentScoreCommand.TABLE_SYNONYMS).anyMatch(name -> name.equals(format))) {
            return DisplayFormat.TABLE;
        }
        if (Arrays.stream(DisplayAssignmentScoreCommand.GRAPH_SYNONYMS).anyMatch(name -> name.equals(format))) {
            return DisplayFormat.GRAPH;
        }
        throw new ParseException(String.format(MESSAGE_INVALID_DISPLAY_FORMAT,
                DisplayAssignmentScoreCommand.MESSAGE_USAGE));
    }
}
