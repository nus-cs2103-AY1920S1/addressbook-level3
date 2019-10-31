package io.xpire.ui;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ExportWindow extends UiPart<Stage> {

    public static final Logger logger = LogsCenter.getLogger(ExportWindow.class);
    public static final String FXML = "ExportWindow.fxml";

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
     * Creates a new HelpWindow.
     */
    public ExportWindow() {
        this(new Stage());
    }

    public void update(byte[] pngData) {
        Image qrCode = new Image(new ByteArrayInputStream(pngData));
        this.qrCode.setImage(qrCode);
    }

    public void show() {
        this.logger.fine("Showing QR Code to user");
        this.getRoot().show();
        this.getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return this.getRoot().isShowing();
    }

    public void hide() {
        this.getRoot().hide();
    }

    public void focus() {
        this.getRoot().requestFocus();
    }
}
