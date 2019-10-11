package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Command to change the focused panel to Order
 */
public class SwitchOrderPanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-o";

    public static final String MESSAGE_SUCCESS = "Switched to Order panel";



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.ORDER);
    }
}
