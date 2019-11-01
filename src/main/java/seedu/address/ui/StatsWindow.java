//@@author LeonardTay748
package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
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

        model.updateFilteredFlashCardList(new RatingContainsKeywordPredicate("good"));
        int numGood = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(new RatingContainsKeywordPredicate("hard"));
        int numHard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(new RatingContainsKeywordPredicate("easy"));
        int numEasy = model.getFilteredFlashCardList().size();

        int[] stats = model.getTestStats();

        buildStatsWindow(numGood, numHard, numEasy, stats[0], stats[1], stats[2]);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Scene Builder.
     */
    public void buildStatsWindow(int numGood, int numHard, int numEasy, int statsA, int statsB, int statsC){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        final LineChart<String, Number> lc = new LineChart<>(xAxis, yAxis);
        bc.setTitle("STATISTICS");
        lc.setTitle("STATS");
        xAxis.setLabel("Rating");
        yAxis.setLabel("Number of FlashCards");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Total");
        series1.getData().add(new XYChart.Data(good, numGood));
        series1.getData().add(new XYChart.Data(hard, numHard));
        series1.getData().add(new XYChart.Data(easy, numEasy));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Completed in Test");
        series2.getData().add(new XYChart.Data(good, statsA));
        series2.getData().add(new XYChart.Data(hard, statsB));
        series2.getData().add(new XYChart.Data(easy, statsC));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Total");
        series3.getData().add(new XYChart.Data(good, numGood));
        series3.getData().add(new XYChart.Data(hard, numHard));
        series3.getData().add(new XYChart.Data(easy, numEasy));

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Completed in Test");
        series4.getData().add(new XYChart.Data(good, statsA));
        series4.getData().add(new XYChart.Data(hard, statsB));
        series4.getData().add(new XYChart.Data(easy, statsC));

        bc.getData().addAll(series1, series2);
        lc.getData().addAll(series3, series4);

        FlowPane root = new FlowPane();
        root.getChildren().addAll(bc, lc);
        Scene scene = new Scene(root, 1000, 450);
        stage.setTitle("KFC STATISTICS");
        stage.setScene(scene);
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
