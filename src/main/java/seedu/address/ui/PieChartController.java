package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayIndicator;

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class PieChartController extends DisplayController {
    private static final String FXML = "PieChartView.fxml";

    private ObservableList<PieChart.Data> pieChartData;
    @FXML
    private PieChart piechart;

    public PieChartController(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        super(FXML);
        initAttributes(logic, displayIndicator);
        setChart();
    }

    @Override
    protected void initPolicyPopularityBreakdown(Logic logic) {
        pieChartData = castData(logic.getPolicyPopularityBreakdown());
    }

    @Override
    protected void initAgeGroupBreakdown(Logic logic) {
        pieChartData = castData(logic.getAgeGroupBreakdown());
    }

    @Override
    protected void initGenderBreakdown(Logic logic) {
        pieChartData = castData(logic.getGenderBreakdown());
    }

    @Override
    protected <T> T castData(ObservableMap<String, Integer> map) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        map.forEach((key, value) ->
            data.add(new PieChart.Data(key, value))
        );
        return (T) data;
    }

    @Override
    protected void setChart() {
        piechart.setData(pieChartData);
        piechart.setTitle(title);
    }
}
