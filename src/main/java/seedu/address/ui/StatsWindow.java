package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.TableEntry;

/**
 * Controller for a stats page
 */

public class StatsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);

    private static final String FXML = "StatsWindow.fxml";

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    private StatsWindow(Stage root) {
        super(FXML, root);
        root.sizeToScene();
    }

    /**
     * Creates a new StatsWindow meant for PieChartStatistics
     */
    StatsWindow(List<String> names, List<Double> percentages, String title) {
        this(new Stage());
        Stage primaryStage = getRoot();
        primaryStage.setTitle(title);

        ObservableList<PieChart.Data> details = FXCollections.observableArrayList();

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            double percentage = percentages.get(i);
            details.add(new PieChart.Data(name, percentage));
        }

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 600, 500);

        PieChart pieChart = new PieChart();
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
     * Creates a new StatsWindow meant for TabularStatistics
     */
    StatsWindow(String title, List<TableEntry> rows) {
        this(new Stage());
        Stage primaryStage = getRoot();
        primaryStage.setTitle(title);

        TableColumn<TableEntry, String> nameColumn = new TableColumn<>("Category");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("name"));

        TableColumn<TableEntry, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(200);
        amountColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("amount"));

        TableColumn<TableEntry, String> frequencyColumn = new TableColumn<>("Frequency");
        frequencyColumn.setMinWidth(200);
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("numEntries"));

        TableView<TableEntry> table = new TableView<>();
        table.setItems(getTableEntries(rows));



        table.getColumns().addAll(nameColumn, amountColumn, frequencyColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
    }

    private ObservableList<TableEntry> getTableEntries(List<TableEntry> rows) {
        ObservableList<TableEntry> result = FXCollections.observableArrayList();
        result.addAll(rows);
        return result;
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
    void show() {
        logger.fine("Showing stats page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the stats window is currently being shown.
     */
    boolean isShowing() {
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
    void focus() {
        getRoot().requestFocus();
    }
}


