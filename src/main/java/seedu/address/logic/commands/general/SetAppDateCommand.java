package seedu.address.logic.commands.general;

import java.time.LocalDate;

import seedu.address.commons.core.Config;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Command to Manually State the AppDate.
 */
public class SetAppDateCommand extends Command {

    public static final String COMMAND_WORD = "set_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the Date used in the App.\n"
            + "Warning: A typical user shouldn't need to do this. \n"
            + "Example: " + COMMAND_WORD + "28/10/2019";

    public static final String MESSAGE_SUCCESS = "Set App Date as %s";
    private final LocalDate toSet;

    /**
     * Creates a SetAppDateCommand
     *
     * @param toSet Date to change
     */
    public SetAppDateCommand(LocalDate toSet) {
        this.toSet = toSet;
    }

    @Override
    public CommandResult execute(Model model) {
        Config.setCurrentDate(toSet);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Config.getCurrentDate()));
    }
}
