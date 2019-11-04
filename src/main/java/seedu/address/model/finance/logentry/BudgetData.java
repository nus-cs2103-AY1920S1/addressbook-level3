package seedu.address.model.finance.logentry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.finance.attributes.Category;

/**
 * Has more information on a {@code Budget} regarding the status
 * of the budget
 */
public class BudgetData {

    protected final Double currAmt; // total amount so far
    protected final Double limitAmt;
    protected final boolean isActive;
    protected final Budget budget;

    public BudgetData(Budget budget, ObservableList<LogEntry> logEntries) {
        requireAllNonNull(budget, logEntries);
        this.budget = budget;
        currAmt = getCurrAmt(budget.getBudgetType(), budget.getBudgetTypeValue(), logEntries);
        limitAmt = budget.getAmount().amount;
        isActive = isActive(budget.getEndDate());
    }

    public Double getCurrAmt() {
        return currAmt;
    }

    private Double getCurrAmt(String budgetType, String budgetTypeValue, ObservableList<LogEntry> logEntries) {
        List<LogEntry> spendLogEntries =
                logEntries
                        .stream()
                        .filter(log -> log instanceof SpendLogEntry)
                        .collect(Collectors.toList());
        String keyWord = budgetType.toLowerCase().trim();
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

    /**
     * Returns whether budget is still within specified
     * start and end dates.
     */
    public boolean isActive() {
        return isActive;
    }

    private boolean isActive(Date endDate) {
        Date currDate = new Date();
        return !currDate.after(endDate);
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
