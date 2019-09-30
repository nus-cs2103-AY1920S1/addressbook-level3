package seedu.address.ui.itinerary;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.template.WindowWithSidebar;

public class DaysPage extends WindowWithSidebar {

    private static final String FXML = "DaysPage.fxml";

    public DaysPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        DaysPage p = new DaysPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }
}
