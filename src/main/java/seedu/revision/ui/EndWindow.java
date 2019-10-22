package seedu.revision.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Controller for a end page
 */
public class EndWindow extends UiPart<Stage> {

    public static final String END_MESSAGE = "Quiz ended. Do you want to try again? (Y/N) ";

    private static final Logger logger = LogsCenter.getLogger(EndWindow.class);
    //TODO: Add end window fxml
    private static final String FXML = "EndWindow.fxml";

    @FXML
    private Label endMessage;

    private Window mainWindow;
    private Window quizWindow;

    /**
     * Creates a new HelpWindow.
     */
    public EndWindow(Window mainWindow, Window quizWindow) {
        this(new Stage(), mainWindow, quizWindow);

    }

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public EndWindow(Stage root, Window mainWindow, Window quizWindow) {
        super(FXML, root);
        this.mainWindow = mainWindow;
        this.quizWindow = quizWindow;
        endMessage.setText(END_MESSAGE);
        VBox vBox = new VBox(endMessage);
        Scene scene = new Scene(vBox);
        root.setScene(scene);
        getRoot().setScene(scene);
        addKeyBoardListener();
    }

    private void addKeyBoardListener() {
        getRoot().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case Y:
                    logger.info("Y pressed");
                    quizWindow.show();
                    break;
                case N:
                    logger.info("N pressed");
                    mainWindow.show();
                    getRoot().hide();
                    break;
                }
            }
        });
    }

    /**
     * Shows the end window.
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
    public void show() {
        logger.fine("Showing message after quiz ended");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the end window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the end window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the end window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
