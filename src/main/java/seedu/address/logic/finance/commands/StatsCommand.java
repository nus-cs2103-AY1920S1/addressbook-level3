package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_BETWEEN;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_GROUP_BY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_SUMMARISE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.model.finance.GraphicsData;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.GroupByAttr;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Display summary statistics of finance log entries.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows finance log's summary statistics.\n"
            + "Parameters: "
            + PREFIX_GROUP_BY + "ATTRIBUTE "
            + PREFIX_SUMMARISE + "STAT "
            + "[" + PREFIX_BETWEEN + "DD-MM-YYYY DD-MM-YYYY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_BY + "entrytype "
            + PREFIX_SUMMARISE + "amt";

    private GroupByAttr groupBy;
    private String summariseAttr;
    private Date[] betweenDates;

    public StatsCommand(GroupByAttr groupBy, String summariseAttr, Date[] betweenDates) {
        this.groupBy = groupBy;
        this.summariseAttr = summariseAttr;
        this.betweenDates = betweenDates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<LogEntry> logEntryList = model.getFilteredLogEntryList();
        // Set statistics into appropriate graphics
        String graphicType = getGraphicType();
        ObservableList<PieChart.Data> pieChartData = getPieChartData(logEntryList);
        ArrayList<XYChart.Series> barChartData = getBarChartData(logEntryList);
        GraphicsData graphicsData = new GraphicsData(graphicType, pieChartData, barChartData);
        model.setGraphicsData(graphicsData);
        String message = "[STATISTIC]: "
                + (summariseAttr.equalsIgnoreCase("freq") ? "No. of log entries " : "total amount ")
                + "by " + groupBy.attr;
        return new CommandResult(message, false, true, false);
    }

    /**
     * There are two graphic types - pie (frequency) and bar (total amount) charts.
     */
    private String getGraphicType() {
        return summariseAttr.equalsIgnoreCase("freq") ? "pie" : "bar";
    }

    private ObservableList<PieChart.Data> getPieChartData(ObservableList<LogEntry> logEntryList) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        // Divide log entries by groups
        ArrayList<String> dataList = getDataList(logEntryList);
        HashSet<String> groupSet = new HashSet<>(dataList);
        // Sort group in descreasing freq
        ArrayList<String> sortedGroupList = getSortedGroupList(groupSet, dataList);
        for (String groupName : sortedGroupList) {
            pieData.add(new PieChart.Data(groupName,
                    Collections.frequency(dataList, groupName)));
        }
        return pieData;
    }

    /**
     * Finds the list of log entries mapped to attribute,
     * according to {@code GroupByAttr} specified by user.
     */
    private ArrayList<String> getDataList(ObservableList<LogEntry> logEntryList) {
        ArrayList<String> dataList = new ArrayList<>();
        String groupAttr = groupBy.attr;
        switch (groupAttr) {
        case "entrytype":
            dataList.addAll(logEntryList
                    .stream()
                    .map(log -> log.getLogEntryType())
                    .collect(Collectors.toList()));
            break;
        case "met":
            dataList.addAll(logEntryList
                    .stream()
                    .map(log -> log.getTransactionMethod().value)
                    .collect(Collectors.toList()));
            break;
        case "cat":
            for (LogEntry log : logEntryList) {
                Set<Category> categorySet = log.getCategories();
                for (Category cat : categorySet) {
                    dataList.add(cat.catName);
                }
            }
            break;
        case "place":
            dataList.addAll(logEntryList
                    .stream()
                .filter(log -> log instanceof SpendLogEntry)
                .map(log -> ((SpendLogEntry) log).getPlace().value)
                .collect(Collectors.toList()));
            break;
        case "month":
            for (LogEntry log : logEntryList) {
                String monthYear = log.getTransactionDate()
                        .value
                        .split("-", 2)[1];
                dataList.add(monthYear);
            }
            break;
        default:
        }
        return dataList;
    }

    /**
     * Returns an array of strings, sorted by the frequency of the string in the given data.
     * In decreasing frequency.
     */
    private ArrayList<String> getSortedGroupList(HashSet<String> groupSet, ArrayList<String> dataList) {
        ArrayList<String> sortedGroupList = new ArrayList<String>();
        ArrayList<String> groupList = new ArrayList<String>(groupSet);
        List<Integer> freqList = groupList
                .stream()
                .map(g -> Collections.frequency(dataList, g))
                .collect(Collectors.toList());
        int maxFreqIdx;
        while (sortedGroupList.size() != groupSet.size()) {
            maxFreqIdx = getMaxFreqIdx(freqList);
            sortedGroupList.add(groupList.get(maxFreqIdx));
            freqList.remove(maxFreqIdx);
            groupList.remove(maxFreqIdx);
        }
        return sortedGroupList;
    }

    private int getMaxFreqIdx(List<Integer> freqList) {
        int maxFreq = 0;
        int maxFreqIdx = -1;
        int currFreq;
        for (int i = 0; i < freqList.size(); i++) {
            currFreq = freqList.get(i);
            if (currFreq > maxFreq) {
                maxFreq = currFreq;
                maxFreqIdx = i;
            }
        }
        return maxFreqIdx;
    }

    private ArrayList<XYChart.Series> getBarChartData(ObservableList<LogEntry> logEntryList) {
        ArrayList<XYChart.Series> barSeriesData = new ArrayList<>();

        // Groups of bars
        HashSet<String> groupList = new HashSet<String>(getDataList(logEntryList));
        XYChart.Series<String, Number> currSeries;
        for (String group : groupList) {
            String groupByAttr = groupBy.attr;
            currSeries = new XYChart.Series<String, Number>();
            currSeries.setName(group);
            if (groupByAttr.equalsIgnoreCase("month") || groupByAttr.equalsIgnoreCase("place")) {
                // Each group has 1 bar
                currSeries.getData().add(new XYChart.Data<String, Number>(group, getTotalAmount(group)));
            } else {
                // Each group has 4 bars
                List<String> logEntryTypes = Arrays.asList("spend", "income", "borrow", "lend");
                for (String type : logEntryTypes) {
                    currSeries.getData().add(new XYChart.Data<String, Number>(group, getTotalAmount(group, type)));
                }
            }
            barSeriesData.add(currSeries);
        }

        return barSeriesData;
    }

    private int getTotalAmount(String group, String logEntryType) {
        return 5;
    }

    private int getTotalAmount(String group) {
        return 7;
    }

}
