package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class SetLightThemeCommand extends Command {

    public static final String COMMAND_WORD = "setLightTheme";

    public static final String MESSAGE_SUCCESS = "Changed to light theme";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, true, "light");
    }

}
