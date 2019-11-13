package budgetbuddy.ui;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for the demo window
 */
public class DemoWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(DemoWindow.class);
    private static final String FXML = "DemoWindow.fxml";

    private final MainWindow mainWindow;

    @FXML
    private ListView<String> mainList;

    @FXML
    private Button editBtn;

    @FXML
    private Button execBtn;

    /**
     * Creates a new DemoWindow.
     */
    public DemoWindow(MainWindow mainWindow) {
        super(FXML, new Stage());
        this.mainWindow = mainWindow;
        mainList.setCellFactory(view -> {
            ListCell<String> ret = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText("");
                    } else {
                        setText(item);
                    }
                }
            };
            return ret;
        });
        mainList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                handleExecClicked();
            }
        });
    }

    /**
     * Handles clicking a list item.
     */
    @FXML
    private void handleListClicked(MouseEvent event) {
        String selected = mainList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            handleListItemDoubleClicked(selected);
        }

    }

    /**
     * Handles clicking the edit button.
     */
    @FXML
    private void handleEditClicked() {
        TextAreaDialog tid = new TextAreaDialog();
        tid.setTitle("Demo command list");
        tid.setHeaderText("Enter command list:");
        String res = tid.showAndWait().orElse(null);

        if (res != null) {
            String[] lines = res.split("\n");
            mainList.setItems(FXCollections.observableArrayList(lines));
        }
    }

    @FXML
    private void handleExecClicked() {
        getCommandBox().getOnAction().handle(new ActionEvent());
    }

    private void handleListItemDoubleClicked(String item) {
        getCommandBox().setText(item.trim());
    }

    private TextField getCommandBox() {
        return (TextField) mainWindow.getRoot().getScene().getRoot().lookup("#commandTextField");
    }

    /**
     * Shows the demo window.
     */
    public void show() {
        getRoot().show();
    }
}
