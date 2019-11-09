package mams.ui.history;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mams.commons.core.LogsCenter;
import mams.logic.history.InputOutput;
import mams.ui.ResultDisplay;
import mams.ui.UiPart;

/**
 * Controller for a page displaying command history.
 */
public class HistoryWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HistoryWindow.class);
    private static final String FXML = "HistoryWindow.fxml";

    private static final String MESSAGE_COPY_FEEDBACK = "Copied the following command to system clipboard:\n%1$s";

    private boolean hideOutputHistory;

    private HistoryListPanel historyListPanel;
    private ResultDisplay historyWindowFeedback;

    @FXML
    private StackPane historyDisplayPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;

    /**
     * Creates a new HistoryWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HistoryWindow(Stage root, boolean hideOutputHistory, ObservableList<InputOutput> commandHistory) {
        super(FXML, root);
        requireNonNull(commandHistory);
        this.hideOutputHistory = hideOutputHistory;
        this.historyListPanel = new HistoryListPanel(commandHistory);
        historyDisplayPanelPlaceholder.getChildren().add(historyListPanel.getRoot());
        this.historyWindowFeedback = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(historyWindowFeedback.getRoot());

        // Global event filter: whenever ESC key is pressed, exit HistoryWindow regardless of focus.
        root.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    /**
     * Creates a new HistoryWindow, and populates it with the command history.
     *
     * @param hideOutputHistory if true, then command feedback history will be displayed along with command history
     */
    public HistoryWindow(boolean hideOutputHistory, ObservableList<InputOutput> commandHistory) {
        this(new Stage(), hideOutputHistory, commandHistory); // null checking delegated to other constructor
    }

    /**
     * Setter method for displaying/hiding command feedback in the history window
     * @param isOutputHidden if true, command feedback is shown in history window
     */
    public void hideOutputDisplay(boolean isOutputHidden) {
        logger.fine("Feedback display in history window has been set to " + Boolean.toString(isOutputHidden));
        this.hideOutputHistory = isOutputHidden;
        historyListPanel.hideOutput(isOutputHidden);
    }

    /**
     * Shows the history window.
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
        logger.fine("Showing the command history page.");
        getRoot().show();
        getRoot().centerOnScreen();
        scrollToBottom();
    }

    /**
     * Returns true if the history window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the history window.
     */
    public void hide() {
        getRoot().hide();
        historyWindowFeedback.clearDisplay();
    }

    /**
     * Focuses on the history window.
     */
    public void focus() {
        getRoot().requestFocus();
        scrollToBottom();
    }

    /**
     * Scroll display list to bottom.
     */
    public void scrollToBottom() {
        historyDisplayPanelPlaceholder.requestFocus();
        historyListPanel.scrollToBottom();
    }

    /**
     * Copies the input of a InputOutput object to the system clipboard.
     */
    public void copyToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent copiedText = new ClipboardContent();
        InputOutput selected = historyListPanel.getCurrentlySelectedInputOutput();
        copiedText.putString(selected.getInput());
        clipboard.setContent(copiedText);
        historyWindowFeedback.setFeedbackToUser(String.format(MESSAGE_COPY_FEEDBACK, selected.getInput()));
    }

    /**
     * Handles {@code KeyEvent} for the history window.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case ESCAPE:
            keyEvent.consume();
            hide();
            break;
        case Q:
            keyEvent.consume();
            copyToClipboard();
            break;
        default:
            break;
        }
    }
}
