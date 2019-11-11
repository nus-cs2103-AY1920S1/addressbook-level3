package seedu.address.logic.commands;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;

/**
 * Command to return user back to home page.
 */
public class HomeCommand extends Command {
    public static final String COMMAND_WORD = "home";
    public static final String MESSAGE_SUCCESS = "Returning back to home page.";
    public static final String MESSAGE_USAGE = "Home command does not require any flags! Type home will do!";

    public HomeCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        model.updateScheduleWithUser(LocalDateTime.now(), ScheduleState.HOME);

        return new CommandResultBuilder(MESSAGE_SUCCESS)
                .setHome().build();
    }

    @Override
    public boolean equals(Command command) {
        return command instanceof HomeCommand;
    }
}
