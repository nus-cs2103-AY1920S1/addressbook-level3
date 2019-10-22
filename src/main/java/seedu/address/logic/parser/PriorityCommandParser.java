package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.commands.ScheduledPriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser to generate a priority command based on the user's input.
 */
public class PriorityCommandParser implements Parser<PriorityCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PriorityCommand
     * and returns a PriorityCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PriorityCommand parse(String args, String flags) throws ParseException {
        // flags should be empty in this case, focus on args only
        String time = args.trim();

        if (time.isEmpty()) {
            return new PriorityCommand();
        }
        try {
            LocalDateTime ldt = ParserUtil.getFormattedDateTime(time);
            return new ScheduledPriorityCommand(ldt);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE), pe);
        }
    }
}
