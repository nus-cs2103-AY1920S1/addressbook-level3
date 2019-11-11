package seedu.address.model.finance.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Has more information on a {@code Budget} regarding the status
 * of the budget
 */
public class BudgetData {

    protected final Double currAmt; // total amount so far
    protected final Double limitAmt;
    protected final Budget budget;

    public BudgetData(Budget budget, ObservableList<LogEntry> logEntries) {
        requireAllNonNull(budget, logEntries);
        this.budget = budget;
        currAmt = getCurrAmt(this.budget, logEntries);
        limitAmt = budget.getAmount().amount;
    }

    public Double getCurrAmt() {
        return currAmt;
    }

    private Double getCurrAmt(Budget budget, ObservableList<LogEntry> logEntries) {
        Date startDate = budget.getStartDate();
        Date endDate = budget.getEndDate();
        String budgetType = budget.getBudgetType();
        String budgetTypeValue = budget.getBudgetTypeValue();
        List<LogEntry> spendLogEntries =
                logEntries
                        .stream()
                        .filter(log -> log instanceof SpendLogEntry)
                        .filter(log -> isWithinDates(log.getTransactionDate().value, startDate, endDate))
                        .collect(Collectors.toList());
        String keyWord = budgetTypeValue == null ? "" : budgetTypeValue.trim();
        Double currAmount;
        switch (budgetType) {
        case "all":
            currAmount = spendLogEntries
                    .stream()
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
            break;
        case "met":
            currAmount = spendLogEntries
                    .stream()
                    .filter(log -> log.getTransactionMethod().value.equals(keyWord))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
            break;
        case "cat":
            currAmount = spendLogEntries
                    .stream()
                    .filter(log -> log.getCategories().contains(new Category(keyWord)))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
            break;
        case "place":
        default:
            currAmount = spendLogEntries
                    .stream()
                    .filter(log -> ((SpendLogEntry) log).getPlace().value.equals(keyWord))
                    .map(log -> log.getAmount().amount)
                    .mapToDouble(log -> log)
                    .sum();
        }
        return currAmount;
    }

    public Double getLimitAmt() {
        return limitAmt;
    }

    public boolean hasExceeded() {
        return currAmt > limitAmt;
    }

    public boolean isCloseToExceed() {
        return getProportion() >= 0.8 && getProportion() <= 1;
    }

    public Double getProportion() {
        return currAmt / limitAmt;
    }

    public String getBalanceStr() {
        BigDecimal limit = new BigDecimal(limitAmt);
        BigDecimal curr = new BigDecimal(currAmt);
        // Ensure 2 decimal places always
        BigDecimal balance = limit.subtract(curr)
                .setScale(2, RoundingMode.HALF_UP);
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            return "+" + balance.toString();
        }
        return balance.toString();
    }

    /**
     * Returns whether a log entry transaction was within the budget dates.
     */
    private boolean isWithinDates(String tDate, Date start, Date end) {
        try {
            SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");
            validFormat.setLenient(false);
            Date transactionDate = validFormat.parse(tDate);
            return !transactionDate.before(start) && !transactionDate.after(end);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Returns whether budget is still within specified
     * start and end dates.
     */
    public boolean isActive() {
        Date startDate = budget.getStartDate();
        Date endDate = budget.getEndDate();
        Date currDate = new Date();
        return !currDate.before(startDate) && !currDate.after(endDate);
    }

    /**
     * Returns whether a budget has passed its end date.
     */
    public boolean hasEnded() {
        Date endDate = budget.getEndDate();
        Date currDate = new Date();
        return currDate.after(endDate);
    }

    public Date getStartDate() {
        return budget.getStartDate();
    }

    public Date getEndDate() {
        return budget.getEndDate();
    }

    public String getBudgetType() {
        return budget.getBudgetType();
    }

    public String getBudgetTypeValue() {
        return budget.getBudgetTypeValue();
    }
}
