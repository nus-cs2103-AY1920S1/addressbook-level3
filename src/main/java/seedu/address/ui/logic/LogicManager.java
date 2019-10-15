package seedu.address.ui.logic;

import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.ui.HelpWindow;
import seedu.address.ui.logic.exception.ParseException;
import seedu.address.ui.commands.CommandResult;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {

    @FXML
    private TabPane tabPane;

    @FXML
    private HelpWindow helpWindow;

    public LogicManager(TabPane tabPane, HelpWindow helpWindow) {
        this.tabPane = tabPane;
        this.helpWindow = helpWindow;
    }

    @Override
    public CommandResult execute(String commandText) throws ParseException {
        String command = commandText.split(" ")[0];
        String param;

        if (command.equals("go")) {
            try {
                param = commandText.split(" ")[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParseException("Please specify a tab to switch to.");
            }
            return goToTab(param);
        } else if (command.equals("exit")) {
            return new CommandResult("Exiting...", true);
        } else if (command.equals("help")) {
            handleHelp();
            return new CommandResult("Showing help menu.");
        } else {
            throw new ParseException("This really shouldn't happen. How did you get here?");
        }
    }

    /**
     * Navigates to another tab and returns empty overall command result.
     * @param param the tab to go to.
     * @return an empty CommandResult.
     * @throws ParseException If an error occurs due to wrong format for tab navigation command.
     */
    private CommandResult goToTab(String param) throws ParseException {

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

        if (param.equalsIgnoreCase("home")) {
            selectionModel.select(0);
        } else if (param.equalsIgnoreCase("members")) {
            selectionModel.select(1);
        } else if (param.equalsIgnoreCase("reimbursements")) {
            selectionModel.select(2);
        } else if (param.equalsIgnoreCase("inventory")) {
            selectionModel.select(3);
        } else if (param.equals("cashier")) {
            selectionModel.select(4);
        } else if (param.equalsIgnoreCase("overview")) {
            selectionModel.select(5);
        } else {
            throw new ParseException("A tab with that name was not found.");
        }

        return new CommandResult("Switched to " + param + " tab!");
    }

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

}
