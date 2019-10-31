package mams.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mams.commons.core.LogsCenter;
import mams.logic.InputOutput;

/**
 * Controller for a page displaying command history.
 */
public class HistoryWindow extends UiPart<Stage> {

    public static final String COMMAND_PROMPT_PREFIX = ">> ";
    public static final String DOUBLE_NEWLINE = "\n\n";

    private static final Logger logger = LogsCenter.getLogger(HistoryWindow.class);
    private static final String FXML = "HistoryWindow.fxml";

    private boolean hideOutputHistory;

    private HistoryListPanel historyListPanel;

    @FXML
    private StackPane historyDisplayPanelPlaceholder;

    /**
     * Creates a new HistoryWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HistoryWindow(Stage root, boolean hideOutputHistory, ObservableList<InputOutput> commandHistory) {
        super(FXML, root);
        this.hideOutputHistory = hideOutputHistory;
        this.historyListPanel = new HistoryListPanel(commandHistory);
        historyDisplayPanelPlaceholder.getChildren().add(historyListPanel.getRoot());

        // Global event filter: whenever ESC key is pressed, exit HistoryWindow regardless of focus.
        root.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    /**
     * Creates a new HistoryWindow, and populates it with the command history.
     *
     * @param hideOutputHistory if true, then command feedback history will be displayed along with command history
     */
    public HistoryWindow(boolean hideOutputHistory, ObservableList<InputOutput> commandHistory) {
        this(new Stage(), hideOutputHistory, commandHistory);
    }

    /**
     * Updates the history text box with the new {@code commandHistory}.
     * @param commandHistory List of InputOutput obj
     */
    public void updateHistoryDisplay(List<InputOutput> commandHistory) {
        String historyAsText = formatCommandHistoryToString(commandHistory);
        //resultDisplay.setText(historyAsText);
        //resultDisplay.appendText(""); // auto-scroll to bottom
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
     * Formats the given {@code commandHistory} into a {@code String} formatted for displaying
     * in {@code HistoryWindow}
     * @param commandHistory
     * @return formatted text
     */
    private String formatCommandHistoryToString(List<InputOutput> commandHistory) {
        StringBuilder sb = new StringBuilder();

        for (InputOutput inputOutput : commandHistory) {
            sb.append(COMMAND_PROMPT_PREFIX);
            sb.append(inputOutput.getInput());
            if (!hideOutputHistory) {
                sb.append(DOUBLE_NEWLINE);
                sb.append(inputOutput.getOutput());
            }
            sb.append(DOUBLE_NEWLINE);
        }
        return sb.toString().trim();
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
        historyListPanel.scrollToBottom();
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
        default:
            break;
        }
    }
}
