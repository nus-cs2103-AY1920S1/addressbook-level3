package thrift.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import thrift.commons.exceptions.IllegalValueException;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String type;
    private final String description;
    private final String value;
    private final String date;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("type") String type, @JsonProperty("description") String description,
            @JsonProperty("value") String value, @JsonProperty("date") String date,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.type = type;
        this.description = description;
        this.value = value;
        this.date = date;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        if (source instanceof Expense) {
            type = "expense";
        } else {
            type = "income";
        }
        description = source.getDescription().toString();
        value = source.getValue().toString();
        date = source.getDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {
        final List<Tag> transactionTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            transactionTags.add(tag.toModelType());
        }

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Transaction.class.getSimpleName()));
        }
        final String modelType = type;

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Value.class.getSimpleName()));
        }
        if (!Value.isValidValue(value)) {
            throw new IllegalValueException(Value.VALUE_CONSTRAINTS);
        }
        final Value modelValue = new Value(value);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionDate.class.getSimpleName()));
        }
        if (!TransactionDate.isValidDate(date)) {
            throw new IllegalValueException(TransactionDate.DATE_CONSTRAINTS);
        }
        final TransactionDate modelDate = new TransactionDate(date);

        final Set<Tag> modelTags = new HashSet<>(transactionTags);

        if (modelType.equals("expense")) {
            return new Expense(modelDescription, modelValue, modelDate, modelTags);
        } else {
            return new Income(modelDescription, modelValue, modelDate, modelTags);
        }
    }

}
