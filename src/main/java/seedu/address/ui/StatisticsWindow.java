package seedu.address.ui;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatisticsWindow extends UiPart<Stage> {
    private static final String FXML = "StatisticsWindow.fxml";

    @FXML
    private Label testLabel;
    /**
     * Creates a new Statistics window.
     *
     * @param root Stage to use as the root of the StatisticsWindow
     */
    public StatisticsWindow(Stage root, String statisticsResult) {
        super(FXML, root);
        testLabel.setText(statisticsResult);
    }

    public StatisticsWindow(Stage root) {
        super(FXML, root);
        testLabel.setText("test");
    }

    /**
     * new Statistics Window
     */
    public StatisticsWindow() {
        this(new Stage());
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
