package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String amount;
    private final String date;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("amount") String amount, @JsonProperty("date") String date,
                                  @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.amount = amount;
        this.date = date;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jason use.
     */
    public JsonAdaptedTransaction(BankAccountOperation source) {
        amount = source.getAmount().toString();
        date = source.getDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public BankAccountOperation toModelType() throws IllegalValueException {
        final List<Tag> transactionTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            transactionTags.add(tag.toModelType());
        }

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        final Set<Tag> modelTags = new HashSet<>(transactionTags);
        // temporary return InTransaction to store transaction (should eventually return in or out transaction)

        return new InTransaction(new Amount(Double.parseDouble(amount)), new Date(date), modelTags);
    }

}
