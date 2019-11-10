package seedu.guilttrip.ui.stats;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.ui.UiPart;

/**
 * Displays the user's statistics by months in the form of a pie chart.
 */
public class StatisticsPieChart extends UiPart<Region> {

    private static final String FXML = "statistics/StatisticsPieChart.fxml";
    private ObservableList<CategoryStatistics> statsMap;
    private ObservableList<PieChart.Data> toAdd;
    private final Logger logger = LogsCenter.getLogger(StatisticsPieChart.class);

    @FXML
    private StackPane stackForPie;

    @FXML
    private Label noEntryLabel;

    @FXML
    private PieChart statsPieChart;

    public StatisticsPieChart(ObservableList<CategoryStatistics> statsMap, String type) {
        super(FXML);
        this.statsMap = statsMap;
        statsMap.addListener(new ListChangeListener<CategoryStatistics>() {
            @Override
            public void onChanged(Change<? extends CategoryStatistics> change) {
                updatePieChart(statsMap);
            }
        });
        toAdd = FXCollections.observableArrayList();

        statsPieChart.setTitle(type);
        if (type.equals("Income")) {
            statsPieChart.setStyle("-fx-text-fill: MediumSeaGreen");
        }
        if (type.equals("Expense")) {
            statsPieChart.setStyle("-fx-text-fill: LightSalmon");
        }
        statsPieChart.setLabelLineLength(15);
        statsPieChart.setLegendSide(Side.BOTTOM);

        updatePieChart(statsMap);
    }

    /**
     * Updates the PieChart displayed by running a calculation on every notified change from the original statsMaplist.
     * @param statsMap contains a listener that calls for this method.
     */
    public void updatePieChart(ObservableList<CategoryStatistics> statsMap) {
        toAdd.clear();
        statsPieChart.getData().clear();
        //Calculate Total
        double totalAmount = 0.00;
        for (int i = 0; i < statsMap.size(); i++) {
            totalAmount = totalAmount + statsMap.get(i).getAmountCalculated();
        }
        for (int i = 0; i < statsMap.size(); i++) {
            CategoryStatistics t = statsMap.get(i);
            if (t.getAmountCalculated() != 0) {
                Double percentage = (double) Math.round(t.getAmountCalculated() / totalAmount * 100);
                String dataToShow = t.getCategoryName() + " (" + percentage + "%)";
                toAdd.add(new PieChart.Data(dataToShow, t.getAmountCalculated()));
            }
        }
        if (toAdd.isEmpty()) {
            statsPieChart.setVisible(false);
            noEntryLabel.setVisible(true);
        } else {
            statsPieChart.setData(toAdd);
            statsPieChart.setVisible(true);
            noEntryLabel.setVisible(false);
        }
    }
}
