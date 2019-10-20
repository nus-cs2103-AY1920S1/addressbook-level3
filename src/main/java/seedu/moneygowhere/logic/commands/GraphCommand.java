package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;

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
        List<Spending> lastShownList = filterListByDate(model);
        Set<Date> dateSet = getDateSet(lastShownList);
        return getCostSpentPerDate(lastShownList, dateSet);
    }

    /**
     * Returns a map displaying cost spent per date
     */
    private Map<Date, Double> getCostSpentPerDate(List<Spending> lastShownList, Set<Date> dateSet) {
        Map<Date, Double> costPerDateList = new TreeMap<>();

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

    /**
     * Returns a list of distinct dates from the list of spending provided.
     */
    private Set<Date> getDateSet(List<Spending> lastShownList) {
        Set<Date> dateSet = new TreeSet<>();

        for (Spending i: lastShownList) {
            dateSet.add(i.getDate());
        }

        return dateSet;
    }

    /**
     * Returns a list of spending filtered by the specified date range if provided.
     */
    private List<Spending> filterListByDate(Model model) {
        List<Spending> lastShownList;

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
