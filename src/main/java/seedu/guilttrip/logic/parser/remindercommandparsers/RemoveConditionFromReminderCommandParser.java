package seedu.guilttrip.logic.parser.remindercommandparsers;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_LOWER_BOUND;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_UPPER_BOUND;

import seedu.guilttrip.logic.commands.remindercommands.RemoveConditionFromReminderCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Parses String into a RemoveConditionToReminderCommand object;
 */
public class RemoveConditionFromReminderCommandParser implements Parser<RemoveConditionFromReminderCommand> {
    private boolean toRemoveLowerBound;
    private boolean toRemoveUpperBound;
    private boolean toRemoveStart;
    private boolean toRemoveEnd;
    private boolean toRemoveTags;

    /**
     * Parses the given {@code String} of arguments in the context of the AddConditionToReminderCommand
     * and returns an AddConditionToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveConditionFromReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_UPPER_BOUND, PREFIX_LOWER_BOUND,
                PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG);
        if (argumentMultimap.getValue(PREFIX_LOWER_BOUND).isPresent()) {
            toRemoveLowerBound = true;
        }
        if (argumentMultimap.getValue(PREFIX_UPPER_BOUND).isPresent()) {
            toRemoveUpperBound = true;
        }
        if (argumentMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            toRemoveStart = true;
        }
        if (argumentMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            toRemoveEnd = true;
        }
        if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
            toRemoveTags = true;
        }
        return new RemoveConditionFromReminderCommand(toRemoveLowerBound, toRemoveUpperBound, toRemoveStart,
                toRemoveEnd, toRemoveTags);
    }
}
