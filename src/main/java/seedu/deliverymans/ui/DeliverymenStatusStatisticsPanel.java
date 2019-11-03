package seedu.deliverymans.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Panel containing the statistics of deliverymen statuses, using a pie chart to display data.
 */
public class DeliverymenStatusStatisticsPanel extends UiPart<Region> {

    private static final String FXML = "DeliverymenStatusStatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeliverymenStatusStatisticsPanel.class);

    @FXML
    private Label totalDeliverymenLabel;
    @FXML
    private Label availableDeliverymenLabel;
    @FXML
    private Label unavailableDeliverymenLabel;
    @FXML
    private Label deliveringDeliverymenLabel;
    @FXML
    private PieChart statusStatisticsPieChart;
    @FXML
    private TextArea resultDisplay;
    @FXML
    private TextArea adviceDisplay;

    // Data fields for analysis
    private ObservableList<PieChart.Data> pieChartData;

    // Data fields
    private int availableListSize;
    private int unavailableListSize;
    private int deliveringListSize;
    private int totalListSize;


    /**
     * Panel containing the statistics of the statuses of deliverymen.
     */
    public DeliverymenStatusStatisticsPanel(ObservableList<Deliveryman> availableList,
                                            ObservableList<Deliveryman> unavailableList,
                                            ObservableList<Deliveryman> deliveringList) {
        super(FXML);
        availableListSize = availableList.size();
        unavailableListSize = unavailableList.size();
        deliveringListSize = deliveringList.size();
        totalListSize = calculateTotalDeliverymen();
        pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Available", availableListSize),
                        new PieChart.Data("Unavailable", unavailableListSize),
                        new PieChart.Data("Delivering", deliveringListSize));
        initialiseLabels();
        initialiseTextArea(pieChartData);
    }

    /**
     * Set up the texts of all the labels.
     */
    private void initialiseLabels() {
        totalDeliverymenLabel.setText("TOTAL DELIVERYMEN:  " + totalListSize);
        availableDeliverymenLabel.setText("AVAILABLE      :  " + availableListSize + "  ");
        unavailableDeliverymenLabel.setText("UNAVAILABLE :  " + unavailableListSize + "  ");
        deliveringDeliverymenLabel.setText("DELIVERING    :  " + deliveringListSize + "   ");
        fillPieChart();
    }

    /**
     * Set up the text area and the inside text.
     */
    private void initialiseTextArea(ObservableList<PieChart.Data> pieChartData) {
        double utilValue = ((double) deliveringListSize / (availableListSize + deliveringListSize)) * 100;
        resultDisplay.appendText("UTILISATION LEVEL: " + String.format("%.2f", utilValue) + "%\n\n");
        resultDisplay.appendText("(Utilisation level signals the level of \nidle deliverymen.)\n\n\n");

        double activityValue = ((double) (deliveringListSize + availableListSize) / totalListSize) * 100;
        resultDisplay.appendText("ACTIVITY LEVEL: " + String.format("%.2f", activityValue) + "%\n\n");
        resultDisplay.appendText("(Activity level signals the level of \nactive deliverymen.)\n\n\n\n\n");

        resultDisplay.appendText("=================================\n");
        resultDisplay.appendText("AVAILABLE:  " + String.valueOf(pieChartData.get(0).getPieValue()) + "%\n");
        resultDisplay.appendText("UNAVAILABLE:  " + String.valueOf(pieChartData.get(1).getPieValue()) + "%\n");
        resultDisplay.appendText("DELIVERING:  " + String.valueOf(pieChartData.get(2).getPieValue()) + "%\n");
        resultDisplay.appendText("=================================\n");

        if (utilValue >= 80.0) {
            adviceDisplay.appendText("Watch out!\nYou are running out of \navailable deliverymen! ");
        } else if (utilValue <= 20.0) {
            adviceDisplay.appendText("You have too much manpower\nthat is not utilized!");
        } else {
            adviceDisplay.appendText("Your deliverymen are balanced.");
        }
    }

    /**
     * Computes total number of deliverymen in database.
     */
    private int calculateTotalDeliverymen() {
        return availableListSize + unavailableListSize + deliveringListSize;
    }

    /**
     * Fills up the pie chart with data.
     */
    @FXML
    private void fillPieChart() {
        statusStatisticsPieChart.setTitle("CURRENT STATUS PIE CHART");
        statusStatisticsPieChart.setData(pieChartData);
    }

    /**
     *
     */
    private double[] calculatePieValues(PieChart.Data data) {
        double[] pieValues = new double[3];
        for (int i = 0; i < pieValues.length; i++) {
            pieValues[i] = data.getPieValue();
        }
        return pieValues;
    }
}
