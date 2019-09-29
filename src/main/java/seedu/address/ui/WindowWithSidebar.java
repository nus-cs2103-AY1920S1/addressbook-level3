package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

public abstract class WindowWithSidebar extends MainWindow {

    public WindowWithSidebar(String fxmlFileName, Stage primaryStage, Logic logic, Model model) {
        super(fxmlFileName, primaryStage, logic, model);
    }

    private void addSidebar() {

    }
}
