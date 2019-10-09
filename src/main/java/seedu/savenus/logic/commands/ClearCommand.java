package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;

/**
 * Clears all food items from our $aveNUS menu.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Your menu has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMenu(new Menu());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
