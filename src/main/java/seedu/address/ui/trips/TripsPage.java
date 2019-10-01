package seedu.address.ui.trips;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.components.CommandBox;
import seedu.address.ui.components.PersonListPanel;
import seedu.address.ui.components.ResultDisplay;
import seedu.address.ui.components.NavigationSideBar;
import seedu.address.ui.components.StatusBarFooter;
import seedu.address.ui.template.WindowWithoutSidebar;

import java.util.List;
import java.util.stream.Collectors;

public class TripsPage extends WindowWithoutSidebar {

    private static final String FXML = "TripsPage.fxml";


    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private GridPane tripGridPane;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Button addButton;

    public TripsPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(model.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        List<Node> tripPanes = model.getTravelPal().getTripList()
                .stream().map(trip -> new TripPane(trip, 1).getRoot())
                .collect(Collectors.toList());
        tripGridPane.getChildren().addAll(tripPanes);
    }

    @FXML
    private void handleAddTrip() {
        //handle add
    }

    public static void switchTo(Stage stage, Logic logic, Model model) {
        TripsPage p = new TripsPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }
}
