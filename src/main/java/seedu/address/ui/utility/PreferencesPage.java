package seedu.address.ui.utility;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.template.WindowWithoutSidebar;

public class PreferencesPage extends WindowWithoutSidebar {

    private static final String FXML = "PreferencesPage.fxml";

    PreferencesPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        PreferencesPage p = new PreferencesPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }
}
