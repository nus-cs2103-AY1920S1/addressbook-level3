package calofit.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
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
    private Label numericalStatistics;

    @FXML
    private Label foodStatistics;

    @FXML
    private TextFlow maximumCalorie;

    @FXML
    private TextFlow minimumCalorie;

    @FXML
    private TextFlow averageCalorie;

    public ReportWindow(Stage root, Statistics statistics) {
        super(FXML, root);

        numericalStatistics.setBackground(new Background(
                new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)
        ));
        numericalStatistics.setStyle("-fx-font-weight: bold; -fx-font-size: 22px; -fx-text-alignment: right");
        foodStatistics.setBackground(new Background(
                new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)
        ));
        foodStatistics.setStyle("-fx-font-weight: bold; -fx-font-size: 22px; -fx-text-alignment: right");

        Background forNumericalStatistics = new Background(
                new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY));
        maximumCalorie.setBackground(forNumericalStatistics);
        maximumCalorie.setTextAlignment(TextAlignment.CENTER);
        minimumCalorie.setBackground(forNumericalStatistics);
        minimumCalorie.setTextAlignment(TextAlignment.CENTER);
        averageCalorie.setBackground(forNumericalStatistics);
        averageCalorie.setTextAlignment(TextAlignment.CENTER);

        if (statistics == null) {
            this.maximum = 0;
            this.minimum = 0;
            this.average = 0.0;

            Text maximum = new Text("Maximum");
            maximum.setStyle("-fx-font-weight: bold");
            maximum.setFont(new Font(100));
            Text valueOfMaximum = new Text(String.valueOf(statistics.getMaximum()));
            maximumCalorie.getChildren().addAll(maximum,
                    new Text(" calorie intake of the month is: \n"), valueOfMaximum);

            Text minimum = new Text("Minimum");
            minimum.setStyle("-fx-font-weight: bold");
            minimum.setFont(new Font(100));
            Text valueOfMinimum = new Text(String.valueOf(statistics.getMinimum()));
            minimumCalorie.getChildren().addAll(minimum,
                    new Text(" calorie intake of the month is: \n"), valueOfMinimum);

            Text average = new Text("Average");
            average.setStyle("-fx-font-weight: bold");
            average.setFont(new Font(100));
            Text valueOfAverage = new Text(String.valueOf(statistics.getAverage()));
            averageCalorie.getChildren().addAll(average,
                    new Text(" calorie intake of the month is: \n"), valueOfAverage);
        } else {
            this.maximum = statistics.getMaximum();
            this.minimum = statistics.getMinimum();
            this.average = statistics.getAverage();

            Text maximum = new Text("Maximum\n");
            maximum.setStyle("-fx-font-weight: bold; -fx-font-size: 35px");
            Text valueOfMaximum = new Text(String.valueOf(statistics.getMaximum()));
            valueOfMaximum.setStyle("-fx-font-size: 40px");
            maximumCalorie.getChildren().addAll(maximum,
                    new Text(" calorie intake of the month is: \n"), valueOfMaximum);

            Text minimum = new Text("Minimum\n");
            minimum.setStyle("-fx-font-weight: bold; -fx-font-size: 35px");
            Text valueOfMinimum = new Text(String.valueOf(statistics.getMinimum()));
            valueOfMinimum.setStyle("-fx-font-size: 40px");
            minimumCalorie.getChildren().addAll(minimum,
                    new Text(" calorie intake of the month is: \n"), valueOfMinimum);

            Text average = new Text("Average\n");
            average.setStyle("-fx-font-weight: bold; -fx-font-size: 35px");
            Text valueOfAverage = new Text(String.valueOf(statistics.getAverage()));
            valueOfAverage.setStyle("-fx-font-size: 40px");
            averageCalorie.getChildren().addAll(average,
                    new Text(" calorie intake of the month is: \n"), valueOfAverage);
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

