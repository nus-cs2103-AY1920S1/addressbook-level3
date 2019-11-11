package seedu.guilttrip.logic.commands;

import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Date;

/**
 * Force sets the date for testing purposes.
 */
public class SetCurrDateCommand extends Command {
    public static final String COMMAND_WORD = "setDate";
    private static final String MESSAGE_SUCCESS = "Current date set to : %1$s";
    private final Date newCurrDate;
    public SetCurrDateCommand(Date date) {
        newCurrDate = date;
    }
    @Override
    public CommandResult execute(final Model model, final CommandHistory history) throws CommandException {
        TimeUtil.forceSetDate(newCurrDate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newCurrDate));
    }
}
