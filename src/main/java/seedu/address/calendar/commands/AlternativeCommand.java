package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public interface AlternativeCommand {
    CommandResult execute(Calendar calendar) throws CommandException;
}
