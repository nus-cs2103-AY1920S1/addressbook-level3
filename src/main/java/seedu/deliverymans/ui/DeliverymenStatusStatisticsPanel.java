package seedu.deliverymans.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.model.deliveryman.Deliveryman;

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

    // Data fields
    private int availableListSize;
    private int unavailableListSize;
    private int deliveringListSize;
    private int totalListSize;

    // Data fields for analysis

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
        initialiseLabels();
    }

    private void initialiseLabels() {
        totalDeliverymenLabel.setText("TOTAL DELIVERYMEN:  " + totalListSize);
        availableDeliverymenLabel.setText("AVAILABLE      :  " + availableListSize + "  ");
        unavailableDeliverymenLabel.setText("UNAVAILABLE :  " + unavailableListSize + "  ");
        deliveringDeliverymenLabel.setText("DELIVERING    :  " + deliveringListSize + "   ");
        fillPieChart();
    }

    private int calculateTotalDeliverymen() {
        return availableListSize + unavailableListSize + deliveringListSize;
    }

    @FXML
    private void fillPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Available", availableListSize),
                        new PieChart.Data("Delivering", deliveringListSize),
                        new PieChart.Data("Unavailable", unavailableListSize));
        // applyCustomColorSequence(pieChartData);
        statusStatisticsPieChart.setTitle("CURRENT STATUS PIE CHART");
        statusStatisticsPieChart.setData(pieChartData);
    }

    private void applyCustomColorSequence(
            ObservableList<PieChart.Data> pieChartData,
            String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: #ffd700");
            i++;
        }
    }
}
