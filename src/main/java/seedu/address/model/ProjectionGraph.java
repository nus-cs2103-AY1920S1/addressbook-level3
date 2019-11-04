package seedu.address.model;

import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import seedu.address.model.transaction.Budget;
import seedu.address.model.util.GradientDescent;

/**
 * JavaFX class for rendering the projection line + scatter plot graph
 */
public class ProjectionGraph extends StackPane {

    private GradientDescent gradientDescent;
    private ObservableList<Budget> budgetList;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public ProjectionGraph(Projection projection) {

        this.gradientDescent = projection.getProjector();
        this.budgetList = projection.getBudgetList();

        this.xAxis = defineXAxis();
        this.yAxis = defineYAxis();

        // Create the charts
        ScatterChart<Number, Number> transactionPlot = createTransactionPlot();
        LineChart<Number, Number> projectionLine = createProjectionLine();
        LineChart<Number, Number> budgetLine = createBudgetLine();

        // Define series
        XYChart.Series transactionSeries = defineTransactionSeries();
        XYChart.Series projectionSeries = defineProjectionSeries();
        XYChart.Series budgetSeries = defineBudgetSeries();

        // Add series to charts
        transactionPlot.getData().add(transactionSeries);
        projectionLine.getData().add(projectionSeries);
        budgetLine.getData().add(budgetSeries);
        this.getChildren().addAll(transactionPlot, projectionLine, budgetLine);
    }


    /**
     * @return a horizontal {@code LineChart} created based on the user's budget
     */
    private LineChart<Number, Number> createBudgetLine() {
        final LineChart<Number, Number> budgetLine = new LineChart<>(this.xAxis, this.yAxis);
        budgetLine.setLegendVisible(false);
        budgetLine.setCreateSymbols(true);
        budgetLine.setHorizontalGridLinesVisible(false);
        budgetLine.setVerticalGridLinesVisible(false);
        budgetLine.getXAxis().setVisible(false);
        budgetLine.getYAxis().setVisible(false);
        budgetLine.getStylesheets().addAll(getClass().getResource("/view/ProjectionGraph.css").toExternalForm());
        return budgetLine;
    }

    /**
     * @return a {@code ScatterChart} created based on the user's {@code transactionHistory}
     */
    private ScatterChart<Number, Number> createTransactionPlot() {
        final ScatterChart<Number, Number> transactionPlot = new ScatterChart<>(this.xAxis, this.yAxis);
        transactionPlot.setLegendVisible(false);
        return transactionPlot;
    }

    /**
     * @return a {@code LineChart} representing the best-fit projection line
     */
    private LineChart<Number, Number> createProjectionLine() {
        final LineChart<Number, Number> projectionLine = new LineChart<>(this.xAxis, this.yAxis);
        projectionLine.setLegendVisible(false);
        projectionLine.setCreateSymbols(true);
        projectionLine.setHorizontalGridLinesVisible(false);
        projectionLine.setVerticalGridLinesVisible(false);
        projectionLine.getXAxis().setVisible(false);
        projectionLine.getYAxis().setVisible(false);
        projectionLine.setTitle(String.format("Balance Projection over %.0f days", gradientDescent.getDataRange()));
        projectionLine.getStylesheets().addAll(getClass().getResource("/view/ProjectionGraph.css").toExternalForm());
        return projectionLine;
    }

    /**
     * @return an {@code XYChart.Series} denoting the two endpoints of the best-fit projection line
     */
    private XYChart.Series defineProjectionSeries() {
        XYChart.Series projectionSeries = new XYChart.Series();
        projectionSeries.setName("Projection Line");
        projectionSeries.getData().add(new XYChart.Data(gradientDescent.getVariable(), gradientDescent.getResult()));
        projectionSeries.getData().add(new XYChart.Data(gradientDescent.getMinData(), gradientDescent.getMinOutput()));
        return projectionSeries;
    }

    /**
     * @return an {@code XYChart.Series} representing various balance states through time
     */
    private XYChart.Series defineTransactionSeries() {
        XYChart.Series transactionSeries = new XYChart.Series();
        transactionSeries.setName("Actual Balance");
        IntStream.range(0, gradientDescent.getNumInputs()).forEach(x ->
                transactionSeries.getData().add(new XYChart.Data(gradientDescent.getInputData(x),
                        gradientDescent.getActualValue(x)))
        );
        return transactionSeries;
    }

    /**
     * @return an {@code XYChart.Series} representing the user's budget
     */
    private XYChart.Series defineBudgetSeries() {
        XYChart.Series budgetSeries = new XYChart.Series();
        budgetSeries.setName("Budget Line");
        budgetSeries.getData().add(new XYChart.Data(gradientDescent.getMinData(),
                budgetList.get(0).getBudget().getIntegerValue())); //TODO: remove stub
        budgetSeries.getData().add(new XYChart.Data(gradientDescent.getMaxData(),
                budgetList.get(0).getBudget().getIntegerValue())); //TODO: remove stub
        return budgetSeries;
    }

    /**
     * Defines the x-axis of the {@code ProjectionGraph}
     * @return the x-axis of the {@code ProjectionGraph}
     */
    private NumberAxis defineXAxis() {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Days From Now");
        xAxis.setAutoRanging(false);
        double xMin = Math.min(gradientDescent.getVariable(), gradientDescent.getMinData());
        double xMax = Math.max(gradientDescent.getVariable(), gradientDescent.getMaxData());
        double xRange = xMax - xMin;
        double xUnit = Math.round(xRange / 10);
        xAxis.setTickUnit(xUnit);
        xAxis.setLowerBound((Math.floor(xMin / xUnit) - 1) * xUnit);
        xAxis.setUpperBound((Math.ceil(xMax / xUnit) + 1) * xUnit);
        return xAxis;
    }

    /**
     * Defines the y-axis of the {@code ProjectionGraph}
     * @return the y-axis of the {@code ProjectionGraph}
     */
    private NumberAxis defineYAxis() {
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Balance ($)");
        yAxis.setAutoRanging(false);
        double yMin = Math.min(gradientDescent.getResult(), gradientDescent.getMinOutput());
        double yMax = Math.max(gradientDescent.getResult(), gradientDescent.getMaxOutput());
        double yRange = yMax - yMin;
        double yUnit = Math.round(yRange / 10);
        yAxis.setTickUnit(yUnit);
        yAxis.setLowerBound((Math.floor(yMin / yUnit) - 1) * yUnit);
        yAxis.setUpperBound((Math.ceil(yMax / yUnit) + 1) * yUnit);
        return yAxis;
    }
}
