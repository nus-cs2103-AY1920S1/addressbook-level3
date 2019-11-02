package seedu.address.ui.stats;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.CategoryStatistics;
import seedu.address.model.statistics.DailyStatistics;
import seedu.address.ui.UiPart;

public class StatisticsBarChart extends UiPart<Region> {
    private static final String FXML = "/statistics/StatisticsBarChart.fxml";
    private ObservableList<DailyStatistics> statsForDaily;
    private XYChart.Series<String, Number> expenseChart;
    private XYChart.Series<String, Number> incomeChart;
    private ObservableList<String> listOfDays;
    private ObservableList<XYChart.Data<String,Number>> dataForExpense;
    private ObservableList<XYChart.Data<String,Number>> dataForIncome;
    private final Logger logger = LogsCenter.getLogger(StatisticsPieChart.class);

    @FXML
    private StackedBarChart barChart;

    @FXML
    private CategoryAxis dateOfRecord;

    @FXML
    private NumberAxis statisticsForDay;

    public StatisticsBarChart(ObservableList<DailyStatistics> statsForDaily) {
        super(FXML);
        this.statsForDaily = statsForDaily;
        this.expenseChart = new XYChart.Series<String, Number>();
        this.incomeChart = new XYChart.Series<String, Number>();
        this.dataForExpense = FXCollections.observableArrayList();
        this.expenseChart.setData(dataForExpense);
        this.dataForIncome = FXCollections.observableArrayList();
        this.expenseChart.setData(dataForIncome);
        statsForDaily.addListener(new ListChangeListener<DailyStatistics>() {
            @Override
            public void onChanged(Change<? extends DailyStatistics> change) {
                updateBarChart(statsForDaily);
            }
        });
        barChart.setTitle("Statistics Daily Summary");
        barChart.setLegendSide(Side.BOTTOM);
        dateOfRecord.setLabel("Date");
        statisticsForDay.setLabel("Daily Expense and Income");
        this.listOfDays = FXCollections.observableArrayList();
        dateOfRecord.setCategories(listOfDays);
        updateBarChart(statsForDaily);
    }

    /**
     * Updates the PieChart displayed by running a calculation on every notified change from the original statsMaplist.
     * @param statsMap contains a listener that calls for this method.
     */
    public void updateBarChart(ObservableList<DailyStatistics> statsForDaily) {
        this.expenseChart.getData().clear();
        this.incomeChart.getData().clear();
//        barChart.getData().clear();
        this.listOfDays.clear();
        dataForIncome.clear();
        dataForExpense.clear();
        int numberOfDays = statsForDaily.get(0).getNumberOfDays();
        for (int i = 1; i <= numberOfDays; i++) {
            listOfDays.add(i);
        }

        for (int i = 0; i < statsForDaily.size(); i++) {
            DailyStatistics t = statsForDaily.get(i);
            dataForExpense.add(new XYChart.Data<>(t.getDate(), t.getTotalExpense()));
            dataForIncome.add(new XYChart.Data<>(t.getDate(), t.getTotalIncome() * -1));
        }
//        if (toAdd.isEmpty()) {
//            statsPieChart.setVisible(false);
//            noEntryLabel.setVisible(true);
//        } else {
//            statsPieChart.setData(toAdd);
//            statsPieChart.setVisible(true);
//            noEntryLabel.setVisible(false);
//        }
    }
}
