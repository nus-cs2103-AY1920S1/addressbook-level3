package seedu.travezy.itinerary.commands;

import javafx.fxml.FXML;
import seedu.travezy.itinerary.model.Model;
import seedu.travezy.itinerary.ui.HelpCommandWindow;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;

/**
 * Opens up a window with the commands available for itinerary.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up a window which showcase all"
            + " the commands for itinerary.";

    public static final String MESSAGE_SUCCESS = "Currently viewing the itinerary help window. (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧";

    private HelpCommandWindow helpWindow = new HelpCommandWindow();

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        handleHelp();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
