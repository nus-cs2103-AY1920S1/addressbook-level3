package seedu.address.ui.logic;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.ui.logic.exception.ParseException;
import seedu.address.util.CommandResult;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {
    public static final String COMMAND_WORD_NAVIGATION = "go";
    public static final String COMMAND_WORD_EXIT = "exit";
    public static final String HOME_TAB = "home";
    public static final String MEMBERS_TAB = "members";
    public static final String REIMBURSEMENTS_TAB = "reimbursements";
    public static final String INVENTORY_TAB = "inventory";
    public static final String CASHIER_TAB = "cashier";
    public static final String OVERVIEW_TAB = "overview";
    private final Logger logger = new LogsCenter().getLogger(getClass());

    @FXML
    private TabPane tabPane;

    public LogicManager(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public CommandResult execute(String commandText) throws ParseException {
        String command = commandText.split(" ")[0];
        String param;
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        if (command.equals(COMMAND_WORD_NAVIGATION)) {
            try {
                param = commandText.split(" ")[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParseException("Please specify a tab to switch to.");
            }
            return goToTab(param);
        } else if (command.equals(COMMAND_WORD_EXIT)) {
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

        if (param.equalsIgnoreCase(HOME_TAB)) {
            selectionModel.select(0);
        } else if (param.equalsIgnoreCase(MEMBERS_TAB)) {
            selectionModel.select(1);
        } else if (param.equalsIgnoreCase(REIMBURSEMENTS_TAB)) {
            selectionModel.select(2);
        } else if (param.equalsIgnoreCase(INVENTORY_TAB)) {
            selectionModel.select(3);
        } else if (param.equalsIgnoreCase(CASHIER_TAB)) {
            selectionModel.select(4);
        } else if (param.equalsIgnoreCase(OVERVIEW_TAB)) {
            selectionModel.select(5);
        } else {
            throw new ParseException("A tab with that name was not found.");
        }

        return new CommandResult("Switched to " + param + " tab!");
    }
}
