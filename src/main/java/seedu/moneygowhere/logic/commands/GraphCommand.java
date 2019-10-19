package seedu.moneygowhere.logic.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;

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
    public Map<Date, Double> getGraphData(Model model) {
        List<Spending> lastShownList = model.getFilteredSpendingList();
        Comparator<Date> dateComparator = new Comparator<Date>() {
            @Override
            public int compare (Date s1, Date s2) {
                SimpleDateFormat inputsdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date1 = null;
                String date2 = null;
                try {
                    date1 = sdf.format(inputsdf.parse(s1.toString()));
                    date2 = sdf.format(inputsdf.parse(s2.toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return Integer.compare(date1.compareTo(date2), 0);
            }
        };
        Set<Date> dateSet = new TreeSet<>(dateComparator);
        //Get list of distinct dates
        for (Spending i: lastShownList) {
            dateSet.add(i.getDate());
        }
        //Get cost spent per date
        Map<Date, Double> costPerDateList = new TreeMap<>(dateComparator);
        for (Date e: dateSet) {
            costPerDateList.put(e, 0.00);
        }
        for (Spending a: lastShownList) {
            double currentCost = costPerDateList.get(a.getDate());
            double updatedCost = currentCost + Double.parseDouble(a.getCost().toString());
            costPerDateList.replace(a.getDate(), updatedCost);
        }
        return costPerDateList;
    }
}
