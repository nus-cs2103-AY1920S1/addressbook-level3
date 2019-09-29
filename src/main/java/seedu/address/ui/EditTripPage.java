package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

public class EditTripPage extends WindowWithoutSidebar {

    private static final String FXML = "EditTripPage.fxml";

    public EditTripPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        EditTripPage p = new EditTripPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }

}
