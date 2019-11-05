package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayIndicator;

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class LineChartController extends AxisController {
    private static final String FXML = "LineChartView.fxml";


    @FXML
    private LineChart<String, Integer> linechart;

    public LineChartController(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        super(FXML);
        requireNonNull(logic);
        requireNonNull(displayIndicator);

        initAttributes(logic, displayIndicator);
        requireNonNullAttributes();

        setChart();
    }

    @Override
    protected void setChart() {
        linechart.setTitle(title);
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);
        linechart.getData().add(series);
    }
}
