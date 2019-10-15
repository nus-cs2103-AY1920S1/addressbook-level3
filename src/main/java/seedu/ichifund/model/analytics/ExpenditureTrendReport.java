package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Transaction;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a report for expenditure trends.
 */
public class ExpenditureTrendReport extends TrendReport {

    /**
     * Constructs a {@code ExpenditureTrendReport}.
     *
     * @param month A month.
     * @param year A year.
     */
    public ExpenditureTrendReport(Month month, Year year) {
        super(month, year);
    }

    /**
     * Fetches the relevant transaction information to generate the expenditure trend report.
     *
     * @param model {@code Model} which the report should be based on.
     * @param year {@code year} which the report should be based on.
     */
    @Override
    public List<Amount> fetch(Model model, Year year) {
        requireNonNull(model);
        requireNonNull(year);
        ObservableList<Transaction> transactionList = model.getFundBook().getTransactionList();
        List<Amount> monthlyExpenditureList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Month currentMonth = new Month(Integer.toString(i+1));
            List<Amount> currentMonthExpenditureList = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                if (transaction.isIn(year) && transaction.isIn(currentMonth) && transaction.isExpenditure()) {
                    currentMonthExpenditureList.add(transaction.getAmount());
                }
            }
            Amount currentMonthExpenditure = Amount.addAll(currentMonthExpenditureList);
            monthlyExpenditureList.add(currentMonthExpenditure);
        }
        return monthlyExpenditureList;
    }
}
