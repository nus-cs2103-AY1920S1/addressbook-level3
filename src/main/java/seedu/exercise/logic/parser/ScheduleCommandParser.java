package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.ScheduleCommand;
import seedu.exercise.logic.commands.ScheduleCompleteCommand;
import seedu.exercise.logic.commands.ScheduleRegimeCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ScheduleCommand and returns a ScheduleCommand object for execution.
     *
     * @throws ParseException if the user input is not the expected format
     */
    @Override
    public ScheduleCommand parse(String userInput) throws ParseException {
        ArgumentMultimap regimeCommandMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_DATE);
        ArgumentMultimap completeCommandMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX);

        checkValidScheduleCommand(regimeCommandMultimap, completeCommandMultimap);

        if (areScheduleRegimePrefixesPresent(regimeCommandMultimap)) {
            return parseScheduleRegimeCommand(regimeCommandMultimap);
        } else {
            return parseScheduleCompleteCommand(completeCommandMultimap);
        }
    }

    /**
     * Parses a valid {@code ScheduleRegimeCommand}.
     */
    private ScheduleCommand parseScheduleRegimeCommand(ArgumentMultimap regimeMultimap) throws ParseException {
        Name regimeName = ParserUtil.parseName(regimeMultimap.getValue(PREFIX_NAME).get());
        Date date = ParserUtil.parseDate(regimeMultimap.getValue(PREFIX_DATE).get());

        return new ScheduleRegimeCommand(regimeName, date);
    }

    private ScheduleCommand parseScheduleCompleteCommand(ArgumentMultimap completeMultimap) throws ParseException {
        Index index = ParserUtil.parseIndex(completeMultimap.getValue(PREFIX_INDEX).get());

        return new ScheduleCompleteCommand(index);
    }

    /**
     * Checks if user input is either {@code ScheduleRegimeCommand} or {@code ScheduleCompleteCommand}.
     *
     * @throws ParseException when prefix for either commands are missing.
     */
    private void checkValidScheduleCommand(ArgumentMultimap regimeCommand, ArgumentMultimap completeCommand)
        throws ParseException {
        if (!areScheduleCompletePrefixesPresent(completeCommand) && !areScheduleRegimePrefixesPresent(regimeCommand)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
    }

    private static boolean areScheduleRegimePrefixesPresent(ArgumentMultimap multimap) {
        return multimap.arePrefixesPresent(PREFIX_NAME, PREFIX_DATE) && multimap.getPreamble().isEmpty();
    }

    private static boolean areScheduleCompletePrefixesPresent(ArgumentMultimap multimap) {
        return multimap.arePrefixesPresent(PREFIX_INDEX) && multimap.getPreamble().isEmpty();
    }

}
