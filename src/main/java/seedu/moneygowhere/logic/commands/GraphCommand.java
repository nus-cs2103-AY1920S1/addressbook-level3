package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Displays a graph of all spendings within the date range specified "
        + "by the user input. If no user input is given, the date range will be the whole date range.\n"
        + "Parameters: startDate and endDate (endDate must be later or equal to startDate)\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DATE + "today "
        + PREFIX_DATE + "tomorrow ";

    public static final String SHOWING_GRAPH_MESSAGE = "Opened graph window.";

    private HashMap<Tag, Double> spendingList;

    private Date startDate;
    private Date endDate;

    /**
     * Creates a GraphCommand with whole date range of all spending
     * if date range is not specified.
     */
    public GraphCommand() {
        startDate = null;
        endDate = null;
    }

    /**
     * Creates a GraphCommand with specified date range.
     */
    public GraphCommand(Date startingDate, Date endingDate) {
        requireNonNull(startingDate);
        requireNonNull(endingDate);
        startDate = startingDate;
        endDate = endingDate;

    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_GRAPH_MESSAGE, true, false);
    }

    @Override
    public Map<Date, Double> getGraphData(Model model) {
        //filter list based on date range specified, if any.
        List<Spending> lastShownList = filterListByDate(model);
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
        Set<Date> dateSet = getDateSet(lastShownList, dateComparator);
        Map<Date, Double> costPerDateList = getCostSpentPerDate(lastShownList, dateComparator, dateSet);
        return costPerDateList;
    }

    private Map<Date, Double> getCostSpentPerDate(List<Spending> lastShownList, Comparator<Date> dateComparator, Set<Date> dateSet) {
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

    private Set<Date> getDateSet(List<Spending> lastShownList, Comparator<Date> dateComparator) {
        Set<Date> dateSet = new TreeSet<>(dateComparator);
        //Get list of distinct dates
        for (Spending i: lastShownList) {
            dateSet.add(i.getDate());
        }
        return dateSet;
    }

    private List<Spending> filterListByDate(Model model) {
        List<Spending> lastShownList;
        //Filters list based on date range if date range is specified.
        if (startDate != null && endDate != null) {
            lastShownList = model.getFilteredSpendingList().filtered(s-> {
                return s.getDate().value.compareTo(startDate.value) >= 0
                    && s.getDate().value.compareTo(endDate.value) <= 0;
            });
        } else {
            lastShownList = model.getFilteredSpendingList();
        }
        return lastShownList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            //Comparing null dates
            || (other instanceof GraphCommand // instanceof handles nulls
            && (startDate == ((GraphCommand) other).startDate)
            && endDate == ((GraphCommand) other).endDate)
            //Comparing valid dates
            || (other instanceof GraphCommand // instanceof handles nulls
            && (startDate.equals(((GraphCommand) other).startDate)
            && endDate.equals(((GraphCommand) other).endDate)));
    }
}
