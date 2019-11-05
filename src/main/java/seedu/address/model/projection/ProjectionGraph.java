package seedu.address.model.projection;

import java.util.stream.IntStream;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;
import seedu.address.model.util.GradientDescent;

/**
 * JavaFX class for rendering the projection line + scatter plot graph
 */
public class ProjectionGraph extends StackPane {

    private GradientDescent gradientDescent;
    private Budget budget;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private double xMax;
    private double xMin;
    private double xUnit;
    private double xRange;
    private double yMax;
    private double yMin;
    private double yUnit;
    private double yRange;

    ProjectionGraph(Projection projection) {

        this.gradientDescent = projection.getProjector();
        this.budget = projection.getBudget().orElse(null);

        // define axes
        this.xAxis = defineXAxis();
        this.yAxis = defineYAxis();

        // draw charts
        drawTransactionScatterChart();
        drawProjectionLineChart();
        if (this.budget != null) {
            drawBudgetLineChart();
        }
    }

    /**
     * Draws the projection line chart
     */
    private void drawProjectionLineChart() {
        LineChart<Number, Number> projectionLine = createProjectionLine();
        XYChart.Series<Number, Number> projectionSeries = defineProjectionSeries();
        projectionLine.getData().add(projectionSeries);
        this.getChildren().add(projectionLine);
    }

    /**
     * Plots the transaction scatter chart
     */
    private void drawTransactionScatterChart() {
        ScatterChart<Number, Number> transactionPlot = createTransactionPlot();
        XYChart.Series<Number, Number> transactionSeries = defineTransactionSeries();
        transactionPlot.getData().add(transactionSeries);
        this.getChildren().add(transactionPlot);
    }

    /**
     * Draws the budget line chart
     */
    private void drawBudgetLineChart() {
        LineChart<Number, Number> budgetLine = createBudgetLine();
        XYChart.Series<Number, Number> budgetSeries = defineBudgetSeries();
        budgetLine.getData().add(budgetSeries);
        this.getChildren().add(budgetLine);
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
        budgetLine.getStylesheets().addAll(getClass().getResource("/view/BudgetLineChart.css").toExternalForm());
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
        projectionLine.setCreateSymbols(false);
        projectionLine.setTitle(String.format("Balance Projection over %.0f days", this.xRange));
        projectionLine.getStylesheets().addAll(getClass()
                .getResource("/view/ProjectionLineChart.css").toExternalForm());
        return projectionLine;
    }

    /**
     * @return an {@code XYChart.Series} denoting the two endpoints of the best-fit projection line
     */
    private XYChart.Series<Number, Number> defineProjectionSeries() {
        XYChart.Series<Number, Number> projectionSeries = new XYChart.Series<>();
        projectionSeries.setName("Projection Line");
        projectionSeries.getData().add(new XYChart.Data<>(gradientDescent.getVariable(), gradientDescent.getResult()));
        projectionSeries.getData().add(
                new XYChart.Data<>(gradientDescent.getMinData(), gradientDescent.getMinOutput()));
        return projectionSeries;
    }

    /**
     * @return an {@code XYChart.Series} representing various balance states through time
     */
    private XYChart.Series<Number, Number> defineTransactionSeries() {
        XYChart.Series<Number, Number> transactionSeries = new XYChart.Series<>();
        transactionSeries.setName("Actual Balance");
        IntStream.range(0, gradientDescent.getNumInputs()).forEach(x ->
                transactionSeries.getData().add(new XYChart.Data<>(gradientDescent.getInputData(x),
                        gradientDescent.getActualValue(x)))
        );
        return transactionSeries;
    }

    /**
     * @return an {@code XYChart.Series} representing the user's budget
     */
    private XYChart.Series<Number, Number> defineBudgetSeries() {
        XYChart.Series<Number, Number> budgetSeries = new XYChart.Series<>();
        budgetSeries.setName("Budget Line");
        budgetSeries.getData().add(new XYChart.Data<>(this.xAxis.getLowerBound() - this.xUnit,
                this.budget.getBudget().getActualValue()));
        budgetSeries.getData().add(new XYChart.Data<>(Date.daysBetween(Date.now(), this.budget.getDeadline()),
                budget.getBudget().getActualValue()));
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
        this.xMin = Math.min(gradientDescent.getVariable(), gradientDescent.getMinData());
        this.xMax = Math.max(gradientDescent.getVariable(), gradientDescent.getMaxData());
        this.xRange = xMax - xMin;
        this.xUnit = Math.round(xRange / 10);
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
        this.yMin = Math.min(gradientDescent.getResult(), gradientDescent.getMinOutput());
        if (this.budget != null) {
            this.yMin = Math.min(yMin, this.budget.getBudget().getActualValue());
        }
        this.yMax = Math.max(gradientDescent.getResult(), gradientDescent.getMaxOutput());
        if (this.budget != null) {
            this.yMax = Math.max(yMax, this.budget.getBudget().getActualValue());
        }
        this.yRange = yMax - yMin;
        this.yUnit = Math.round(yRange / 10);
        yAxis.setTickUnit(yUnit);
        yAxis.setLowerBound((Math.floor(yMin / yUnit) - 1) * yUnit);
        yAxis.setUpperBound((Math.ceil(yMax / yUnit) + 1) * yUnit);
        return yAxis;
    }
}
