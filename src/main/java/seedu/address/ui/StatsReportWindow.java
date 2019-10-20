package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.Statistics;

import java.util.logging.Logger;

public class StatsReportWindow extends UiPart<Stage> {

    public static final String TIP = "Press 'Esc' to quit slideshow";

    private static final Logger logger = LogsCenter.getLogger(StatsReportWindow.class);
    private static final String FXML = "StatsReportWindow.fxml";

    private StatisticsCard statsCard;

    @FXML
    private Label tipLabel;

    @FXML
    private StackPane statisticsPanelPlaceholder;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatsReportWindow(Stage root) {
        super(FXML, root);

        tipLabel.setText(TIP);
        root.setFullScreen(true);

        // Set keyboard listener
        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                root.close();
            }
        });

        root.getScene().getStylesheets().add("view/LightTheme.css");
        root.getScene().getStylesheets().add("view/Extensions.css");
    }

    /**
     * Creates a new HelpWindow.
     */
    public StatsReportWindow() {
        this(new Stage());
    }

    public void setStatsCard(StatisticsCard statsCard) {
        this.statsCard = statsCard;
        statisticsPanelPlaceholder.getChildren().add(statsCard.getRoot());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Show slideshow.");
        getRoot().show();
        getRoot().centerOnScreen();
        getRoot().setFullScreen(true);
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the slideshow window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the slideshow window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
