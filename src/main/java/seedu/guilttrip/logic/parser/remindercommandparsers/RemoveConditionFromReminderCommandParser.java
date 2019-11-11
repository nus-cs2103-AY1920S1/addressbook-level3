package seedu.guilttrip.logic.parser.remindercommandparsers;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.RemoveConditionFromReminderCommand;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Parses String into a AddConditionToReminderCommand object.
 */
public class RemoveConditionFromReminderCommandParser implements Parser<RemoveConditionFromReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddConditionToReminderCommand
     * and returns an AddConditionToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveConditionFromReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index = ParserUtil.parseIndex(args.trim());
        return new RemoveConditionFromReminderCommand(index);
    }
}
