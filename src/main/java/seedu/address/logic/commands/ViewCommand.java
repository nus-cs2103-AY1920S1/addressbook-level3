package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.ui.tab.Tab;

/**
 * Views the different kind of modes in PalPay.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = ViewCommand.COMMAND_WORD + ": View the bank account.\n"
        + "Parameter: SIMI\n"
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
}
