package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s1-cs2103t-f12-2.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "For detailed help, check out the User Guide:";
    public static final String QUICK_GUIDE = "Here are some Commands to get started: "
            + "(Note: Optional parameters are labelled in [ ] )\n"
            + "\t\u2022 Add a Patient - add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]\n"
            + "\t\u2022 Find a Patient - find KEYWORD [MORE KEYWORDS]\n"
            + "\t\u2022 Show all Patients - list\n"
            + "\t\u2022 Add a new Visit report for a patient - addvisit INDEX [v/DATE]\n"
            + "\t\u2022 Show a patient's full profile - profile INDEX\n"
            + "\t\u2022 Create a shortcut command - alias l/SHORTHAND v/COMMAND\n"
            + "\t\u2022 Delete a shortcut command - unalias SHORTHAND\n"
            + "\t\u2022 Create a Reminder - reminder TEXT [d/DAYS]";


    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label userGuideUrl;

    @FXML
    private Label quickGuide;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        userGuideUrl.setText(USERGUIDE_URL);
        quickGuide.setText(QUICK_GUIDE);
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
