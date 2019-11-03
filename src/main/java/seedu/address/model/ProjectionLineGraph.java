package seedu.address.model;

import java.util.stream.IntStream;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.address.model.util.GradientDescent;

/**
 * JavaFX class for rendering the projection line + scatter plot graph
 */
public class ProjectionLineGraph extends Stage {

    private GradientDescent gradientDescent;

    public ProjectionLineGraph(Projection projection) {

        this.gradientDescent = projection.getProjector();

        this.setTitle("Balance Projection");

        //define the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Days From Now");
        yAxis.setLabel("Balance ($)");
        xAxis.setAutoRanging(false);
        double xUnit = Math.round(gradientDescent.getDataRange() / 10);
        xAxis.setTickUnit(xUnit);
        xAxis.setLowerBound(gradientDescent.getMinData() - xUnit);
        xAxis.setUpperBound(gradientDescent.getMaxData() + xUnit);
        yAxis.setAutoRanging(false);
        double yUnit = Math.round(gradientDescent.getOutputRange() / 10);
        yAxis.setTickUnit(yUnit);
        yAxis.setLowerBound((gradientDescent.getMinOutput() / yUnit - 1) * yUnit);
        yAxis.setUpperBound((gradientDescent.getMaxOutput() / yUnit + 1) * yUnit);

        //creating the chart
        final ScatterChart<Number, Number> dataPlot = new ScatterChart<>(xAxis, yAxis);
        dataPlot.setLegendVisible(false);

        final LineChart<Number, Number> projectionLine = new LineChart<>(xAxis, yAxis);
        projectionLine.setLegendVisible(false);
        projectionLine.setCreateSymbols(true);
        projectionLine.setHorizontalGridLinesVisible(false);
        projectionLine.setVerticalGridLinesVisible(false);
        projectionLine.getXAxis().setVisible(false);
        projectionLine.getYAxis().setVisible(false);
        projectionLine.setTitle(String.format("Balance Projection over %.0f days", gradientDescent.getDataRange()));
        projectionLine.getStylesheets().addAll(getClass().getResource("/view/ProjectionLineChart.css").toExternalForm());

        //defining a series
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Actual Balance");
        IntStream.range(0, gradientDescent.getNumInputs()).forEach(x ->
                dataSeries.getData().add(new XYChart.Data(gradientDescent.getInputData(x),
                        gradientDescent.getActualValue(x)))
        );

        XYChart.Series projectionSeries = new XYChart.Series();
        projectionSeries.setName("Projection Line");
        projectionSeries.getData().add(new XYChart.Data(gradientDescent.getVariable(), gradientDescent.getResult()));
        projectionSeries.getData().add(new XYChart.Data(gradientDescent.getMinData(), gradientDescent.getMinOutput()));

        dataPlot.getData().add(dataSeries);
        projectionLine.getData().add(projectionSeries);
        StackPane chart = new StackPane();
        chart.getChildren().addAll(dataPlot, projectionLine);
        Scene scene = new Scene(chart, 800, 700);
        this.setScene(scene);
    }
}
