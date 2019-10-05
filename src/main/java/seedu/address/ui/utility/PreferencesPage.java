package seedu.address.ui.utility;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.WindowWithoutSidebar;

public class PreferencesPage extends WindowWithoutSidebar {

    private static final String FXML = "PreferencesPage.fxml";

    PreferencesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }
}
