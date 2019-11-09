package seedu.address.diaryfeature.model.util;

import javafx.scene.chart.XYChart;

public interface DiaryBookStatistics {

    int getTotalDiaryEntries();

    XYChart.Series<String, Number> getDiaryChart();
}
