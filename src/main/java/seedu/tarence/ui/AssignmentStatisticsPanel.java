package seedu.tarence.ui;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;

/**
 * Panel containing statistics display for assignments.
 */
public class AssignmentStatisticsPanel extends UiPart<Region> {
    private static final String FXML = "AssignmentStatisticsPanel.fxml";
    private static final int NUM_OF_COLS = 5;
    private final Logger logger = LogsCenter.getLogger(AssignmentStatisticsPanel.class);
    private Assignment assignment;
    private List<Integer> scores;
    private HashMap<Integer, Integer> distribution;
    private int[] histogramData;
    private int columnRange;
    private int lowestRange;
    private int highestRange;

    @FXML
    private VBox cardPane;

    @FXML
    private BarChart scoreBarChart;

    @FXML
    private Label maxScore;

    @FXML
    private Label median;

    @FXML
    private Label upperPercentile;

    @FXML
    private Label lowerPercentile;

    public AssignmentStatisticsPanel(Map<Student, Integer> resultInfo, Assignment assignment) {
        super(FXML);
        requireAllNonNull(resultInfo, assignment);
        this.assignment = assignment;
        scores = getScores(resultInfo);
        cardPane = new VBox();
        if (!scores.isEmpty()) {
            Collections.sort(scores);
            setStatistics(assignment);
            createHistogram();
            cardPane.getChildren().addAll(scoreBarChart, maxScore, median, upperPercentile, lowerPercentile);
        } else {
            cardPane.getChildren().add(getEmptyLabel());
        }
    }

    /**
     * Generates a histogram containing the score distribution of the students.
     */
    private void createHistogram() {
        groupData();
        groupHistogramData();
        XYChart.Series series = new XYChart.Series();

        for (int i = 1; i <= NUM_OF_COLS; i++) {
            String range;
            int upperBound = i * columnRange + lowestRange - 1;
            if (upperBound > assignment.getMaxScore()) {
                upperBound = assignment.getMaxScore();
            }
            range = (((i - 1) * columnRange) + lowestRange) + "-" + upperBound;
            series.getData().add(new XYChart.Data(range, histogramData[i - 1]));
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        scoreBarChart = new BarChart(xAxis, yAxis);
        scoreBarChart.setTitle("Score distribution for " + assignment.getAssignName());
        scoreBarChart.getYAxis().setLabel("Frequency");
        scoreBarChart.getXAxis().setLabel("Score");
        scoreBarChart.setLegendVisible(false);
        scoreBarChart.getYAxis().setTickLabelsVisible(false);
        scoreBarChart.autosize();
        scoreBarChart.getData().add(series);
        scoreBarChart.setStyle("-fx-padding: 10 10 30 10;");
        scoreBarChart.setCategoryGap(0);
        scoreBarChart.setBarGap(0);
    }

    /**
     * @return Pane with attendance table to display.
     */
    public Pane getPane() {
        return this.cardPane;
    }

    /**
     * Sets statistic labels based on the scores from the assignment
     */
    private void setStatistics(Assignment assignment) {
        maxScore.setText("Maximum score: " + assignment.getMaxScore());
        median.setText("Median score: " + calcPercentile(50));
        upperPercentile.setText("75th percentile: " + calcPercentile(75));
        lowerPercentile.setText("25th percentile: " + calcPercentile(25));
    }

    /**
     * calculates the given percentile from the hashmap values.
     * @param percentile - percentile to calculate.
     */
    private long calcPercentile(double percentile) {
        int index = (int) Math.ceil(((double) percentile / (double) 100) * (double) scores.size());
        return scores.get(index - 1);
    }

    /**
     * Groups the data of the scores based on their frequency.
     */
    private void groupData() {
        distribution = new HashMap<>();
        for (int i = 0; i < scores.size(); i++) {
            if (distribution.containsKey(scores.get(i))) {
                distribution.put(scores.get(i), distribution.get(scores.get(i)) + 1);
            } else {
                distribution.put(scores.get(i), 1);
            }
        }
    }

    private List<Integer> getScores(Map<Student, Integer> resultInfo) {
        return resultInfo.values().stream().filter(i -> i != -1).collect(Collectors.toList());
    }

    private Label getEmptyLabel() {
        String emptyListMessage = "Sorry :( there are no scores to display";
        return new Label(emptyListMessage);
    }

    /**
     * Groups the frequency data into 5 columns for the histogram.
     */
    private void groupHistogramData() {
        logger.info("Histogram range: " + columnRange);
        histogramData = new int[5];

        highestRange = Integer.MIN_VALUE;
        lowestRange = Integer.MAX_VALUE;

        // Set histogram range
        Iterator it = distribution.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if ((int) pair.getKey() > highestRange) {
                highestRange = (int) pair.getKey();
            }
            if ((int) pair.getKey() < lowestRange) {
                lowestRange = (int) pair.getKey();
            }
        }

        logger.info("Highest range: " + highestRange);
        logger.info("Lowest range: " + lowestRange);

        columnRange = (int) Math.ceil((double) (highestRange - lowestRange + 1) / (double) NUM_OF_COLS);

        for (int i = 1; i <= NUM_OF_COLS; i++) {
            it = distribution.entrySet().iterator();
            while (it.hasNext()) {
                int lowerRange = ((i - 1) * columnRange) + lowestRange;
                int higherRange = columnRange + lowerRange - 1;
                Map.Entry pair = (Map.Entry) it.next();
                if (((int) pair.getKey() >= lowerRange) && ((int) pair.getKey() <= higherRange)) {
                    histogramData[i - 1] += (int) pair.getValue();
                }
            }
        }
    }
}
