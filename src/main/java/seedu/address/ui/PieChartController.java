package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayIndicator;

/**
 * An UI component that displays the policy popularity breakdown in the display panel.
 */
public class PieChartController extends DisplayController {
    private static final String FXML = "PieChartView.fxml";

    private static final String ASSERTION_MESSAGE =
        "Variable needs to be of type ObservableList<PieChart.Data>.";
    private static final String EXCEPTION_MESSAGE =
        "Generic needs to be of type ObservableList<PieChart.Data>. "
            + "Check type inference in the usage of this generic method";

    private ObservableList<PieChart.Data> pieChartData;
    @FXML
    private PieChart piechart;

    public PieChartController(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        super(FXML);
        requireNonNull(logic);
        requireNonNull(displayIndicator);

        initAttributes(logic, displayIndicator);
        requireNonNullAttributes();

        setChart();
    }

    @Override
    protected void initPolicyPopularityBreakdown(Logic logic) {
        assert pieChartData instanceof ObservableList : ASSERTION_MESSAGE;
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

        T result;
        try {
            result = (T) data;
        } catch (ClassCastException e) {
            throw new ClassCastException(EXCEPTION_MESSAGE);
        }

        return result;
    }

    @Override
    protected void setChart() {
        piechart.setData(pieChartData);
        piechart.setTitle(title);
    }

    @Override
    protected void requireNonNullAttributes() {
        requireNonNull(title);
        requireNonNull(pieChartData);
    }
}
