package seedu.address.financialtracker.model.util;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public interface FinancialTrackerStatistics {
    ObservableList<PieChart.Data> getFinancialPieChartData();

    XYChart.Series<String, Number>getFinancialBarChartData();
}
