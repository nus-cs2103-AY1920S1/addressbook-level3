package seedu.guilttrip.logic.commands.uicommands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.ui.util.PanelName;

/**
 * Toggles the visibility of a specified panel.
 */
public class TogglePanelCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Toggles the visibility the specified panel. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "PARAMETERS: wishlist/wishes/wish, budget/budgets, reminders/reminder OR autoexpense/autoexp\n"
            + "ALIASES: w, b, r, ae"
            + "  EXAMPLE: " + COMMAND_WORD + " wishlist";

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
