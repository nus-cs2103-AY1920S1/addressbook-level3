package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link JsonAdaptedLedgerOperations}.
 */
public class JsonAdaptedLedgerOperations {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ledger's %s field is missing!";

    private final String date;
    private final String amount;
    private final List<JsonAdaptedPerson> people = new ArrayList<>();

    // TODO: include peopleInvolved
    @JsonCreator
    public JsonAdaptedLedgerOperations(@JsonProperty("date") String date,
                                       @JsonProperty("amount") String amount,
                                       @JsonProperty("people") List<JsonAdaptedPerson> people) {
        this.date = date;
        this.amount = amount;
        if (people != null) {
            this.people.addAll(people);
        }
    }

    public JsonAdaptedLedgerOperations(LedgerOperation source) {
        date = source.getDate().toString();
        amount = source.getAmount().toString();
        people.addAll(source.getPeopleInvolved().asUnmodifiableObservableList()
                        .stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted ledger object into the model's {@code Ledger} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ledger.
     */
    public LedgerOperation toModelType() throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        final Date modelDate = new Date(date);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        // TODO
        return new ReceiveMoney(modelDate, modelAmount);
    }
}
