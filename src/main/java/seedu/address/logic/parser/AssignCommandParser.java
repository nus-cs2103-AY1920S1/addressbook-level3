package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.EventTime;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {


    private static ParseException getWrongFormatException() {
        return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @return the parsed command
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRIVER, PREFIX_TASK, PREFIX_EVENT_TIME);


        // check if the force flag is correctly spelled
        boolean isForce = argMultimap.getPreamble().equals("force");
        boolean isEmpty = argMultimap.getPreamble().isEmpty();

        if (isEmpty == isForce) {
            throw getWrongFormatException();
        }

        String driver = argMultimap.getValue(PREFIX_DRIVER).orElseThrow(AssignCommandParser::getWrongFormatException);
        int driverId = ParserUtil.parseId(driver);

        String task = argMultimap.getValue(PREFIX_TASK).orElseThrow(AssignCommandParser::getWrongFormatException);
        int taskId = ParserUtil.parseId(task);


        String time = argMultimap.getValue(PREFIX_EVENT_TIME).orElseThrow(AssignCommandParser::getWrongFormatException);

        EventTime proposed;
        try {
            proposed = ParserUtil.parseEventTime(time);
        } catch (DateTimeParseException e) {
            throw getWrongFormatException();
        }

        return new AssignCommand(driverId, taskId, proposed, isForce);
    }

}
