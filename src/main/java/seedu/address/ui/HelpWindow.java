package seedu.address.ui;

import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.FinSecParser;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s1-cs2103t-w12-1.github.io/main/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static TreeSet<String> defaultCommandList = new TreeSet<>(FinSecParser.getCommandList().values());
    private static String helpMsg = "The 'help' command format is as follows: "
            + "help cmd/COMMAND type/TYPE"
            + "\n\n"
            + "Command List: \n"
            + getDefaultCommandList().toString().replace(", ", "]\n[")
            + "\n\n"
            //+ "Shortcut List:\n"
            //+ FinSecParser.getShortcutList().toString()
            //    .replace(", ", "]\n[")
            //    .replace(" Word", "Shortcut")
            //    .replace("Task", "    Command")
            //+ "\n\n"
            + "Type List:\n"
            + "[brief] (gives a brief description)\n"
            + "[guide] (opens up our user guide on your browser) \n"
            + "[api] (for advanced users who want to know the inner workings of the command) \n\n"
            + "Example: help cmd/add_contact type/guide\n"
            + "For more information you can refer to the user guide by clicking on the button";

    @FXML
    private Button gotoButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(helpMsg);
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

    public static TreeSet<String> getDefaultCommandList() {
        return defaultCommandList;
    }

    public static String getHelpMsg() {
        return helpMsg;
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void gotoUrl() throws IOException {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(USERGUIDE_URL));
    }
}
