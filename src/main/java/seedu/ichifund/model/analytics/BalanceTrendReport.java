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
 * Represents a report for balance trends.
 */
public class BalanceTrendReport extends TrendReport {

    /**
     * Constructs a {@code BalanceTrendReport}.
     *
     * @param month A month.
     * @param year A year.
     */
    public BalanceTrendReport(Month month, Year year) {
        super(month, year);
    }

    /**
     * Fetches the relevant transaction information to generate the balance trend report.
     *
     * @param model {@code Model} which the report should be based on.
     * @param year {@code year} which the report should be based on.
     */
    @Override
    public List<Amount> fetch(Model model, Year year) {
        requireNonNull(model);
        requireNonNull(year);
        ObservableList<Transaction> transactionList = model.getFundBook().getTransactionList();
        List<Amount> monthlyBalanceList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Month currentMonth = new Month(Integer.toString(i + 1));
            List<Amount> currentMonthExpenditureList = new ArrayList<>();
            List<Amount> currentMonthIncomeList = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                if (transaction.isIn(year) && transaction.isIn(currentMonth)) {
                    if (transaction.isExpenditure()) {
                        currentMonthExpenditureList.add(transaction.getAmount());
                    } else if (!transaction.isExpenditure()) {
                        currentMonthIncomeList.add(transaction.getAmount());
                    }
                }
            }
            Amount currentMonthExpenditure = Amount.addAll(currentMonthExpenditureList);
            Amount currentMonthIncome = Amount.addAll(currentMonthIncomeList);
            Amount currentMonthBalance = Amount.subtract(currentMonthIncome, currentMonthExpenditure);
            monthlyBalanceList.add(currentMonthBalance);
        }
        return monthlyBalanceList;
    }
}
