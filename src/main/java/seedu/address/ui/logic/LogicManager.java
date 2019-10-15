package seedu.address.ui.logic;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import seedu.address.overview.commands.CommandResult;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {

    @FXML
    TabPane tabPane;

    public LogicManager(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        return new CommandResult("To be implemented");
    }

}
