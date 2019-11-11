//@@author LeonardTay748
package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;

/**
 * Displays Statistics as Bar Chart
 */
public class StatsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);
    private static final String FXML = "StatsWindow.fxml";
    private static final Stage stage = new Stage();

    private static final String GOOD = "good";
    private static final String HARD = "hard";
    private static final String EASY = "easy";


    /**
     * Creates a new StatsWindow.
     */
    public StatsWindow() {
        super(FXML, stage);
    }

    /**
     * Shows the stats window.
     */
    public void show(Model model) {
        logger.fine("Showing stats page about the application.");
        requireNonNull(model);

        buildStatsWindow(model);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Scene Builder.
     */
    public void buildStatsWindow(Model model) {
        final CategoryAxis xAxisA = new CategoryAxis();
        final NumberAxis yAxisA = new NumberAxis();
        final CategoryAxis xAxisB = new CategoryAxis();
        final NumberAxis yAxisB = new NumberAxis();
        final NumberAxis xAxisC = new NumberAxis();
        final NumberAxis yAxisC = new NumberAxis();

        final BarChart<String, Number> bc1 = new BarChart<>(xAxisA, yAxisA);
        final BarChart<String, Number> bc2 = new BarChart<>(xAxisB, yAxisB);
        final LineChart<Number, Number> lc = new LineChart<>(xAxisC, yAxisC);

        int numGood = model.getFilteredFlashCardListNoCommit(new RatingContainsKeywordPredicate(GOOD)).size();
        int numHard = model.getFilteredFlashCardListNoCommit(new RatingContainsKeywordPredicate(HARD)).size();
        int numEasy = model.getFilteredFlashCardListNoCommit(new RatingContainsKeywordPredicate(EASY)).size();

        int[] stats = model.getTestStats();
        ArrayList<Float> perform = model.getPerformance();

        int upperBoundA = findUpperBound(numGood, numHard, numEasy);
        int upperBoundB = findUpperBound(stats[0], stats[1], stats[2]);
        initialize(bc1, bc2, lc, xAxisA, xAxisB, xAxisC, yAxisA,
                yAxisB, yAxisC, upperBoundA, upperBoundB, perform.size());

        XYChart.Series seriesA = new XYChart.Series();
        seriesA.getData().add(new XYChart.Data(GOOD, numGood));
        seriesA.getData().add(new XYChart.Data(HARD, numHard));
        seriesA.getData().add(new XYChart.Data(EASY, numEasy));

        XYChart.Series seriesB = new XYChart.Series();
        seriesB.getData().add(new XYChart.Data(GOOD, stats[0]));
        seriesB.getData().add(new XYChart.Data(HARD, stats[1]));
        seriesB.getData().add(new XYChart.Data(EASY, stats[2]));

        XYChart.Series seriesC = new XYChart.Series();
        if (perform.size() == 0) {
            seriesC.getData().add(new XYChart.Data(0, 0));
        } else {
            for (int i = 0; i < perform.size(); i++) {
                BigDecimal bd = new BigDecimal(perform.get(i).floatValue()).setScale(2, RoundingMode.HALF_UP);
                seriesC.getData().add(new XYChart.Data(i + 1, bd.floatValue()));
            }
        }

        bc1.getData().addAll(seriesA);
        bc2.getData().addAll(seriesB);
        lc.getData().add(seriesC);

        removeLegend(bc1, bc2, lc);
        setColour(bc1, bc2);

        FlowPane root = new FlowPane(Orientation.HORIZONTAL);
        root.getChildren().addAll(bc1, bc2, lc);
        ScrollPane sp = new ScrollPane();
        sp.setContent(root);
        Scene scene = new Scene(sp, 550, 500);
        stage.setTitle("STATISTICS");
        stage.setScene(scene);

    }

    /**
     * Removes the legend from bar charts since they are not required.
     */
    public void removeLegend(BarChart<String, Number> bc1, BarChart<String, Number> bc2, LineChart<Number, Number> lc) {
        bc1.setLegendVisible(false);
        bc2.setLegendVisible(false);
        lc.setLegendVisible(false);
    }

    /**
     * Sets the bar chart colours to red, blue and green.
     */
    public void setColour(BarChart<String, Number> bc1, BarChart<String, Number> bc2) {
        Node n = bc1.lookup(".data0.chart-bar");
        n.setStyle("-fx-bar-fill: blue");
        n = bc1.lookup(".data1.chart-bar");
        n.setStyle("-fx-bar-fill: red");
        n = bc1.lookup(".data2.chart-bar");
        n.setStyle("-fx-bar-fill: green");

        Node n2 = bc2.lookup(".data0.chart-bar");
        n2.setStyle("-fx-bar-fill: blue");
        n2 = bc2.lookup(".data1.chart-bar");
        n2.setStyle("-fx-bar-fill: red");
        n2 = bc2.lookup(".data2.chart-bar");
        n2.setStyle("-fx-bar-fill: green");
    }

    /**
     * Sets the title & labels the axis of the charts.
     * Sets the boundary and precision for the axis if needed.
     */
    public void initialize(BarChart bc1, BarChart bc2, LineChart lc, CategoryAxis xAxisA,
                           CategoryAxis xAxisB, NumberAxis xAxisC, NumberAxis yAxisA, NumberAxis yAxisB,
                           NumberAxis yAxisC, int upperBoundA, int upperBoundB, int upperBoundC) {
        bc1.setTitle("Total");
        bc2.setTitle("Completed in tests");
        lc.setTitle("Performance Chart");
        xAxisA.setLabel("Rating");
        yAxisA.setLabel("Number of FlashCards");
        xAxisB.setLabel("Rating");
        yAxisB.setLabel("Number of FlashCards");
        xAxisC.setLabel("Test Number");
        yAxisC.setLabel("Percentage");

        yAxisA.setAutoRanging(false);
        yAxisA.setLowerBound(0);
        yAxisA.setUpperBound(upperBoundA);
        yAxisA.setTickUnit(1);
        yAxisA.setMinorTickVisible(false);

        yAxisB.setAutoRanging(false);
        yAxisB.setLowerBound(0);
        yAxisB.setUpperBound(upperBoundB);
        yAxisB.setTickUnit(1);
        yAxisB.setMinorTickVisible(false);

        xAxisC.setAutoRanging(false);
        xAxisC.setLowerBound(0);
        xAxisC.setUpperBound(upperBoundC);
        xAxisC.setTickUnit(1);
        xAxisC.setMinorTickVisible(false);

        yAxisC.setAutoRanging(false);
        yAxisC.setLowerBound(0);
        yAxisC.setUpperBound(100);
        yAxisC.setTickUnit(1);
    }

    /**
     * Returns the highest value
     */
    public int findUpperBound(int one, int two, int three) {
        int largest = one;

        if (two > largest) {
            largest = two;
        }
        if (three > largest) {
            largest = three;
        }

        return largest;
    }

    /**
     * Returns true if the stats window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the stats window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the stats window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
