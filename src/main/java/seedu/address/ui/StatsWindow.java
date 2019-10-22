package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a stats page
 */

public class StatsWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);

    private static final String FXML = "StatsWindow.fxml";

    @javafx.fxml.FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    private final ObservableList<PieChart.Data> details = FXCollections.observableArrayList();

    private BorderPane root;

    private PieChart pieChart;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatsWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        root.sizeToScene();
    }

    /**
     * Creates a new HelpWindow.
     */

    public StatsWindow(List<String> names, List<Double> percentages, String title) {
        this(new Stage());
        Stage primaryStage = getRoot();
        primaryStage.setTitle(title);//set the main stage

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            double percentage = percentages.get(i);
            details.add(new PieChart.Data(name,percentage));
        }

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 600, 500);

        pieChart = new PieChart();
        pieChart.setData(details);
        pieChart.setTitle(title);
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);

        borderPane.setCenter(pieChart);

        pieChart.setStartAngle(90);
        pieChart.setClockwise(false);

        primaryStage.setScene(scene);
    }



    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


    //here just because its still in the fxml file
    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}


