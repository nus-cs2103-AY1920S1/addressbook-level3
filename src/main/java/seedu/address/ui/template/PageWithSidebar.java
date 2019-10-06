package seedu.address.ui.template;

import javafx.scene.Node;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Template class for windows with the navigation sidebar.
 */
public abstract class PageWithSidebar<T extends Node> extends Page<T> {

    public PageWithSidebar(String fxmlFileName, MainWindow mainWindow, Logic logic, Model model) {
        super(fxmlFileName, mainWindow, logic, model);
    }
}
