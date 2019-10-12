package seedu.address.ui;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * pop up window that will be used to display statistics calculated
 */
public class StatisticsWindow extends UiPart<Stage> {
    private static final String FXML = "StatisticsWindow.fxml";

    @FXML
    private Label statsLabel;
    @FXML
    private Label testLabel;

    /**
     * Create a new Statistic window
     * @param root Stage in which the window will use
     */
    public StatisticsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * utility method to create statistic window with the data
     * @param statisticsResult the result of the statistic
     * @param statsLabel the title of the stats
     */
    public StatisticsWindow(String statisticsResult, String statsLabel) {
        this(new Stage());
        this.testLabel.setText(statisticsResult);
        this.statsLabel.setText(statsLabel);
    }

    /**
     * Shows the Statistics Window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the statistics Window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics Window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistics Window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
