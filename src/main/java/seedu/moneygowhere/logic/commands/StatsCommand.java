package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Displays statistics of the user's spending.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_SUCCESS = "Statistics of all spending displayed below.\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates statistics of all spendings within the date range specified "
        + "by the user input. If no user input is given, the date range will be the whole date range.\n"
        + "Parameters: startDate and endDate (endDate must be later or equal to startDate)\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DATE + "today "
        + PREFIX_DATE + "tomorrow ";

    private Date startDate;
    private Date endDate;

    /**
     * Creates a StatsCommand with whole date range of all spending
     * if date range is not specified.
     */
    public StatsCommand() {
        startDate = null;
        endDate = null;
    }

    /**
     * Creates a StatsCommand with specified date range.
     */
    public StatsCommand(Date startingDate, Date endingDate) {
        requireNonNull(startingDate);
        requireNonNull(endingDate);
        startDate = startingDate;
        endDate = endingDate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Spending> lastShownList = filterListByDate(model);

        double totalCost = getTotalCost(lastShownList);
        String feedbackToUser = getStringBudgetStatus(model, totalCost);

        Set<Tag> tagSet = getTagsOfSpendings(lastShownList);
        HashMap<Tag, Double> costPerTagList = getCostPerTagList(lastShownList, tagSet);
        Map<Tag, Double> sorted = sortCostPerTagList(costPerTagList);
        feedbackToUser = getStringCostPerTag(totalCost, feedbackToUser, sorted);

        return new CommandResult(feedbackToUser);
    }

    private double getTotalCost(List<Spending> lastShownList) {
        double totalCost = 0;

        for (Spending i: lastShownList) {
            totalCost += Double.parseDouble(i.getCost().toString());
        }

        return totalCost;
    }

    /**
     * Returns a set of tags representing tags of filtered spendings.
     */
    private Set<Tag> getTagsOfSpendings(List<Spending> lastShownList) {
        Set<Tag> tagSet = new HashSet<>();

        for (Spending i: lastShownList) {
            Set<Tag> spendingTags = i.getTags();
            tagSet.addAll(spendingTags);
        }

        return tagSet;
    }

    /**
     * Returns a hashmap which shows cost spent per tag.
     */
    private HashMap<Tag, Double> getCostPerTagList(List<Spending> lastShownList, Set<Tag> tagSet) {
        HashMap<Tag, Double> costPerTagList = new HashMap<Tag, Double>();

        for (Tag e: tagSet) {
            costPerTagList.put(e, 0.00);
        }

        for (Spending a: lastShownList) {
            Set<Tag> aTags = a.getTags();
            for (Tag b: tagSet) {
                if (aTags.contains(b)) {
                    double currentCost = costPerTagList.get(b);
                    double updatedCost = currentCost + Double.parseDouble(a.getCost().toString());
                    costPerTagList.replace(b, updatedCost);
                }
            }
        }

        return costPerTagList;
    }

    /**
     * Returns a sorted map which sorts according to cost per tag in descending order.
     */
    private Map<Tag, Double> sortCostPerTagList(HashMap<Tag, Double> costPerTagList) {
        return costPerTagList
            .entrySet()
            .stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(
                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                    LinkedHashMap::new));
    }

    /**
     * Returns a list of spending which is filtered by the date range specified.
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

    /**
     * Returns a string which provides a summary of the budget and expenditure status.
     */
    private String getStringBudgetStatus(Model model, double totalCost) {
        double budget = model.getBudget().getValue();
        double budgetRemaining = budget - totalCost;

        String feedbackToUser;

        if (budgetRemaining >= 0) {
            String s = MESSAGE_SUCCESS
                + "\nTotal Cost: $" + String.format("%.2f", totalCost)
                + "\nBudget: $" + String.format("%.2f", budget)
                + "\nBudget Remaining: $" + String.format("%.2f", budgetRemaining)
                + "\nStatus: Safe";
            feedbackToUser = s;
        } else {
            String s = MESSAGE_SUCCESS
                + "\nTotal Cost: $" + String.format("%.2f", totalCost)
                + "\nBudget: $" + String.format("%.2f", budget)
                + "\nBudget Remaining: -$" + String.format("%.2f", -1 * budgetRemaining)
                + "\nStatus: Deficit";
            feedbackToUser = s;
        }

        return feedbackToUser;
    }

    /**
     * Returns a string which provides a summary of the tags and the cost incurred for each tag.
     */
    private String getStringCostPerTag(double totalCost, String feedbackToUser, Map<Tag, Double> sorted) {
        String updatedFeedback = feedbackToUser;
        updatedFeedback += "\n\nSpending by Tags:";

        if (sorted.size() == 0) {
            updatedFeedback += "\nNone";
        }

        int counter = 1;
        for (Map.Entry<Tag, Double> entry : sorted.entrySet()) {
            double percentage = (entry.getValue() / totalCost) * 100;
            updatedFeedback += String.format("\n%d. %s: $%.2f (%.2f%%)", counter, entry.getKey().tagName,
                entry.getValue(), percentage);
            counter++;
        }
        return updatedFeedback;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            //Comparing null dates
            || (other instanceof StatsCommand // instanceof handles nulls
            && (startDate == ((StatsCommand) other).startDate)
            && endDate == ((StatsCommand) other).endDate)
            //Comparing valid dates
            || (other instanceof StatsCommand // instanceof handles nulls
            && (startDate.equals(((StatsCommand) other).startDate)
            && endDate.equals(((StatsCommand) other).endDate)));
    }
}
