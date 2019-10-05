package seedu.address.ui.template;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.Page;

/**
 * Template class for windows with the navigation sidebar.
 */
public abstract class WindowWithSidebar<T extends Node> extends Page<T> {

    public WindowWithSidebar(String fxmlFileName, MainWindow mainWindow, Logic logic, Model model) {
        super(fxmlFileName, mainWindow, logic, model);
    }
}
