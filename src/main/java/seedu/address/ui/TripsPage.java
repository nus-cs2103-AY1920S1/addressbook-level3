package seedu.address.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

import javax.swing.*;

public class TripsPage extends WindowWithoutSidebar {

    private static final String FXML = "Main.fxml";


    @javafx.fxml.FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox sidebarPlaceholder;

    @FXML
    private Button addButton;

    public TripsPage(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        Sidebar sidebar = new Sidebar();
        sidebarPlaceholder.getChildren().add(sidebar.getRoot());

        personListPanel = new PersonListPanel(model.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(model.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    @FXML
    private void handleAddTrip() {
        //handle add
    }

    static void switchTo(Stage stage, Logic logic, Model model) {
        TripsPage p = new TripsPage(stage, logic, model);
        p.show();
        p.fillInnerParts();
    }
}
