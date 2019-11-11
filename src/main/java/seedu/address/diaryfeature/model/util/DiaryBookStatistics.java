package seedu.address.diaryfeature.model.util;

import javafx.scene.chart.XYChart;

/**
 * Statistics for {@link seedu.address.diaryfeature.model.DiaryBook}.
 */
public interface DiaryBookStatistics {

    /**
     * Get total number of diary entries.
     *
     * @return total number of diary entries
     */
    int getTotalDiaryEntries();

    /**
     * Get bar chart data for diary book.
     *
     * @return series of bar chart data for diary book
     */
    XYChart.Series<String, Number> getDiaryBarChart();
}
