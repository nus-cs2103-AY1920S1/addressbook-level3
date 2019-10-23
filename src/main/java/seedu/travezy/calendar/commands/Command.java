package seedu.travezy.calendar.commands;

import seedu.travezy.calendar.model.Calendar;
import seedu.travezy.logic.commands.CommandResult;

public abstract class Command {
    public abstract CommandResult execute(Calendar calendar);
}
