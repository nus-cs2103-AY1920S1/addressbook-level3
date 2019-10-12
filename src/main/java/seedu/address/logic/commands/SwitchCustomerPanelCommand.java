package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Command to change the focused panel to Customer
 */
public class SwitchCustomerPanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-c";

    public static final String MESSAGE_SUCCESS = "Switched to Customer panel";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.CUSTOMER);
    }
}
