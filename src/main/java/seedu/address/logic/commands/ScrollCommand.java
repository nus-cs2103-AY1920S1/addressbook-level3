package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to handle scrolling events using CLI.
 */
public class ScrollCommand extends Command {

    public static final String COMMAND_WORD = "n";

    public static final String MESSAGE_SUCCESS = "Showing next view of schedule";
    public static final String MESSAGE_FAILURE = "Nothing to scroll";

    public ScrollCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleWindowDisplayType status = model.getState();
        if (status == ScheduleWindowDisplayType.PERSON
                || status == ScheduleWindowDisplayType.GROUP
                || status == ScheduleWindowDisplayType.NONE) {
            //model.updateScheduleWindowDisplay(new ScheduleWindowDisplay(ScheduleWindowDisplayType.NONE));
            return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
        } else {
            return new CommandResult(MESSAGE_FAILURE, false, false, false, false);
        }


    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof ScrollCommand);
    }
}
