package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;

/**
 * Command to toggle schedule view to show the subsequent week.
 */
public class ToggleNextWeekCommand extends Command {

    public static final String COMMAND_WORD = "togglenext";
    public static final String MESSAGE_SUCCESS = "Showing subsequent weeks' schedule! You can only view schedules up "
            + "to 4 weeks ahead.";
    public static final String MESSAGE_USAGE = "To view next week's schedule, type togglenext!";
    public static final String MESSAGE_FAILURE = "No schedule is shown.\n"
            + "The toggle next command only works when there is an existing schedule in your schedule window!";

    public ToggleNextWeekCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleState status = model.getState();
        if (status == ScheduleState.PERSON
                || status == ScheduleState.GROUP) {
            return new CommandResultBuilder(MESSAGE_SUCCESS).setToggleNextWeek().build();
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Command command) {
        return command instanceof ToggleNextWeekCommand;
    }
}
