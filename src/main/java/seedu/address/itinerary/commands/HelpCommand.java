package seedu.address.itinerary.commands;

import javafx.fxml.FXML;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.ui.HelpCommandWindow;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens up a window with the commands available for itinerary.
 */
public class HelpCommand extends Command<Model> {
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
