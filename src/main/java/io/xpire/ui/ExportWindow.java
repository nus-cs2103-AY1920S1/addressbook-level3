package io.xpire.ui;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

//@@author JermyTan
/**
 * Controller for a export screen.
 */
public class ExportWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ExportWindow.class);
    private static final String FXML = "ExportWindow.fxml";

    @FXML
    private ImageView qrCode;

    /**
     * Creates a new ExportWindow.
     *
     * @param root Stage to use as the root of the ExportWindow.
     *
     */
    public ExportWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ExportWindow.
     */
    public ExportWindow() {
        this(new Stage());
    }

    /**
     * Renders the QR code.
     *
     * @param pngData Data containing the QR code.
     */
    public void update(byte[] pngData) {
        Image qrCode = new Image(new ByteArrayInputStream(pngData));
        this.qrCode.setImage(qrCode);
    }

    /**
     * Shows the export window.
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
        this.logger.fine("Showing QR Code to user");
        this.getRoot().show();
        this.getRoot().centerOnScreen();
    }

    /**
     * Returns true if the export window is currently being shown.
     */
    public boolean isShowing() {
        return this.getRoot().isShowing();
    }

    /**
     * Hides the export window.
     */
    public void hide() {
        this.getRoot().hide();
    }

    /**
     * Focuses on the export window.
     */
    public void focus() {
        this.getRoot().requestFocus();
    }
}
