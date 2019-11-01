package seedu.sugarmummy.logic.commands.bio;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Displays information on user's biography.
 */
public class BioCommand extends Command {

    public static final String COMMAND_WORD = "bio";

    public static final String SHOWING_BIO_MESSAGE = "Here's your biography information I've displayed on this "
            + "window.\nPlease keep your data safe!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_BIO_MESSAGE, false, true, false, false);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BIO;
    }
}
