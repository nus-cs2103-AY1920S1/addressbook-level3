package seedu.address.ui.utility;

import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.Page;

public class PreferencesPage extends Page<AnchorPane> {

    private static final String FXML = "PreferencesPage.fxml";

    public PreferencesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
    }
}
