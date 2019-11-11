package com.typee.ui;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL =
            "https://ay1920s1-cs2103t-f14-3.github.io/main/UserGuide.html";
    public static final String COMMAND_SUMMARY = "Command Summary:\n"
            + "Add: add t/ENGAGEMENT_TYPE s/START_TIME[dd/mm/YYYY/HHMM]"
            + " e/END_TIME[dd/mm/YYYY/HHMM] l/LOCATION d/DESCRIPTION a/ATTENDEES p/PRIORITY\n"
            + "Tab: tab b/MENU_NAME\n"
            + "\tEngagements Window: tab b/engagement\n"
            + "\tCalendar Window: tab b/calendar\n"
            + "\tGame Window: tab b/game\n"
            + "\tReport Window: tab b/report\n"
            + "Calendar Window: calendar c/CALENDAR_COMMAND\n"
            + "\tOpen Display: calendar c/opendisplay d/DATE\n"
            + "\tClose Display: calendar c/closedisplay d/DATE\n"
            + "\tNext Month: calendar c/nextmonth\n"
            + "\tPrevious Month: calendar c/previousmonth\n"
            + "Clear: clear\n"
            + "Delete: delete i/INDEX\n"
            + "PDF: pdf i/INDEX t/RECEIVER f/SENDER\n"
            + "Redo: redo\n"
            + "Sort: sort p/PROPERTY o/ORDER\n"
            + "Undo: undo\n"
            + "Help: help\n"
            + "List: list\n";

    public static final String HELP_MESSAGE = "Refer to our user guide for more information: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label commandSummary;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        commandSummary.setText(COMMAND_SUMMARY);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
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
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
