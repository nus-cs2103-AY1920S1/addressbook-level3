package seedu.address.model.statistics;

import java.util.List;

import javafx.scene.chart.XYChart;
import seedu.address.model.question.Difficulty;

/**
 * Represents a stack bar chart series.
 */
public class StackBarChartModel {

    private Difficulty difficulty;
    private List<XYChart.Data<String, Number>> data;

    /**
     * Creates a series with the specified difficulty and data.
     * @param difficulty The difficulty to be added into the series.
     * @param data The data to be added into the series.
     */
    public StackBarChartModel(Difficulty difficulty, List<XYChart.Data<String, Number>> data) {
        this.difficulty = difficulty;
        this.data = data;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<XYChart.Data<String, Number>> getData() {
        return data;
    }
}
