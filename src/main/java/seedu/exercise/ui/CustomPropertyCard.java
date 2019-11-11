package seedu.exercise.ui;

import static seedu.exercise.ui.util.LabelUtil.setLabelTooltip;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Represents a UI component that displays a custom property information.
 */
public class CustomPropertyCard extends UiPart<Region> {
    private static final String FXML = "CustomPropertyCard.fxml";

    @FXML
    private Label propertyAndValue;

    public CustomPropertyCard(String customPropertyDisplayResult) {
        super(FXML);
        this.propertyAndValue.setText(customPropertyDisplayResult);
        setLabelTooltip(propertyAndValue);
    }

}
