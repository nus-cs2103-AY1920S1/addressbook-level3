package seedu.moolah.ui.statistics;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Timestamp;

/**
 * A factory class to produce the Line Charts for each category for the Statistics Panel
 */
public class LineChartCategoriesRegionFactory implements StatisticsRegionFactory {

    private final List<Timestamp> dates;
    private final List<ArrayList<Double>> periodicCategoricalExpenses;
    private final String title;

    public LineChartCategoriesRegionFactory(List<Timestamp> dates,
                                            List<ArrayList<Double>> periodicCategoricalExpenses, String title) {
        this.dates = dates;
        this.periodicCategoricalExpenses = periodicCategoricalExpenses;
        this.title = title;
    }

    @Override
    public Region createRegion() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.getData().clear();


        List<Category> categories = Category.getValidCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            XYChart.Series<String, Number> categoryTrendLine = new XYChart.Series<String, Number>();
            categoryTrendLine.setName(category.getCategoryName());
            ArrayList<Double> periodicCategoricalExpenditure = periodicCategoricalExpenses.get(i);
            int numberOfZeroes = 0;
            for (int j = 0; j < periodicCategoricalExpenditure.size(); j++) {
                double yValue = periodicCategoricalExpenditure.get(j);
                if (yValue == 0) {
                    numberOfZeroes++;
                }
                categoryTrendLine.getData().add(new XYChart.Data<String, Number>(dates.get(j).showDate(), yValue));
            }

            if (numberOfZeroes != periodicCategoricalExpenditure.size()) {
                lineChart.getData().add(categoryTrendLine);
            }
        }

        return lineChart;
    }


    @Override
    public String getTitle() {
        return title;
    }
}
