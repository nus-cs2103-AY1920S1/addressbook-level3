package seedu.address.ui;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * pop up window that will be used to display statistics calculated
 */
public class DefaultStatisticsWindow extends UiPart<Stage> {
    private static final String FXML = "DefaultStatisticsWindow.fxml";
    @FXML
    private Label statsLabel;
    @FXML
    private Label defaultValue;


    /**
     * Create a new default Statistic window
     */
    public DefaultStatisticsWindow(String defaultValue, String statsLabel) {
        super(FXML, new Stage());
        this.statsLabel.setText("Currently, " + statsLabel + "  on all  completed Orders are:");
        this.statsLabel.setWrapText(true);
        this.defaultValue.setText(defaultValue);
        this.defaultValue.setWrapText(true);
    }


    /**
     * Shows the Default Statistics Window.
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
     * Returns true if the Default Statistics Window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the Default Statistics Window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Default Statistics Window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
