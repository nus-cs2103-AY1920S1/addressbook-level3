package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.SetDeadlineCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SetDeadlineCommand object
 */
public class SetDeadlineCommandParser implements Parser<SetDeadlineCommand> {
    public static final String MESSAGE_NO_ID = "Please enter the TASK ID of the task you want to set a deadline for.";
    public static final String DEADLINE_PASSED = "The deadline entered has already passed, please try again";

    /**
     * Parses the given {@code String} of arguments in the context of the SetDeadlineCommand
     * and returns an SetDeadlineCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetDeadlineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX, PREFIX_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_INDEX, PREFIX_DEADLINE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDeadlineCommand.MESSAGE_USAGE));
        }

        Index index;
        LocalDateTime dateTime;

        index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());
        dateTime = DateTimeUtil.parseDateTime(argMultimap.getValue(PREFIX_DEADLINE).get());

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new ParseException(DEADLINE_PASSED);
        }

        return new SetDeadlineCommand(index, dateTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

