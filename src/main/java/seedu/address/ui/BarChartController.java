package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayIndicator;

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class BarChartController extends AxisController {
    private static final String FXML = "BarChartView.fxml";

    @FXML
    private BarChart<String, Integer> barchart;

    public BarChartController(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        super(FXML);
        requireNonNull(logic);
        requireNonNull(displayIndicator);
        initAttributes(logic, displayIndicator);
        setChart();
    }

    @Override
    protected void setChart() {
        barchart.setTitle(title);
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);
        barchart.getData().add(series);
    }
}
