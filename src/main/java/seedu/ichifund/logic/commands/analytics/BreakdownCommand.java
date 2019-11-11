package seedu.ichifund.logic.commands.analytics;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.analytics.BreakdownReport;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Generates a report for expenditure breakdown by category.
 */
public class BreakdownCommand extends Command {

    public static final String COMMAND_WORD = "breakdown";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays breakdown of expenditure by category for the "
            + "month and/or year specified, or current month and/or year if month and/or year is unspecified."
            + "Parameters: "
            + "[" + PREFIX_MONTH + "MONTH] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "7 "
            + PREFIX_YEAR + "2019 ";

    public static final String MESSAGE_SUCCESS = "Breakdown of expenditure by category for %1$s %2$s displayed.";

    public static final String REPORT_DESCRIPTION = "Total expenditure on %1$s for %2$s %3$s";

    protected final Month month;
    protected final Year year;

    /**
     * Constructs a {@code BreakdownCommand}.
     *
     * @param month A month.
     * @param year A year.
     */
    public BreakdownCommand(Optional<Month> month, Optional<Year> year) {
        requireNonNull(month);
        requireNonNull(year);
        this.month = month.orElseGet(Month::getCurrent);
        this.year = year.orElseGet(Year::getCurrent);
    }

    protected BreakdownReport createBreakdownReport(Month month, Year year) {
        requireNonNull(month);
        requireNonNull(year);
        return new BreakdownReport(month, year);
    }

    /**
     * Fills a {@code BreakdownReport} using data from a {@code Model}.
     *
     * @param model Model to be referenced.
     * @param report Report to be filled.
     */
    void fillBreakdownReport(Model model, BreakdownReport report) {
        requireNonNull(model);
        requireNonNull(report);
        ObservableList<Transaction> transactionList = model.getFundBook().getTransactionList();
        Map<Category, Amount> categoricalExpenditureMap = new HashMap<>();
        List<Data> categoricalExpenditureList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.isIn(year) && transaction.isIn(month) && transaction.isExpenditure()) {
                if (categoricalExpenditureMap.containsKey(transaction.getCategory())) {
                    categoricalExpenditureMap.put(transaction.getCategory(),
                            Amount.add(categoricalExpenditureMap.get(transaction.getCategory()),
                                    transaction.getAmount()));
                } else {
                    categoricalExpenditureMap.put(transaction.getCategory(), transaction.getAmount());
                }
            }
        }
        for (Map.Entry<Category, Amount> entry : categoricalExpenditureMap.entrySet()) {
            Category category = entry.getKey();
            Amount amount = entry.getValue();
            Data currentData = new Data(String.format(REPORT_DESCRIPTION, category.toString(), month.wordString(),
                    year.toString()), amount, Optional.of(year),
                    Optional.of(month), Optional.empty(), Optional.of(category));
            categoricalExpenditureList.add(currentData);
        }
        report.fillReport(categoricalExpenditureList);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        BreakdownReport report = createBreakdownReport(month, year);
        fillBreakdownReport(model, report);
        model.updateDataList(report.getBreakdownList());
        model.updateCommand(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, month.wordString(), year.toString()));
    }
}
