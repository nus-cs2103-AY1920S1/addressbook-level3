package seedu.address.ui.logic;

import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.ui.commands.CommandResult;
import seedu.address.ui.logic.exception.ParseException;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {

    @FXML
    private TabPane tabPane;

    public LogicManager(TabPane tabPane) {
        this.tabPane = tabPane;
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

}
