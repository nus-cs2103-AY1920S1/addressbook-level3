package com.typee.ui.calendar;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class IndividualDatePane extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private StackPane individualDatePane;

    private static final String FXML = "IndividualDatePane.fxml";

    /**
     * Constructs an individual date pane for use in the calendar window.
     */
    public IndividualDatePane() {
        super(FXML);
    }

    /**
     * Returns the stackpane that represents this individual date pane.
     * @return The stackpane that represents this individual date pane.
     */
    public StackPane getIndividualDatePane() {
        return individualDatePane;
    }

}
