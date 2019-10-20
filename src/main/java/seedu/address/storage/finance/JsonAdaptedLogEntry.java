package seedu.address.storage.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Jackson-friendly version of {@link LogEntry}.
 */
abstract class JsonAdaptedLogEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log entry's %s field is missing!";

    protected final String amount;
    protected final String tDate;
    protected final String desc;
    protected final String tMethod;
    protected final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLogEntry} with the given log entry details.
     */
    @JsonCreator
    public JsonAdaptedLogEntry(@JsonProperty("amount") String amount,
                               @JsonProperty("transactionDate") String tDate,
                               @JsonProperty("description") String desc,
                               @JsonProperty("transactionMethod") String tMethod,
                               @JsonProperty("categories") List<JsonAdaptedCategory> categories) {
        this.amount = amount;
        this.tDate = tDate;
        this.desc = desc;
        this.tMethod = tMethod;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code LogEntry} into this class for Jackson use.
     */
    public JsonAdaptedLogEntry(LogEntry source) {
        amount = source.getAmount().amount;
        tDate = source.getTransactionDate().value;
        desc = source.getDescription().value;
        tMethod = source.getTransactionMethod().value;
        categories.addAll(source.getCategories().stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted log entry object into the model's {@code LogEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log entry.
     */
    public abstract LogEntry toModelType() throws IllegalValueException;

}
