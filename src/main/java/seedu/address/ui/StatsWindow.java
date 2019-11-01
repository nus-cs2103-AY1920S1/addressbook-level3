//@@author LeonardTay748
package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    private static final String good = "Good";
    private static final String hard = "Hard";
    private static final String easy = "Easy";


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

        bc1.setTitle("Total");
        bc2.setTitle("Completed in tests");
        lc.setTitle("Performance Chart");
        xAxisA.setLabel("Rating");
        yAxisA.setLabel("Number of FlashCards");
        xAxisB.setLabel("Rating");
        yAxisB.setLabel("Number of FlashCards");
        xAxisC.setLabel("Test Number");
        yAxisC.setLabel("Percentage");

        model.updateFilteredFlashCardList(new RatingContainsKeywordPredicate("good"));
        int numGood = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(new RatingContainsKeywordPredicate("hard"));
        int numHard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(new RatingContainsKeywordPredicate("easy"));
        int numEasy = model.getFilteredFlashCardList().size();

        int[] stats = model.getTestStats();

        XYChart.Series seriesA = new XYChart.Series();
        seriesA.getData().add(new XYChart.Data(good, numGood));
        seriesA.getData().add(new XYChart.Data(hard, numHard));
        seriesA.getData().add(new XYChart.Data(easy, numEasy));

        XYChart.Series seriesB = new XYChart.Series();
        seriesB.getData().add(new XYChart.Data(good, stats[0]));
        seriesB.getData().add(new XYChart.Data(hard, stats[1]));
        seriesB.getData().add(new XYChart.Data(easy, stats[2]));

        XYChart.Series seriesC = new XYChart.Series();
        ArrayList<Integer> perform = model.getPerformance();
        if (perform.size() == 0) {
            seriesC.getData().add(new XYChart.Data(0, 0));
        } else {
            for (int i = 0; i < perform.size(); i++) {
                seriesC.getData().add(new XYChart.Data(i + 1, perform.get(i).intValue()));
            }
        }

        bc1.getData().addAll(seriesA);
        bc2.getData().addAll(seriesB);
        lc.getData().add(seriesC);

        removeLegend(bc1, bc2, lc);
        setColour(bc1, bc2);

        FlowPane root = new FlowPane();
        root.getChildren().addAll(bc1, bc2, lc);
        Scene scene = new Scene(root, 1600, 400);
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
