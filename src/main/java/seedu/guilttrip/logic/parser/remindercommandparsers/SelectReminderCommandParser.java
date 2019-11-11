package seedu.guilttrip.logic.parser.remindercommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.SelectReminderCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Generates a SelectReminderCommand
 */
public class SelectReminderCommandParser implements Parser<SelectReminderCommand> {
    @Override
    public SelectReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectReminderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectReminderCommand.MESSAGE_USAGE), pe);
        }
    }
}
