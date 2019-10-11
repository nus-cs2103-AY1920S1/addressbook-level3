package calofit.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import calofit.commons.core.LogsCenter;
import calofit.model.util.Statistics;

/**
 * A UI Component that displays statistics in a Report format.
 */
public class ReportWindow extends UiPart<Stage> {

    public static final String FXML = "ReportWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(ReportWindow.class);

    public final int maximum;
    public final int minimum;
    public final double average;

    @FXML
    private Label maximumCalorie;

    @FXML
    private Label minimumCalorie;

    @FXML
    private Label averageCalorie;

    public ReportWindow(Stage root, Statistics statistics) {
        super(FXML, root);
        if (statistics == null) {
            this.maximum = 0;
            this.minimum = 0;
            this.average = 0.0;

            this.minimumCalorie.setText("Nil");
            this.maximumCalorie.setText("Nil");
            this.averageCalorie.setText("Nil");
        } else {
            this.maximum = statistics.getMaximum();
            this.minimum = statistics.getMinimum();
            this.average = statistics.getAverage();

            this.maximumCalorie.setText("Maximum Calorie Intake this month is " + this.maximum);
            this.minimumCalorie.setText("Minimum Calorie Intake this month is " + this.minimum);
            this.averageCalorie.setText("Average Calorie Intake per day this month is " + this.average);
        }
    }

    public ReportWindow(Statistics statistics) {
        this(new Stage(), statistics);
    }

    /**
     * Displays the Report to the user.
     */
    public void show() {
        logger.fine("Showing report page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public void hide() {
        getRoot().hide();
    }
}

