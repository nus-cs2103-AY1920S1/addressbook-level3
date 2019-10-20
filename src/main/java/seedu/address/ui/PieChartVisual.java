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
public class PieChartVisual extends UiPart<Region> {
    private static final String FXML = "PieChartVisual.fxml";

    private ObservableList<PieChart.Data> pieChartData;
    private String title;
    @FXML
    private PieChart piechart;

    public PieChartVisual(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        super(FXML);
        setData(logic, displayIndicator);
    }

    private void setData(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        title = displayIndicator.toString();

        switch (displayIndicator.value) {
        case DisplayIndicator.POLICY_POPULARITY_BREAKDOWN:
            pieChartData = getData(logic.getPolicyPopularityBreakdown());
            break;
        case DisplayIndicator.AGE_GROUP_BREAKDOWN:
            pieChartData = getData(logic.getAgeGroupBreakdown());
            break;
        case DisplayIndicator.GENDER_BREAKDOWN:
            pieChartData = getData(logic.getGenderBreakdown());
            break;
        default:
            throw new ParseException(DisplayIndicator.getMessageConstraints());
        }

        piechart.setData(pieChartData);
        piechart.setTitle(title);
    }

    private ObservableList<PieChart.Data> getData(ObservableMap<String, Integer> map) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        map.forEach((key, value) ->
            data.add(new PieChart.Data(key, value))
        );
        return data;
    }
}
