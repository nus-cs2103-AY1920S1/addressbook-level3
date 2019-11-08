package seedu.address.storage.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.finance.commands.BudgetCommand;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.budget.Budget;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * An Immutable FinanceLog that is serializable to JSON format.
 */
@JsonRootName(value = "financelog")
class JsonSerializableFinanceLog {

    private final List<JsonAdaptedLogEntry> logEntries = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinanceLog} with the given log entries.
     */
    @JsonCreator
    public JsonSerializableFinanceLog(@JsonProperty("logEntries") List<JsonAdaptedLogEntry> logEntries,
                                      @JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        this.logEntries.addAll(logEntries);
        this.budgets.addAll(budgets);
    }

    /**
     * Converts a given {@code ReadOnlyFinanceLog} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinanceLog}.
     */
    public JsonSerializableFinanceLog(ReadOnlyFinanceLog source) {
        logEntries.addAll(source.getLogEntryList()
                .stream().map((log) -> createLogEntry(log))
                .collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList()
                .stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
    }

    /**
     * Returns the appropriate child class of {@code JsonAdaptedLogEntry}
     * according to log entry type.
     */
    private JsonAdaptedLogEntry createLogEntry(LogEntry log) {
        String amount = log.getAmount().toString();
        String tDate = log.getTransactionDate().toString();
        String desc = log.getDescription().toString();
        String tMethod = log.getTransactionMethod().toString();
        List<JsonAdaptedCategory> categories = new ArrayList<>();
        categories.addAll(log.getCategories()
                .stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));

        String logEntryType = log.getLogEntryType();

        switch (logEntryType) {

        case SpendLogEntry.LOG_ENTRY_TYPE:
            SpendLogEntry spendLogEntry = (SpendLogEntry) log;
            String place = spendLogEntry.getPlace().toString();
            return new JsonAdaptedSpendLogEntry(amount, tDate, desc, tMethod, categories, logEntryType, place);

        case IncomeLogEntry.LOG_ENTRY_TYPE:
            IncomeLogEntry incomeLogEntry = (IncomeLogEntry) log;
            String src = incomeLogEntry.getFrom().name;
            return new JsonAdaptedIncomeLogEntry(amount, tDate, desc, tMethod, categories, logEntryType, src);

        case BorrowLogEntry.LOG_ENTRY_TYPE:
            BorrowLogEntry borrowLogEntry = (BorrowLogEntry) log;
            String borrowedFrom = borrowLogEntry.getFrom().name;
            boolean isRepaid = borrowLogEntry.isRepaid();
            String isRepaidStr = Boolean.toString(isRepaid);
            String repaidDate = null;
            if (isRepaid) {
                repaidDate = borrowLogEntry.getRepaidDate().value;
            }
            return new JsonAdaptedBorrowLogEntry(amount, tDate, desc, tMethod, categories, logEntryType,
                    borrowedFrom, isRepaidStr, repaidDate);
        case LendLogEntry.LOG_ENTRY_TYPE:
        default:
            LendLogEntry lendLogEntry = (LendLogEntry) log;
            String lentTo = lendLogEntry.getTo().name;
            isRepaid = lendLogEntry.isRepaid();
            isRepaidStr = Boolean.toString(isRepaid);
            repaidDate = null;
            if (isRepaid) {
                repaidDate = lendLogEntry.getRepaidDate().value;
            }
            return new JsonAdaptedLendLogEntry(amount, tDate, desc, tMethod, categories, logEntryType,
                    lentTo, isRepaidStr, repaidDate);
        }
    }

    /**
     * Converts this finance log into the model's {@code FinanceLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceLog toModelType() throws IllegalValueException {
        FinanceLog financeLog = new FinanceLog();
        for (JsonAdaptedLogEntry jsonAdaptedLogEntry : logEntries) {
            LogEntry logEntry = jsonAdaptedLogEntry.toModelType();
            financeLog.addLogEntry(logEntry);
        }
        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            if (financeLog.hasBudget(budget)) {
                throw new IllegalValueException(BudgetCommand.MESSAGE_DUPLICATE_BUDGET);
            }
            financeLog.addBudget(budget);
        }
        return financeLog;
    }

}
