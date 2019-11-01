package seedu.sugarmummy.logic.commands.bio;

import static java.util.Objects.requireNonNull;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.bio.UserList;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Clears the address book.
 */
public class ClearBioCommand extends Command {

    public static final String COMMAND_WORD = "clrbio";
    public static final String MESSAGE_SUCCESS = "Bio has been cleared!";
    public static final String MESSAGE_ALREADY_EMPTY = "Oops! Bio is already empty and as such there is no biography "
            + "information for me to clear.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getFilteredUserList().isEmpty()) {
            throw new CommandException(MESSAGE_ALREADY_EMPTY);
        }
        model.setUserList(new UserList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BIO;
    }

    @Override
    public boolean getNewPaneIsToBeCreated() {
        return true;
    }
}
