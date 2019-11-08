package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seedu.moneygowhere.commons.util.MoneyUtil;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Tab containing the pie chart.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "PlaceholderPanel.fxml";

    @FXML
    private StackPane panePlaceholder;

    public StatsPanel(LinkedHashMap<String, Double> statsData, String commandResult, Currency currencyInUse) {
        super(FXML);
        loadData(statsData, commandResult, currencyInUse);
    }

    //@@author choongyx
    /**
     * Constructs the pie chart with the data.
     */
    private void loadData(LinkedHashMap<String, Double> statsData, String commandResult, Currency currency) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> i : statsData.entrySet()) {
            double newCost = currency.rate * i.getValue();
            pieChartData.add(new PieChart.Data(String.format("%s (%s%s)", i.getKey(), currency.symbol, MoneyUtil.format(newCost)),
                Math.round(i.getValue())));
        }
        PieChart pieChart = new PieChart(pieChartData);
        Text text = new Text();
        if (pieChartData.size() == 0) {
            text.setText("No data to show");
        }
        pieChart.setTitle(commandResult);
        pieChart.setLegendVisible(false);
        panePlaceholder.getChildren().addAll(pieChart, text);
    }

}
