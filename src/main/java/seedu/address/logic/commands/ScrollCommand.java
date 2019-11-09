package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to handle scrolling events using CLI.
 */
public class ScrollCommand extends Command {

    public static final String COMMAND_WORD = "scroll";

    public static final String MESSAGE_SUCCESS = "Showing next view of schedule";
    public static final String MESSAGE_FAILURE = "Nothing to scroll";

    public ScrollCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleWindowDisplayType status = model.getState();
        if (status == ScheduleWindowDisplayType.PERSON
                || status == ScheduleWindowDisplayType.GROUP) {
            return new CommandResultBuilder(MESSAGE_SUCCESS).setScroll().build();
        } else {
            return new CommandResultBuilder(MESSAGE_FAILURE).build();
        }


    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof ScrollCommand);
    }
}
