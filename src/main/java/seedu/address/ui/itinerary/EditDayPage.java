package seedu.address.ui.itinerary;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.template.WindowWithSidebar;

public class EditDayPage extends WindowWithSidebar {

    private static final String FXML = "EditDayPage.fxml";

    public EditDayPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        EditDayPage p = new EditDayPage(stage, logic, model);
    }
}
