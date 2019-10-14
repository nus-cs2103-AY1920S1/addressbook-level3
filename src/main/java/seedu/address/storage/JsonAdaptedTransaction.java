package seedu.address.storage;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.Transaction;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    // TODO: Have to convert to Strings
    private final String amount;
    private final String date;
    private final JsonAdaptedPerson peopleInvolved;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("amount") String amount, @JsonProperty("date") String date,
                             @JsonProperty("people") JsonAdaptedPerson peopleInvolved,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.amount = amount;
        this.date = date;
        this.peopleInvolved = peopleInvolved;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        amount = source.getAmount().toString();
        date = source.getDate().toString();
        peopleInvolved = new JsonAdaptedPerson(source.getPeopleInvolved());
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Transaction toModelType() throws IllegalValueException {
        final List<Tag> transactionTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            transactionTags.add(tag.toModelType());
        }

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Integer.parseInt(amount))) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(Integer.parseInt(amount));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        Person person = this.peopleInvolved.toModelType();

        final Set<Tag> modelTags = new HashSet<>(transactionTags);
        return new InTransaction(new Amount(Integer.parseInt(amount)), new Date(date)); //temporary return InTransaction to store transaction (should eventually return in or out transaction)
    }

}
