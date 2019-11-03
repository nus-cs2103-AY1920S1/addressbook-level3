//package com.dukeacademy.ui;
//
//import java.util.logging.Logger;
//
//import com.dukeacademy.commons.core.LogsCenter;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.input.Clipboard;
//import javafx.scene.input.ClipboardContent;
//import javafx.stage.Stage;
//
///**
// * Controller for a help page
// */
//class HelpWindow extends UiPart<Stage> {
//
//    /**
//     * The constant USERGUIDE_URL.
//     */
//    private static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
//    /**
//     * The constant HELP_MESSAGE.
//     */
//    private static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
//
//    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
//    private static final String FXML = "HelpWindow.fxml";
//
//    @FXML
//    private Button copyButton;
//
//    @FXML
//    private Label helpMessage;
//
//    /**
//     * Creates a new HelpWindow.
//     *
//     * @param root Stage to use as the root of the HelpWindow.
//     */
//    private HelpWindow(Stage root) {
//        super(FXML, root);
//        helpMessage.setText(HELP_MESSAGE);
//        root.sizeToScene();
//    }
//
//    /**
//     * Creates a new HelpWindow.
//     */
//    public HelpWindow() {
//        this(new Stage());
//    }
//
//    /**
//     * Shows the help window.
//     *
//     * @throws IllegalStateException <ul>
//     *     <li> if this method is called on a thread other than the JavaFX Application Thread.     </li>
//     *     <li>         if this method is called during animation or layout processing. </li>
//     *     <li>         if this method is called on the primary stage.     </li>
//     *     <li>         if {@code dialogStage} is already showing.     </li>
//     * </ul>
//     */
//    public void show() {
//        logger.fine("Showing help page about the application.");
//        getRoot().show();
//        getRoot().centerOnScreen();
//    }
//
//    /**
//     * Returns true if the help window is currently being shown.
//     *
//     * @return the boolean
//     */
//    public boolean isShowing() {
//        return getRoot().isShowing();
//    }
//
//    /**
//     * Hides the help window.
//     */
//    public void hide() {
//        getRoot().hide();
//    }
//
//    /**
//     * Focuses on the help window.
//     */
//    public void focus() {
//        getRoot().requestFocus();
//    }
//
//    /**
//     * Copies the URL to the user guide to the clipboard.
//     */
//    @FXML
//    private void copyUrl() {
//        final Clipboard clipboard = Clipboard.getSystemClipboard();
//        final ClipboardContent url = new ClipboardContent();
//        url.putString(USERGUIDE_URL);
//        clipboard.setContent(url);
//    }
//}
