package seedu.ichifund.logic.commands.analytics;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.analytics.TrendReport;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Generates an expenditure trend report.
 */
public class IncomeTrendCommand extends TrendCommand {

    public static final String COMMAND_WORD = "income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays monthly income trend for the year "
            + "specified, or current year if year is unspecified."
            + "Parameters: "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_YEAR + "2019 ";

    public static final String MESSAGE_SUCCESS = "Monthly income trend for %1$s displayed.";

    public static final String REPORT_DESCRIPTION = "Total income for %1$s %2$s";

    /**
     * Constructs a {@code IncomeTrendCommand}.
     *
     * @param year A year.
     */
    public IncomeTrendCommand(Optional<Year> year) {
        super(year);
    }

    /**
     * Fills a {@code TrendReport} using data from a {@code Model}.
     *
     * @param model Model to be referenced.
     * @param report Report to be filled.
     */
    private void fillIncomeTrendReport(Model model, TrendReport report) {
        requireNonNull(model);
        requireNonNull(report);
        ObservableList<Transaction> transactionList = model.getFundBook().getTransactionList();
        List<Data> monthlyIncomeList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Month currentMonth = new Month(Integer.toString(i + 1));
            List<Amount> currentMonthIncomeList = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                if (transaction.isIn(year) && transaction.isIn(currentMonth) && !(transaction.isExpenditure())) {
                    currentMonthIncomeList.add(transaction.getAmount());
                }
            }
            Amount currentMonthIncome = Amount.addAll(currentMonthIncomeList);
            currentMonthIncomeList.add(currentMonthIncome);
            Data currentData = new Data(String.format(REPORT_DESCRIPTION, currentMonth.wordString(), year.toString()),
                    currentMonthIncome, Optional.of(year),
                    Optional.of(currentMonth), Optional.empty(), Optional.empty());
            monthlyIncomeList.add(currentData);
        }
        report.fillReport(monthlyIncomeList);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        TrendReport report = createTrendReport(year);
        fillIncomeTrendReport(model, report);
        model.updateDataList(report.getTrendList());
        model.updateCommand(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, year.toString()));
    }
}
