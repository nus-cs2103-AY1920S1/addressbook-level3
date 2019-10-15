package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.UiManager;

import static java.util.Objects.requireNonNull;

public class CheckCommand extends Command {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_SUCCESS_CONTACT = "Contact Shown";

    public static final String MESSAGE_SUCCESS_CLAIM = "Claim Shown";

    public static final String MESSAGE_FAILURE = "Command not available in this View";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Goes to the view identified in the parameter typed by user\n"
            + "Parameters: 'index'\n"
            + "Example: " + COMMAND_WORD + " contacts";

    public CheckCommand(Index index) {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //if the current state is not claims or contacts, the check command will be invalid
        if (UiManager.state.equals("claims")) {
            return new CommandResult(MESSAGE_SUCCESS_CLAIM, false, false, true);
        } else if (UiManager.state.equals("contacts")) {
            return new CommandResult(MESSAGE_SUCCESS_CONTACT);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
