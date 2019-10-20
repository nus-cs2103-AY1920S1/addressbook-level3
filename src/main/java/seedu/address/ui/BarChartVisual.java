package seedu.address.ui;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayIndicator;

// TODO: Use images to style chart haha

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class BarChartVisual extends UiPart<Region> {
    private static final String FXML = "BarChartVisual.fxml";
    private static final String POLICY_POPULARITY_XAXIS = "Policy Type";
    private static final String POLICY_POPULARITY_YAXIS = "Number of policies sold";

    private static final String GENDER_BREAKDOWN_XAXIS = "Gender";
    private static final String GENDER_BREAKDOWN_YAXIS = "Population Size";

    private static final String AGE_GROUP_XAXIS = "Age Group";
    private static final String AGE_GROUP_YAXIS = "Population Size";

    private String title;
    private String xAxisLabel;
    private String yAxisLabel;
    private XYChart.Series<String, Integer> series;

    @FXML
    private BarChart<String, Integer> barchart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public BarChartVisual(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        super(FXML);
        setData(logic, displayIndicator);
    }

    private void setData(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        title = displayIndicator.toString();

        switch (displayIndicator.value) {
        case DisplayIndicator.POLICY_POPULARITY_BREAKDOWN:
            xAxisLabel = POLICY_POPULARITY_XAXIS;
            yAxisLabel = POLICY_POPULARITY_YAXIS;
            series = getData(logic.getPolicyPopularityBreakdown());
            break;
        case DisplayIndicator.AGE_GROUP_BREAKDOWN:
            xAxisLabel = AGE_GROUP_XAXIS;
            yAxisLabel = AGE_GROUP_YAXIS;
            series = getData(logic.getAgeGroupBreakdown());
            break;
        case DisplayIndicator.GENDER_BREAKDOWN:
            xAxisLabel = GENDER_BREAKDOWN_XAXIS;
            yAxisLabel = GENDER_BREAKDOWN_YAXIS;
            series = getData(logic.getGenderBreakdown());
            break;
        default:
            // TODO: display report as default instead
            throw new ParseException(DisplayIndicator.getMessageConstraints());
        }

        barchart.setTitle(title);
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);
        barchart.getData().add(series);
    }

    private XYChart.Series<String, Integer> getData(ObservableMap<String, Integer> map) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        map.forEach((key, value) ->
            series.getData().add(new XYChart.Data<>(key, value))
        );
        return series;
    }
}
