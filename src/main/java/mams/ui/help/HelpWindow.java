package mams.ui.help;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mams.commons.core.LogsCenter;
import mams.ui.UiPart;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s1-cs2103-t11-2.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    public static final String YELLOW_LABEL_STYLE_CLASS = "prefix_yellow";
    public static final String PURPLE_LABEL_STYLE_CLASS = "prefix_purple";
    public static final String RED_LABEL_STYLE_CLASS = "prefix_red";
    public static final String SKY_BLUE_LABEL_CLASS = "prefix_sky_blue";
    public static final String PINK_LABEL_STYLE_CLASS = "prefix_pink";
    public static final String GREEN_LABEL_STYLE_CLASS = "prefix_green";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    public static final List<String> LABEL_STYLES = Arrays.asList(YELLOW_LABEL_STYLE_CLASS,
            PURPLE_LABEL_STYLE_CLASS, RED_LABEL_STYLE_CLASS, SKY_BLUE_LABEL_CLASS,
            PINK_LABEL_STYLE_CLASS, GREEN_LABEL_STYLE_CLASS);

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
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
        changeHelpMessageStyle(); // see #changeHelpMessageStyle() for why this is needed.
    }

    /**
     * Changes the background fill for the helpMessage {@code Label} to a random colour. The reason
     * why this is needed is due to a bug on JavaFX on some computers where the help window will
     * not sometimes not load on re-opening until an event is fired (or until something "prompts" the framework).
     * By changing the Label style randomly on each re-open, we prevent this from happening. Besides,
     * it also looks nice.
     */
    public void changeHelpMessageStyle() {
        helpMessage.getStyleClass().removeAll(LABEL_STYLES);
        Random random = new Random();
        String randomStyleClass = LABEL_STYLES.get(random.nextInt(LABEL_STYLES.size()));
        helpMessage.getStyleClass().add(randomStyleClass);
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
        // unlike #close(), this is not explicitly needed here (see #changeHelpMessageStyle)
        // just purely for the visual effects.
        changeHelpMessageStyle();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    public void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Handles {@code KeyEvent} for the help window.
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
