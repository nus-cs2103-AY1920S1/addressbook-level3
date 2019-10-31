package seedu.ichifund.logic.commands.analytics;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.analytics.TrendReport;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Generates a report for expenditure ranking.
 */
public class ExpenditureRankingCommand extends Command {

    public static final String COMMAND_WORD = "exprank";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays expenditure ranking chart for the "
            + "month and year specified, or current month and year if year is unspecified, "
            + "or current year if month and year are unspecified."
            + "Parameters: "
            + "[" + PREFIX_MONTH + "MONTH] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "7 "
            + PREFIX_YEAR + "2019 ";

    public static final String MESSAGE_SUCCESS = "Expenditure ranking chart for %1$s %2$s displayed.";

    protected final Optional<Month> month;
    protected final Year year;

    /**
     * Constructs a {@code ExpenditureRankingCommand}.
     *
     * @param month A month.
     * @param year A year.
     */
    public ExpenditureRankingCommand(Optional<Month> month, Optional<Year> year) {
        requireNonNull(month);
        requireNonNull(year);
        this.month = month;
        this.year = year.orElseGet(Year::getCurrent);
    }

    protected TrendReport createTrendReport(Year year) {
        requireNonNull(year);
        return new TrendReport(year);
    }

    /**
     * Fills a {@code TrendReport} using data from a {@code Model}.
     *
     * @param model Model to be referenced.
     * @param report Report to be filled.
     */
    private void fillExpenditureRankingTrendReport(Model model, TrendReport report) {
        requireNonNull(model);
        requireNonNull(report);
        ObservableList<Transaction> transactionList = model.getFundBook().getTransactionList();
        List<Data> expenditureList = new ArrayList<>();
        if (month.isPresent()) {
            for (Transaction transaction : transactionList) {
                if (transaction.isIn(year) && transaction.isIn(month.get()) && transaction.isExpenditure()) {
                    Data currentData = new Data(transaction.getDescription().toString(), transaction.getAmount(),
                            Optional.ofNullable(transaction.getDate().getYear()),
                            Optional.ofNullable(transaction.getDate().getMonth()),
                            Optional.ofNullable(transaction.getDate().getDay()),
                            Optional.ofNullable(transaction.getCategory()));
                    expenditureList.add(currentData);
                }
            }
        } else {
            for (Transaction transaction : transactionList) {
                if (transaction.isIn(year) && transaction.isExpenditure()) {
                    Data currentData = new Data(transaction.getDescription().toString(), transaction.getAmount(),
                            Optional.ofNullable(transaction.getDate().getYear()),
                            Optional.ofNullable(transaction.getDate().getMonth()),
                            Optional.ofNullable(transaction.getDate().getDay()),
                            Optional.ofNullable(transaction.getCategory()));
                    expenditureList.add(currentData);
                }
            }
        }
        report.fillReport(expenditureList);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        TrendReport report = createTrendReport(year);
        fillExpenditureRankingTrendReport(model, report);
        model.updateDataList(report.getSortedTrendList());
        if (month.isPresent()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, month.get().wordString(), year.toString()));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, "the whole of", year.toString()));
        }
    }
}
