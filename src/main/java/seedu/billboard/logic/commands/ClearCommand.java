package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;

/**
 * Clears the billboard.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Billboard has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBillboard(new Billboard());
        return new CommandResult(MESSAGE_SUCCESS, false, false, CommandResult.DEFAULT_LIST_VIEW);
    }
}
