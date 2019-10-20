package seedu.address.storage.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * An Immutable FinanceLog that is serializable to JSON format.
 */
@JsonRootName(value = "financelog")
class JsonSerializableFinanceLog {

    private final List<JsonAdaptedLogEntry> logEntries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinanceLog} with the given log entries.
     */
    @JsonCreator
    public JsonSerializableFinanceLog(@JsonProperty("logEntries") List<JsonAdaptedSpendLogEntry> logEntries) {
        this.logEntries.addAll(logEntries);
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
            return new JsonAdaptedSpendLogEntry(amount, tDate, desc, tMethod, categories, logEntryType);
        default:
            return new JsonAdaptedSpendLogEntry(amount, tDate, desc, tMethod, categories, logEntryType);
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
        return financeLog;
    }

}
