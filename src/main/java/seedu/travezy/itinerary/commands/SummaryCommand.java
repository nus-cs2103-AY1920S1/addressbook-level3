package seedu.travezy.itinerary.commands;

import javafx.fxml.FXML;
import seedu.travezy.itinerary.model.Model;
import seedu.travezy.itinerary.ui.SummaryCommandWindow;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;

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
