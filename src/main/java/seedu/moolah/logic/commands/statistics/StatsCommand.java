package seedu.moolah.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Optional;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.model.statistics.PieChartStatistics;
import seedu.moolah.ui.StatsPanel;

/**
 * Calculates statistics for Moolah
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "statsbasic" + CommandGroup.GENERAL;

    public static final String MESSAGE_SUCCESS = "Pie Chart calculated!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates statistics between the Start Date and End Date "
            + "Parameters: "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "11-11-1111 "
            + PREFIX_END_DATE + "12-12-1212 ";

//    private final Timestamp startDate;
//    private final Timestamp endDate;

    private StatsDescriptor statsDescriptor;
    /**
     * Creates a StatsCommand to calculate statistics between 2 dates {@code Timestamp}
     */
//    public StatsCommand(Timestamp startDate, Timestamp endDate) {
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
    //can consider this to be private now that there are other static methods available
    public StatsCommand(StatsDescriptor statsDescriptor) {
        requireNonNull(statsDescriptor);
        this.statsDescriptor = statsDescriptor;
    }



//    /**
//     * Creates a StatsCommand that only has a start date
//     * @param startDate The start date
//     */
//    public static StatsCommand createOnlyWithStartDate(Timestamp startDate) {
//        requireNonNull(startDate);
//        return new StatsCommand(startDate, null);
//    }
//
//    /**
//     * Creates a StatsCommand that only has an end date
//     * @param endDate The end date
//     */
//    public static StatsCommand createOnlyWithEndDate(Timestamp endDate) {
//        requireNonNull(endDate);
//        return new StatsCommand(null, endDate);
//    }
//
//    /**
//     * Creates a StatsCommand that contains both a start date and end date
//     * @param startDate The start date
//     * @param endDate The end date
//     */
//    public static StatsCommand createWithBothDates(Timestamp startDate, Timestamp endDate) {
//        requireNonNull(startDate);
//        requireNonNull(endDate);
//        return new StatsCommand(startDate, endDate);
//    }
//
//    /**
//     * Creates a StatsCommand that does not contain a start date or end date
//     */
//    public static StatsCommand createWithNoDate() {
//        return new StatsCommand(null, null);
//    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasPrimaryBudget()) {
            throw new CommandException(MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET);
        }
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
//        model.calculateStatistics(COMMAND_WORD, startDate, endDate, false);


//        List<Expense> lastShownList = model.getFilteredExpenseList();
//        Expense expenseToEdit = lastShownList.get(index.getZeroBased());
//        Expense editedExpense = createEditedExpense(expenseToEdit, editExpenseDescriptor);


//        model.setExpense(expenseToEdit, editedExpense);
//        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        Budget primaryBudget = model.getPrimaryBudget();
        Statistics statistics = createPieChartStatistics(primaryBudget, statsDescriptor);
        model.setStatistics(statistics);

        return new CommandResult(MESSAGE_SUCCESS, false, false, StatsPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsCommand // instance of handles nulls
                && statsDescriptor.equals(((StatsCommand) other).statsDescriptor));
//                && startDate.equls(((StatsCommand) other).startDate)
//                && endDate.equals(((StatsCommand) other).endDate));

    }


    //copy the "stats" which is primary budget object, now we want make the Statistics object in logic instead of the Big Model parser class
    //everything is orElse, I guess this is where the logic comes from, like what the descriptor getDate and it happens to be optional
    //the expense though a model object requires optional things to make the object first
    //Command make object
    //so StatsCommand makes Stats object, not the command

    //we cannot do without order because not independent

    //createdPieChartStatistics

    private static PieChartStatistics createPieChartStatistics(Budget primaryBudget, StatsDescriptor statsDescriptor) {
        requireNonNull(primaryBudget);
        Optional<Timestamp> startDate = statsDescriptor.getStartDate();
        Optional<Timestamp> endDate = statsDescriptor.getEndDate();
        //PieChart model stats logic

        boolean isStartPresent = startDate.isPresent();
        boolean isEndPresent = endDate.isPresent();



        if (!isStartPresent && !isEndPresent) {
            startDate = Optional.of(primaryBudget.getWindowStartDate());
            endDate = Optional.of(primaryBudget.getWindowEndDate());
        } else if (isStartPresent && !isEndPresent) {
            endDate = Optional.of(startDate.get().createForwardTimestamp(primaryBudget.getBudgetPeriod()).minusDays(1));
        } else if (!isStartPresent) {
            startDate = Optional.of(endDate.get().createBackwardTimestamp(primaryBudget.getBudgetPeriod()).plusDays(1));
        }

        //do this first then later chang the constructor, later can just pass in expenses, no need the windows
        return PieChartStatistics.run(Category.getValidCategories(), startDate.get(), endDate.get(), primaryBudget);
        //return new PieChartStatistics()
    }


}


