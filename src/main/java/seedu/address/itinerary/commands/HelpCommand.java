package seedu.address.itinerary.commands;

import javafx.fxml.FXML;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.ui.HelpCommandWindow;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class HelpCommand extends Command {
    private HelpCommandWindow helpWindow = new HelpCommandWindow();

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up a window which showcase all"
            + " the commands for itinerary.";

    public static final String MESSAGE_SUCCESS = "Currently viewing the itinerary help window.";

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

    public CommandResult execute(Model model) throws CommandException {
        handleHelp();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
