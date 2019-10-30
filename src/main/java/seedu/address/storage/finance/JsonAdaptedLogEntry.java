package seedu.address.storage.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Jackson-friendly version of {@link LogEntry}.
 */

//@@author jbarrueta-reused
//Reused from https://stackoverflow.com/questions/30362446/deserialize-json-with-jackson-into
// -polymorphic-types-a-complete-example-is-giv/30386694#30386694 with minor modifications
// To save polymorphic log entries
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedSpendLogEntry.class, name = "AdaptedSpendLogEntry"),
        @JsonSubTypes.Type(value = JsonAdaptedIncomeLogEntry.class, name = "AdaptedIncomeLogEntry"),
        @JsonSubTypes.Type(value = JsonAdaptedBorrowLogEntry.class, name = "AdaptedBorrowLogEntry"),
        @JsonSubTypes.Type(value = JsonAdaptedLendLogEntry.class, name = "AdaptedLendLogEntry") }
)
//@@author
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
                               @JsonProperty("transactionDate") String transactionDate,
                               @JsonProperty("description") String desc,
                               @JsonProperty("transactionMethod") String transactionMethod,
                               @JsonProperty("categories") List<JsonAdaptedCategory> categories) {
        this.amount = amount;
        this.tDate = transactionDate;
        this.desc = desc;
        this.tMethod = transactionMethod;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code LogEntry} into this class for Jackson use.
     */
    public JsonAdaptedLogEntry(LogEntry source) {
        amount = source.getAmount().toString();
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
