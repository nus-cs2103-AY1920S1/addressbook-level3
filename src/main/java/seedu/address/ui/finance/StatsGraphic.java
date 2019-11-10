package seedu.address.ui.finance;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import seedu.address.model.finance.GraphicsData;

/**
 * Panel showing statistical graphs.
 */
public class StatsGraphic extends UiPart<Region> {

    private static final String FXML = "StatsGraphic.fxml";

    @FXML
    private PieChart pieChart;
    @FXML
    private VBox barChartPlaceholder;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xLabel;
    @FXML
    private NumberAxis yLabel;
    @FXML
    private Label graphicsNullLabel;

    public StatsGraphic(GraphicsData gData) {
        super(FXML);
        String graphicsType = gData.getGraphicType();

        // Pie chart
        pieChart.setData(gData.getPieChartData());
        pieChart.setLabelsVisible(true);
        //@@author jewelsea
        //Reused from https://stackoverflow.com/questions/35479375/display-
        // additional-values-in-pie-chart to add values beside label name
        pieChart.getData()
                .forEach(data -> data.nameProperty().bind(
                            Bindings.concat(data.getName(), ": ", data.pieValueProperty())
                    ));

        // Bar chart
        xLabel.setLabel("Group");
        xLabel.tickLabelFontProperty().set(Font.font(15));
        xLabel.setTickLabelFill(Color.BLACK);
        yLabel.setLabel("Total Amount");
        yLabel.tickLabelFontProperty().set(Font.font(15));
        yLabel.setTickLabelFill(Color.BLACK);

        ArrayList<XYChart.Series> barSeriesList = gData.getBarChartData();
        for (XYChart.Series series : barSeriesList) {
            barChart.getData().addAll(series);
        }

        // No graphic
        graphicsNullLabel.setText("No statistics to show.");

        // Set appropriate graphic visible
        pieChart.setVisible(false);
        barChartPlaceholder.setVisible(false);
        graphicsNullLabel.setVisible(false);

        if (graphicsType.equalsIgnoreCase("pie")) {
            pieChart.setVisible(true);
        } else if (graphicsType.equalsIgnoreCase("bar")) {
            barChartPlaceholder.setVisible(true);
        } else {
            graphicsNullLabel.setVisible(true);
        }
    }
}
