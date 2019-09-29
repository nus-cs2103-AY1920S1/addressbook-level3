package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

public abstract class WindowWithoutSidebar extends MainWindow {

    public WindowWithoutSidebar(String fxmlFileName, Stage primaryStage, Logic logic, Model model) {
        super(fxmlFileName, primaryStage, logic, model);
    }
}
