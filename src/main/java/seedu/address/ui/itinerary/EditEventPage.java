package seedu.address.ui.itinerary;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.template.WindowWithSidebar;

public class EditEventPage extends WindowWithSidebar {

    private static final String FXML = "EditEventPage.fxml";

    public EditEventPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        EditEventPage p = new EditEventPage(stage, logic, model);
    }
}
