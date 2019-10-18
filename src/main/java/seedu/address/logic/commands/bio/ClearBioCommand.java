package seedu.address.logic.commands.bio;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.UserList;
import seedu.address.ui.DisplayPaneType;

/**
 * Clears the address book.
 */
public class ClearBioCommand extends Command {

    public static final String COMMAND_WORD = "clrbio";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUserList(new UserList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BIO;
    }
}
