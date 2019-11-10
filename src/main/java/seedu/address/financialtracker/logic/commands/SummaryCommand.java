package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.ui.FinancialTrackerSummaryWindow;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists out a summary of your current expenses.
 */
public class SummaryCommand extends Command<Model> {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up a window which shows the summary of your"
            + "total expenses.";

    public static final String MESSAGE_SUCCESS = "Currently viewing the Summary Window";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // initialise pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // initialise bar chart data
        ArrayList<XYChart.Series> seriesArray = new ArrayList<>();

        // get statistics from model
        HashMap<String, Double> modelData = model.getSummaryMap();
        double total = modelData.get("Total");

        for (String key : modelData.keySet()) {
            if (key.equals("Total")) {
                continue;
            }
            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(key);
            dataSeries.getData().add(new XYChart.Data("", modelData.get(key)));
            seriesArray.add(dataSeries);
            pieChartData.add(new PieChart.Data(key, modelData.get(key) / total));
        }

        new FinancialTrackerSummaryWindow(pieChartData, seriesArray).show();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
