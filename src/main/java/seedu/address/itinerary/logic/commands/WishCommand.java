package seedu.address.itinerary.logic.commands;

import javafx.fxml.FXML;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.ui.WishWindow;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

// The wish command will be implemented in v2.0.
/**
 * TravEzy look through previous entries and suggest three events for the given time slot.
 */
public class WishCommand extends Command<Model> {
    public static final String COMMAND_WORD = "wish";
    public static final String MESSAGE_SUCCESS = "TravEzy is currently doing wonders!";

    private WishWindow wishWindow = new WishWindow();

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleSuggest() {
        if (!wishWindow.isShowing()) {
            wishWindow.show();
        } else {
            wishWindow.focus();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        handleSuggest();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
