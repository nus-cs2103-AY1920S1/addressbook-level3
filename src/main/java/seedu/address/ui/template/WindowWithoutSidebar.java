package seedu.address.ui.template;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Template class for windows without the navigation sidebar.
 */
public abstract class WindowWithoutSidebar extends MainWindow {
    public WindowWithoutSidebar(String fxmlFileName, Stage primaryStage, Logic logic, Model model) {
        super(fxmlFileName, primaryStage, logic, model);
    }
}
