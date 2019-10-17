package com.typee.ui;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * Controller class for Report Window.
 */
public class ReportWindow extends UiPart<Region> {
    public static final String FXML = "ReportWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnTest;

    public ReportWindow() {
        super(FXML);
    }

    @FXML
    private void setTextForLabel(MouseEvent event) {
        lblStatus.setText("Testing report window");
    }
}
