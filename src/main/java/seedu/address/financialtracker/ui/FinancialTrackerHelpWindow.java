package seedu.address.financialtracker.ui;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.PageManager;
import seedu.address.ui.UiPart;

/**
 * Controller for a help page.
 */
@SuppressWarnings("unused")
public class FinancialTrackerHelpWindow extends UiPart<Stage> {

    private static final String HELP_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    private static final String HELP_MESSAGE = "For more information check out our user guide here: \n" + HELP_URL;
    private static final String SUMMARY_MESSAGE = "summary";
    private static final String HELP = "help";
    private static final String GOTO_MESSAGE = "goto PAGE";
    private static final String EXIT_MESSAGE = "exit";
    private static final String ADD_MESSAGE = "add a/AMOUNT d/DESCRIPTION t/TYPE_OF_EXPENDITURE "
            + "[date/DATE] [time/TIME]";
    private static final String DELETE_MESSAGE = "delete INDEX";
    private static final String EDIT_MESSAGE = "edit INDEX [a/AMOUNT] [d/DESCRIPTION] [t/TYPE_OF_EXPENDITURE] "
            + "[date/DATE] [time/TIME]";
    private static final String SORT_MESSAGE = "sort amount";
    private static final String SWITCH_MESSAGE = "switch Japan";
    private static final String CLEAR_MESSAGE = "clear";
    private static final String UNDO_MESSAGE = "undo";
    private static final Logger logger = LogsCenter.getLogger(FinancialTrackerHelpWindow.class);
    private static final String FXML = "FinancialTrackerHelpWindow.fxml";


    @FXML
    private Button copyButton;

    @FXML
    private Button summaryCommand;

    @FXML
    private Button helpCommand;

    @FXML
    private Button gotoCommand;

    @FXML
    private Button exitCommand;

    @FXML
    private Button addCommand;

    @FXML
    private Button deleteCommand;

    @FXML
    private Button editCommand;

    @FXML
    private Button sortCommand;

    @FXML
    private Button switchCommand;

    @FXML
    private Button clearCommand;

    @FXML
    private Button undoCommand;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    private FinancialTrackerHelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        // Adapted from pohlinwei
        getRoot().focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                delay.setOnFinished(event -> getRoot().hide());
                delay.play();
            }
        }));

        getRoot().setOnShowing(event -> {
            getRoot().setHeight(494);
            getRoot().setWidth(582);
            // centralise
            getRoot().setX(PageManager.getXPosition() - 291);
            getRoot().setY(PageManager.getYPosition() - 247);
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public FinancialTrackerHelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
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
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(HELP_URL);
        clipboard.setContent(url);
    }

    /**
     * Copies the summary command template to the clipboard.
     */
    @FXML
    private void copySummary() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(SUMMARY_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the help command template to the clipboard.
     */
    @FXML
    private void copyHelp() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(HELP);
        clipboard.setContent(url);
    }

    /**
     * Copies the goto command template to the clipboard.
     */
    @FXML
    private void copyGoto() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(GOTO_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the exit command template to the clipboard.
     */
    @FXML
    private void copyExit() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(EXIT_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the add command template to the clipboard.
     */
    @FXML
    private void copyAdd() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(ADD_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the delete command template to the clipboard.
     */
    @FXML
    private void copyDelete() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(DELETE_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the edit command template to the clipboard.
     */
    @FXML
    private void copyEdit() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(EDIT_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the sort command template to the clipboard.
     */
    @FXML
    private void copySort() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(SORT_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the switch command template to the clipboard.
     */
    @FXML
    private void copySwitch() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(SWITCH_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the clear command template to the clipboard.
     */
    @FXML
    private void copyClear() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(CLEAR_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the undo command template to the clipboard.
     */
    @FXML
    private void copyUndo() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(UNDO_MESSAGE);
        clipboard.setContent(url);
    }
}
