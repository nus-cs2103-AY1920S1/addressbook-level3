package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.moneygowhere.commons.core.LogsCenter;

/**
 * Tab containing the spending graph.
 */
public class GraphPanel extends UiPart<Region> {
    private static final String FXML = "PlaceholderPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GraphPanel.class);

    @FXML
    private StackPane panePlaceholder;

    public GraphPanel(LinkedHashMap<String, Double> graphData) {
        super(FXML);
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount spent ($)");
        LineChart<String, Number> spendingChart = new LineChart<>(xAxis, yAxis);

        spendingChart.setTitle("Graph");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Spending");
        for (Map.Entry<String, Double> i : graphData.entrySet()) {
            XYChart.Data<String, Number> dataToAdd = new XYChart.Data<>(i.getKey().toString(),
                    Math.round(i.getValue()));
            series.getData().add(dataToAdd);
        }
        spendingChart.getData().add(series);

        for (XYChart.Series<String, Number> s : spendingChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(
                        "Date: \t" + d.getXValue() + "\n"
                                + "Spending: \t$" + d.getYValue() + ".00"));

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
        panePlaceholder.getChildren().add(spendingChart);
    }

}
