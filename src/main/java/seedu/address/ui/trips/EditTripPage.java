package seedu.address.ui.trips;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.template.WindowWithoutSidebar;

public class EditTripPage extends WindowWithoutSidebar {

    private static final String FXML = "EditTripPage.fxml";

    @javafx.fxml.FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Button addButton;

    public EditTripPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }

    @FXML
    private void handleEditTrip() {
        //handle add
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        EditTripPage p = new EditTripPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }

}
