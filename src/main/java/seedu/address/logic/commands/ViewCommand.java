package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.ui.tab.Tab;

/**
 * Views the different tabs in PalPay.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = ViewCommand.COMMAND_WORD + ": View the tab in PalPay.\n"
        + "Parameter: TAB\n"
        + "Example: " + ViewCommand.COMMAND_WORD + " transaction";

    public static final String MESSAGE_SUCCESS = "Tab switched!";

    private final Tab tab;

    public ViewCommand(Tab tab) {
        this.tab = tab;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, tab);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ViewCommand)) {
            return false;
        }

        ViewCommand other = (ViewCommand) obj;
        return tab == other.tab;
    }
}
