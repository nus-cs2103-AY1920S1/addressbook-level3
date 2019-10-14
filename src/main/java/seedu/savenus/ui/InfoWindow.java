package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.info.AddInfo;
import seedu.savenus.logic.commands.InfoCommand;

/**
 * Controller for a info page
 */
public class InfoWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(InfoWindow.class);
    private static final String FXML = "InfoWindow.fxml";

    @FXML
    private Label commandWord;

    @FXML
    private Label infoMessage;

    @FXML
    private Label usageExample;

    @FXML
    private Label output;

    @FXML
    private Button quitButton;

    /**
     * Creates a new InfoWindow.
     *
     * @param root Stage to use as the root of the InfoWindow.
     */
    public InfoWindow(Stage root) {
        super(FXML, root);
        root.initStyle(StageStyle.UNDECORATED);
        commandWord.setText("Default");
        infoMessage.setText("Default");
        usageExample.setText("Default");
    }

    /**
     * Creates a new InfoWindow.
     */
    public InfoWindow() {
        this(new Stage());
    }

    public void closeWindow() {
        getRoot().close();
    }

    /**
     * Shows the information window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show(String input) {
        logger.fine("Showing info page about the command.");
        switch (input) {
            case InfoCommand.ADD_INFO :
                commandWord.setText(AddInfo.COMMAND_WORD);
                infoMessage.setText(AddInfo.INFORMATION);
                usageExample.setText(AddInfo.USAGE);
                output.setText(AddInfo.OUTPUT);
        }
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the info window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the info window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
