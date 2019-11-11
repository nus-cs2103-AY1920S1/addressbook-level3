package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_GROUP_BY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_SUMMARISE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP_BY + "entrytype "
            + PREFIX_SUMMARISE + "amt";

    private GroupByAttr groupBy;
    private String summariseAttr;

    public StatsCommand(GroupByAttr groupBy, String summariseAttr) {
        this.groupBy = groupBy;
        this.summariseAttr = summariseAttr;
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
        return new CommandResult(message, false, true, false, false);
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
        ArrayList<XYChart.Series> barSeriesData;

        // Groups of bars
        HashSet<String> groupList = new HashSet<String>(getDataList(logEntryList));
        String groupByAttr = groupBy.attr;
        if (groupByAttr.equals("entrytype") || groupByAttr.equals("place")) {
            // Each group has 1 bar
            barSeriesData = getOneBarPerGroup(groupList, logEntryList);
        } else {
            // Each group has 4 bars
            barSeriesData = getFourBarsPerGroup(groupList, logEntryList);
        }
        return barSeriesData;
    }

    // For grouping by log entry types and places
    private ArrayList<XYChart.Series> getOneBarPerGroup(HashSet<String> groupList,
                                                        ObservableList<LogEntry> logEntryList) {
        ArrayList<XYChart.Series> barSeriesData = new ArrayList<>();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName(groupBy.attr);
        for (String group : groupList) {
            series.getData().add(new XYChart.Data<String, Number>(group, getAmount(groupBy.attr, group, logEntryList)));
        }
        barSeriesData.add(series);
        return barSeriesData;
    }

    private ArrayList<XYChart.Series> getFourBarsPerGroup(HashSet<String> groupList,
                                                          ObservableList<LogEntry> logEntryList) {
        ArrayList<XYChart.Series> barSeriesData = new ArrayList<>();
        XYChart.Series<String, Number> currSeries;
        List<String> logEntryTypesList = Arrays.asList("spend", "income", "borrow", "lend");
        for (String type : logEntryTypesList) {
            currSeries = new XYChart.Series<String, Number>();
            currSeries.setName(type);
            for (String group : groupList) {
                currSeries.getData()
                        .add(new XYChart.Data<String, Number>(
                                group, getAmount(groupBy.attr, group, logEntryList, type)));
            }
            barSeriesData.add(currSeries);
        }
        return barSeriesData;
    }

    /**
     * Return total amount associated with log entries
     * of that attribute group.
     * @param attr  Attribute of log entry
     * @param group Group of that attribute to filter through
     */
    private double getAmount(String attr, String group, List<LogEntry> logEntryList) {
        double currAmt;
        //@@author Lokeshkumar R
        //Modified from https://stackoverflow.com/questions/16242733/
        // sum-all-the-elements-java-arraylist
        if (attr.equals("entrytype")) {
            currAmt = logEntryList
                    .stream()
                    .filter(log -> log.getLogEntryType().equals(group))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
        } else if (attr.equals("place")) {
            currAmt = logEntryList
                    .stream()
                    .filter(log -> log.getLogEntryType().equals("spend"))
                    .filter(log -> ((SpendLogEntry) log).getPlace().value.equals(group))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
        } else if (attr.equals("month")) {
            currAmt = logEntryList
                    .stream()
                    .filter(log -> (log.getTransactionDate().value.split("-", 2)[1]).equals(group))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
        } else if (attr.equals("met")) {
            currAmt = logEntryList
                    .stream()
                    .filter(log -> log.getTransactionMethod().value.equals(group))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
        } else if (attr.equals("cat")) {
            currAmt = logEntryList
                    .stream()
                    .filter(log -> logContainsCat(log, group))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
        } else {
            currAmt = 0;
        }
        return currAmt;
    }

    /**
     * Similar to above {@code getAmount} but with an
     * additional filter of log entry type.
     */
    private double getAmount(String attr, String group,
                             List<LogEntry> logEntryList, String logEntryType) {
        List<LogEntry> filteredLogEntryList = logEntryList
                .stream()
                .filter(log -> log.getLogEntryType().equals(logEntryType))
                .collect(Collectors.toList());
        return getAmount(attr, group, filteredLogEntryList);
    }

    /**
     * Returns true if log contains a category by that name.
     */
    private boolean logContainsCat(LogEntry log, String catName) {
        Set<Category> catSet = log.getCategories();
        if (catSet.size() == 0) {
            return false;
        }
        for (Category cat : catSet) {
            if (cat.catName.equals(catName)) {
                return true;
            }
        }
        return false;
    }
}
