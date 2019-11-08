package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.GotoReminderCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GotoReminderCommand object
 */
public class GotoReminderCommandParser implements Parser<GotoReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GotoReminderCommand
     * and returns a GotoReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GotoReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GotoReminderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoReminderCommand.MESSAGE_USAGE), pe);
        }
    }
}
