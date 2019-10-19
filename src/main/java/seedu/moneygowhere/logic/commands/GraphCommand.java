package seedu.moneygowhere.logic.commands;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Format full help instructions for every command for display.
 */
public class GraphCommand extends Command {

    public static final String COMMAND_WORD = "graph";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows current month's spending in a graph.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_GRAPH_MESSAGE = "Opened graph window.";

    private HashMap<Tag, Double> spendingList;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_GRAPH_MESSAGE, true, false);
    }

    @Override
    public TreeMap<String, Double> getGraphData(Model model) {
        List<Spending> lastShownList = model.getFilteredSpendingList();
        Set<Date> dateSet = new HashSet<>();
        for (Spending i: lastShownList) {
            dateSet.add(i.getDate());
        }
        //Get cost spent per date
        HashMap<String, Double> costPerDateList = new HashMap<String, Double>();
        for (Date e: dateSet) {
            costPerDateList.put(e.toString(), 0.00);
        }
        for (Spending a: lastShownList) {
            double currentCost = costPerDateList.get(a.getDate().toString());
            double updatedCost = currentCost + Double.parseDouble(a.getCost().toString());
            costPerDateList.replace(a.getDate().toString(), updatedCost);
        }

        return new TreeMap<String, Double>(costPerDateList);
    }
}
