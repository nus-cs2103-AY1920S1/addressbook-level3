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
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.StatisticsRecordCard;

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
    @FXML
    private Label caption;

    private StatisticsRecordCard recordCard;

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
    public DeliverymenStatusStatisticsPanel(StatisticsRecordCard recordCard) {
        super(FXML);
        this.recordCard = recordCard;
        availableListSize = (Integer) recordCard.retrieveRecordCardField(1);
        unavailableListSize = (Integer) recordCard.retrieveRecordCardField(2);
        deliveringListSize = (Integer) recordCard.retrieveRecordCardField(3);
        totalListSize = (Integer) recordCard.retrieveRecordCardField(4);
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

        adviceDisplay.appendText(recordCard.adviceMessage());
        resultDisplay.appendText(recordCard.resultMessage());

        /*
        resultDisplay.appendText("=========================================\n");
        resultDisplay.appendText("AVAILABLE:  " + String.valueOf(pieChartData.get(0).getPieValue()) + "%\n");
        resultDisplay.appendText("UNAVAILABLE:  " + String.valueOf(pieChartData.get(1).getPieValue()) + "%\n");
        resultDisplay.appendText("DELIVERING:  " + String.valueOf(pieChartData.get(2).getPieValue()) + "%\n");
        resultDisplay.appendText("=========================================\n");
        */

    }

    /**
     * Fills up the pie chart with data.
     */
    @FXML
    private void fillPieChart() {
        statusStatisticsPieChart.setTitle("STATUS PIE CHART");
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
