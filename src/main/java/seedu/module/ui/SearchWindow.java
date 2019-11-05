package seedu.module.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.module.commons.core.LogsCenter;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Controller for a search page
 */
public class SearchWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SearchWindow.class);
    private static final String FXML = "SearchWindow.fxml";
    private MainWindow mainWindow;

    @FXML
    private Button searchButton;

    @FXML
    private TextField moduleCodesBox;

    @FXML
    private TextField moduleTitleBox;

    @FXML
    private TextField descriptionBox;

    @FXML
    private TextField prerequisitesBox;

    @FXML
    private TextField preclusionsBox;

    @FXML
    private CheckBox semOneBox;

    @FXML
    private CheckBox semTwoBox;

    @FXML
    private CheckBox semThreeBox;

    @FXML
    private CheckBox semFourBox;

    /**
     * Creates a new SearchWindow.
     *
     * @param root Stage to use as the root of the searchWindow.
     */
    public SearchWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public SearchWindow(MainWindow mainWindow) {
        this(new Stage());
        this.mainWindow = mainWindow;
    }

    /**
     * Shows the search window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing search page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Search for the given module.
     */
    @FXML
    private void search() throws CommandException, ParseException {
        mainWindow.executeCommand(createCommand());
    }

    /**
     * Creates the command string from user UI input.
     */
    private String createCommand() {
        StringBuilder command = new StringBuilder();
        command.append("find ");
        if (!moduleCodesBox.getText().isEmpty()) {
            command.append("mod\\ ").append(moduleCodesBox.getText()).append(" ");
        }
        if (!moduleTitleBox.getText().isEmpty()) {
            command.append("title\\ ").append(moduleTitleBox.getText()).append(" ");
        }
        if (!descriptionBox.getText().isEmpty()) {
            command.append("desc\\ ").append(descriptionBox.getText()).append(" ");
        }
        if (!prerequisitesBox.getText().isEmpty()) {
            command.append("prereq\\ ").append(prerequisitesBox.getText()).append(" ");
        }
        if (!preclusionsBox.getText().isEmpty()) {
            command.append("preclu\\ ").append(preclusionsBox.getText()).append(" ");
        }
        if (semOneBox.isSelected() || semTwoBox.isSelected() || semThreeBox.isSelected() || semFourBox.isSelected()) {
            command.append("sem\\ ");
            if (semOneBox.isSelected()) {
                command.append("1 ");
            }
            if (semTwoBox.isSelected()) {
                command.append("2 ");
            }
            if (semThreeBox.isSelected()) {
                command.append("3 ");
            }
            if (semFourBox.isSelected()) {
                command.append("4 ");
            }
        }

        return command.toString();
    }
}
