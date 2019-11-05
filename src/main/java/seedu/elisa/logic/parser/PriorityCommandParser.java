package seedu.elisa.logic.parser;

import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.elisa.logic.commands.PriorityCommand;
import seedu.elisa.logic.commands.ScheduledPriorityCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;

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
        String time = args.trim();
        flags = flags.trim();

        boolean focusMode = false;
        if (!flags.isEmpty()) {
            if (flags.equalsIgnoreCase("-f") || flags.equalsIgnoreCase("-focus")) {
                focusMode = true;
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
            }
        }

        if (time.isEmpty()) {
            return new PriorityCommand(focusMode);
        }

        try {
            LocalDateTime ldt = ParserUtil.getFormattedDateTime(time);
            return new ScheduledPriorityCommand(ldt, focusMode);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE), pe);
        }
    }
}
