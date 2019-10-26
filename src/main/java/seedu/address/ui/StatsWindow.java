package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;

//@@author LeonardTay748
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

        //stage.setTitle("Statistics");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("STATISTICS");
        xAxis.setLabel("Rating");
        yAxis.setLabel("Number of FlashCards");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Total");
        series1.getData().add(new XYChart.Data(good, numGood));
        series1.getData().add(new XYChart.Data(hard, numHard));
        series1.getData().add(new XYChart.Data(easy, numEasy));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Completed in Test");
        series2.getData().add(new XYChart.Data(good, stats[0]));
        series2.getData().add(new XYChart.Data(hard, stats[1]));
        series2.getData().add(new XYChart.Data(easy, stats[2]));

        Scene scene = new Scene(bc, 800, 600, Color.BLACK);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        getRoot().show();
        getRoot().centerOnScreen();
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
