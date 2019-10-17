package seedu.address.itinerary.commands;

import javafx.fxml.FXML;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.ui.SummaryCommandWindow;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class SummaryCommand extends Command {
    private SummaryCommandWindow summaryCommandWindow = new SummaryCommandWindow();

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_SUCCESS = "Currently viewing the itinerary summary window.";

    /**
     * Opens the summary window or focuses on it if it's already opened.
     */
    @FXML
    public void handleSummary(int number) {
        if (!summaryCommandWindow.isShowing()) {
            summaryCommandWindow.show();
        } else {
            summaryCommandWindow.focus();
        }
    }

    public CommandResult execute(Model model) throws CommandException {
        handleSummary(model.getFilteredEventList().size());
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
