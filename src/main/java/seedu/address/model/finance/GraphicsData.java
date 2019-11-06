package seedu.address.model.finance;

import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * Contains information on the statistic graphic to display.
 */
public class GraphicsData {

    private final String graphicType;
    private final ObservableList<PieChart.Data> pieChartData;
    private final ArrayList<XYChart.Series> barChartData;

    /**
     * Initialise with empty data;
     */
    public GraphicsData() {
        this.graphicType = "none";
        this.pieChartData = initPieChart();
        this.barChartData = initBarChart();
    }

    public GraphicsData(String graphicType, ObservableList<PieChart.Data> pieChartData,
                        ArrayList<XYChart.Series> barChartData) {
        this.graphicType = graphicType;
        this.pieChartData = pieChartData;
        this.barChartData = barChartData;
    }

    public String getGraphicType() {
        return graphicType;
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    public ArrayList<XYChart.Series> getBarChartData() {
        return barChartData;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GraphicsData // instanceof handles nulls
                && graphicType.equals(((GraphicsData) other).graphicType)
                && pieChartData.equals(((GraphicsData) other).pieChartData)
                && barChartData.equals(((GraphicsData) other).barChartData));
    }

    /**
     * Returns an "empty" piechart.
     */
    private ObservableList<PieChart.Data> initPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("", 0));
        return pieChartData;
    }

    /**
     * Returns an "empty" barchart.
     */
    private ArrayList<XYChart.Series> initBarChart() {
        ArrayList<XYChart.Series> barList = new ArrayList<XYChart.Series>();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("");
        series.getData().add(new XYChart.Data<String, Number>("", 0));
        barList.add(series);
        return barList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(graphicType, pieChartData, barChartData);
    }

}
