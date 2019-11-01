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

    /**
     * Constructor that sets panel to default display
     */
    public AssignmentStatisticsPanel() {
        super(FXML);
        cardPane = new VBox();
        cardPane.getChildren().add(getEmptyLabel());
    }

    /**
     * Generates the graph based on the given hashmap of results and assignment info
     * @param resultInfo - hashmap of students and their scores
     * @param assignment - assignment to be displayed (contains assignment info)
     */
    public void generateGraph(Map<Student, Integer> resultInfo, Assignment assignment) {
        requireAllNonNull(resultInfo, assignment);
        cardPane.getChildren().clear();
        this.assignment = assignment;
        scores = getScores(resultInfo);
        cardPane = new VBox();
        if (!scores.isEmpty()) {
            Collections.sort(scores);
            setStatistics(assignment);
            createHistogram();
            cardPane.getChildren().addAll(scoreBarChart, maxScore, median, upperPercentile, lowerPercentile);
        } else {
            logger.info("Assignment Graphical display is empty");
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
            String upperBoundString;
            String lowerBoundString;

            int upperBound = i * columnRange + scores.get(0) - 1;
            upperBoundString = String.valueOf(upperBound);
            int lowerBound = (((i - 1) * columnRange) + scores.get(0));
            lowerBoundString = String.valueOf(lowerBound);
            if (upperBound > assignment.getMaxScore()) {
                upperBoundString = "";
            }
            if (lowerBound > assignment.getMaxScore()) {
                lowerBoundString = "";
            }
            range = lowerBoundString + "-" + upperBoundString;
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
        int index = (int) Math.ceil((percentile / 100) * scores.size());
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
        logger.info("Lower range: " + scores.get(0));
        logger.info("Upper range: " + scores.get(scores.size() - 1));
        histogramData = new int[5];

        columnRange = (int) Math.ceil((double) (scores.get(scores.size() - 1) - scores.get(0) + 1)
                / (double) NUM_OF_COLS);

        Iterator it;
        for (int i = 1; i <= NUM_OF_COLS; i++) {
            it = distribution.entrySet().iterator();
            while (it.hasNext()) {
                int lowerRange = ((i - 1) * columnRange) + scores.get(0);
                int higherRange = columnRange + lowerRange - 1;
                Map.Entry pair = (Map.Entry) it.next();
                if (((int) pair.getKey() >= lowerRange) && ((int) pair.getKey() <= higherRange)) {
                    histogramData[i - 1] += (int) pair.getValue();
                }
            }
        }
    }
}
