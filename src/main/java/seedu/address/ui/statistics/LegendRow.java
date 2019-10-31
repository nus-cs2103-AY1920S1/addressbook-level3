package seedu.address.ui.statistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.address.ui.UiPart;

/**
 * Represents a ui for a legend symbol and legend description.
 */
public class LegendRow extends UiPart<Region> {

    private static final String FXML = "LegendRow.fxml";

    @FXML
    private Rectangle lineChartSymbol;

    @FXML
    private Label lineChartDescription;

    public LegendRow(Color color, String legendDescription) {
        super(FXML);
        lineChartSymbol.setFill(color);
        lineChartDescription.setText(legendDescription);
    }
}
