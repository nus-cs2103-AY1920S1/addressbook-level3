package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;

/**
 * Command to handle scrolling events using CLI.
 */
public class ScrollCommand extends Command {

    public static final String COMMAND_WORD = "scroll";

    public static final String MESSAGE_SUCCESS = "Showing next view of schedule";
    public static final String MESSAGE_FAILURE = "No schedule is shown.\n"
            + "Scroll command requires an existing schedule to be present!";

    public ScrollCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleState status = model.getState();
        if (status == ScheduleState.PERSON
                || status == ScheduleState.GROUP) {
            return new CommandResultBuilder(MESSAGE_SUCCESS).setScroll().build();
        } else {
            return new CommandResultBuilder(MESSAGE_FAILURE).setScroll().build();
        }
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof ScrollCommand);
    }
}
