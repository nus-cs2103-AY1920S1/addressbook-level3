package seedu.address.itinerary.commands;

import javafx.fxml.FXML;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.ui.SummaryCommandWindow;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens up a window with the summary statistic for itinerary.
 */
public class SummaryCommand extends Command {
    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_SUCCESS = "Currently viewing the itinerary summary window.";

    private SummaryCommandWindow summaryCommandWindow = new SummaryCommandWindow();

    /**
     * Opens the summary window or focuses on it if it's already opened.
     */
    @FXML
    private void handleSummary() {
        if (!summaryCommandWindow.isShowing()) {
            summaryCommandWindow.show();
        } else {
            summaryCommandWindow.focus();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        handleSummary();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
