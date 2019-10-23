package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.CommandResult;

public abstract class Command {
    public abstract CommandResult execute(Calendar calendar);
}
