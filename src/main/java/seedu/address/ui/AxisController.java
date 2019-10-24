package seedu.address.ui;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seedu.address.logic.Logic;

/**
 * Controller for charts with X-Y axis.
 */
public abstract class AxisController extends DisplayController {
    private static final String FXML = "BarChartView.fxml";
    private static final String POLICY_POPULARITY_XAXIS = "Policy Type";
    private static final String POLICY_POPULARITY_YAXIS = "Number of policies sold";

    private static final String GENDER_BREAKDOWN_XAXIS = "Gender";
    private static final String GENDER_BREAKDOWN_YAXIS = "Population Size";

    private static final String AGE_GROUP_XAXIS = "Age Group";
    private static final String AGE_GROUP_YAXIS = "Population Size";

    protected String xAxisLabel;
    protected String yAxisLabel;
    protected XYChart.Series<String, Integer> series;

    @FXML
    protected CategoryAxis xAxis;

    @FXML
    protected NumberAxis yAxis;

    public AxisController(String fxmlFileName) {
        super(fxmlFileName);
    }

    @Override
    protected void initPolicyPopularityBreakdown(Logic logic) {
        xAxisLabel = POLICY_POPULARITY_XAXIS;
        yAxisLabel = POLICY_POPULARITY_YAXIS;
        series = castData(logic.getPolicyPopularityBreakdown());
    }

    @Override
    protected void initAgeGroupBreakdown(Logic logic) {
        xAxisLabel = AGE_GROUP_XAXIS;
        yAxisLabel = AGE_GROUP_YAXIS;
        series = castData(logic.getAgeGroupBreakdown());
    }

    @Override
    protected void initGenderBreakdown(Logic logic) {
        xAxisLabel = GENDER_BREAKDOWN_XAXIS;
        yAxisLabel = GENDER_BREAKDOWN_YAXIS;
        series = castData(logic.getGenderBreakdown());
    }

    @Override
    protected <T> T castData(ObservableMap<String, Integer> map) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        map.forEach((key, value) ->
            series.getData().add(new XYChart.Data<>(key, value))
        );
        return (T) series;
    }

    @Override
    protected abstract void setChart();

}
