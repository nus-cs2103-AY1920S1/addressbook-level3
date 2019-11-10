package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.parser.Option;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents any command that is a result of the user's previous input.
 */
public interface AlternativeCommand {
    CommandResult execute(Calendar calendar, Option option) throws CommandException;

    CommandResult execute(Calendar calendar) throws CommandException;
}
