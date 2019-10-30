package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PanelName;

/**
 * Toggles the visibility of a specified panel.
 */
public class TogglePanelCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles the visibility the specified panel. "
            + "PARAMETERS: (wishlist) wishlist/wish(es)/w, (budget) budget(s)/b, "
            + "(reminder) reminder(s)/r or (autoexpense) autoexpense(s)/autoexp/ae"
            + "EXAMPLE: " + COMMAND_WORD + " wishlist";

    public static final String MESSAGE_SUCCESS = "Toggled visibility of %1$s panel";

    private final PanelName panelName;

    public TogglePanelCommand(PanelName panelName) {
        this.panelName = panelName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, panelName), panelName, true);
    }
}
