package seedu.moolah.ui.statistics;

import java.util.List;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.moolah.model.expense.Timestamp;

/**
 * A factory class to produce the Line Charts to compare the periodic
 * total against the budget limit for the Statistics Panel
 */
public class LineChartBudgetRegionFactory implements StatisticsRegionFactory {

    private final List<Double> periodicLimit;
    private final List<Double> periodicTotal;
    private final List<Timestamp> dates;
    private final String title;

    public LineChartBudgetRegionFactory(List<Timestamp> dates, List<Double> periodicTotal,
                                        List<Double> periodicLimit, String title) {
        this.dates = dates;
        this.periodicTotal = periodicTotal;
        this.periodicLimit = periodicLimit;
        this.title = title;
    }

    @Override
    public Region createRegion() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.getData().clear();

        XYChart.Series<String, Number> expenseTrendLine = new XYChart.Series<String, Number>();
        expenseTrendLine.setName("Total money spent");
        XYChart.Series<String, Number> budgetLimitTrendLine = new XYChart.Series<String, Number>();
        budgetLimitTrendLine.setName("Budget limit");
        //need a better hard-coded parser
        for (int i = 0; i < dates.size(); i++) {
            expenseTrendLine.getData().add(
                    new XYChart.Data<String, Number>(dates.get(i).showDate(), periodicTotal.get(i)));
            budgetLimitTrendLine.getData().add(
                    new XYChart.Data<String, Number>(dates.get(i).showDate(), periodicLimit.get(i)));
        }
        lineChart.getData().addAll(expenseTrendLine, budgetLimitTrendLine);

        return lineChart;
    }

    @Override
    public String getTitle() {
        return title;
    }





}
