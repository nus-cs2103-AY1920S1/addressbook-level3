package seedu.address.model.projection;

import java.util.List;
import java.util.stream.IntStream;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;

/**
 * JavaFX class for rendering the projection line + scatter plot graph
 */
public class ProjectionGraph extends StackPane {

    private Projection projection;
    private GradientDescent gradientDescent;
    private List<Budget> budgets;
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
        this.projection = projection;
        this.gradientDescent = projection.getProjector();
        this.budgets = projection.getBudgets();

        // define axes
        this.xAxis = defineXAxis();
        this.yAxis = defineYAxis();

        // draw charts
        drawTransactionScatterChart();
        drawProjectionLineChart();
        if (this.budgets != null && !this.budgets.isEmpty()) {
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
        IntStream.range(0, this.budgets.size()).forEach(x -> {
            XYChart.Series<Number, Number> budgetSeries = defineBudgetSeries(x);
            budgetLine.getData().add(budgetSeries);
        });
        this.getChildren().add(budgetLine);
    }

    /**
     * @return a horizontal {@code LineChart} created based on the user's budget
     */
    private LineChart<Number, Number> createBudgetLine() {
        final LineChart<Number, Number> budgetLine = new LineChart<>(this.xAxis, this.yAxis);
        budgetLine.setHorizontalGridLinesVisible(false);
        budgetLine.setVerticalGridLinesVisible(false);
        budgetLine.getXAxis().setVisible(false);
        budgetLine.setTitle(String.format("Balance Projection over %.0f days", this.xRange));
        budgetLine.getStylesheets().addAll(getClass().getResource("/view/BudgetLineChart.css").toExternalForm());
        return budgetLine;
    }

    /**
     * @return a {@code ScatterChart} created based on the user's {@code transactionHistory}
     */
    private ScatterChart<Number, Number> createTransactionPlot() {
        final ScatterChart<Number, Number> transactionPlot = new ScatterChart<>(this.xAxis, this.yAxis);
        transactionPlot.getXAxis().setVisible(false);
        transactionPlot.setTitle(String.format("Balance Projection over %.0f days", this.xRange));
        return transactionPlot;
    }

    /**
     * @return a {@code LineChart} representing the best-fit projection line
     */
    private LineChart<Number, Number> createProjectionLine() {
        final LineChart<Number, Number> projectionLine = new LineChart<>(this.xAxis, this.yAxis);
        projectionLine.setVerticalGridLinesVisible(false);
        projectionLine.getXAxis().setVisible(false);
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
        projectionSeries.getData().add(new XYChart.Data<>(gradientDescent.getVariable(), gradientDescent.getResult()));
        projectionSeries.getData().add(new XYChart.Data<>(gradientDescent.getInputData(0),
                gradientDescent.predict(gradientDescent.getInputData(0)) / 100));
        projectionSeries.setName("Projection Line");
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
    private XYChart.Series<Number, Number> defineBudgetSeries(int idx) {
        XYChart.Series<Number, Number> budgetSeries = new XYChart.Series<>();
        budgetSeries.setName(this.budgets.get(idx).toString());
        budgetSeries.getData().add(new XYChart.Data<>(Date.daysBetween(Date.now(), this.budgets.get(idx).getStart()),
                projection.getBudgetStartValue(idx).getActualValue()));
        budgetSeries.getData().add(new XYChart.Data<>(Date.daysBetween(Date.now(), this.budgets.get(idx).getStart()),
                projection.getBudgetThreshold(idx).getActualValue()));
        budgetSeries.getData().add(new XYChart.Data<>(Date.daysBetween(Date.now(), this.budgets.get(idx).getDeadline()),
                projection.getBudgetThreshold(idx).getActualValue()));
        budgetSeries.getData().add(new XYChart.Data<>(Date.daysBetween(Date.now(), this.budgets.get(idx).getDeadline()),
                projection.getBudgetThreshold(idx).addAmount(projection.getBudgetProjection(idx)).getActualValue()));
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
        if (!this.budgets.isEmpty()) {
            this.xMin = Math.min(xMin, IntStream.range(0, this.budgets.size())
                    .mapToDouble(idx -> Date.daysBetween(Date.now(), this.projection.getBudgetStart(idx))).min()
                    .orElseThrow(AssertionError::new));
        }
        this.xMax = Math.max(gradientDescent.getVariable(), gradientDescent.getMaxData());
        if (!this.budgets.isEmpty()) {
            this.xMax = Math.max(xMax, IntStream.range(0, this.budgets.size())
                    .mapToDouble(idx -> Date.daysBetween(Date.now(), this.projection.getBudgetDeadline(idx))).max()
                    .orElseThrow(AssertionError::new));
        }
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
        if (!this.budgets.isEmpty()) {
            this.yMin = Math.min(yMin, IntStream.range(0, this.budgets.size())
                    .mapToDouble(idx -> this.projection.getBudgetStartValue(idx)
                            .getActualValue()).min()
                    .orElseThrow());
        }
        this.yMax = Math.max(gradientDescent.getResult(), gradientDescent.getMaxOutput());
        if (!this.budgets.isEmpty()) {
            this.yMax = Math.max(yMax, IntStream.range(0, this.budgets.size())
                    .mapToDouble(idx -> this.projection.getBudgetThreshold(idx).getActualValue()).max()
                    .orElseThrow(AssertionError::new));
        }
        this.yRange = yMax - yMin;
        this.yUnit = Math.round(yRange / 10);
        yAxis.setTickUnit(yUnit);
        yAxis.setLowerBound((Math.floor(yMin / yUnit) - 1) * yUnit);
        yAxis.setUpperBound((Math.ceil(yMax / yUnit) + 1) * yUnit);
        return yAxis;
    }
}
