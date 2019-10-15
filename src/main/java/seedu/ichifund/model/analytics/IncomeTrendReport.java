package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Represents a report for income trends.
 */
public class IncomeTrendReport extends TrendReport {

    /**
     * Constructs a {@code IncomeTrendReport}.
     *
     * @param month A month.
     * @param year A year.
     */
    public IncomeTrendReport(Month month, Year year) {
        super(month, year);
    }

    /**
     * Fetches the relevant transaction information to generate the income trend report.
     *
     * @param model {@code Model} which the report should be based on.
     * @param year {@code year} which the report should be based on.
     */
    @Override
    public List<Amount> fetch(Model model, Year year) {
        requireNonNull(model);
        requireNonNull(year);
        ObservableList<Transaction> transactionList = model.getFundBook().getTransactionList();
        List<Amount> monthlyIncomeList = new ArrayList<>();
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
        }
        return monthlyIncomeList;
    }
}
