package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.UniquePersonList;

import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.LendMoney;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.transaction.Split;
import seedu.address.model.util.Date;

/**
 * Jackson-friendly version of {@link JsonAdaptedLedgerOperations}.
 */
public class JsonAdaptedLedgerOperations {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "LedgerOperation's %s field is missing!";

    private final String date;
    private final String amount;
    private final String description;
    private final List<JsonAdaptedPerson> people = new ArrayList<>();
    private final List<String> shares = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedLedgerOperations(@JsonProperty("date") String date,
                                       @JsonProperty("amount") String amount,
                                       @JsonProperty("description") String description,
                                       @JsonProperty("people") List<JsonAdaptedPerson> people,
                                       @JsonProperty("shares") List<String> shares) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        if (people != null) {
            this.people.addAll(people);
        }
        if (shares != null) {
            this.shares.addAll(shares);
        }
    }

    public JsonAdaptedLedgerOperations(LedgerOperation source) {
        date = source.getDate().toString();
        amount = source.getAmount().toString();
        description = source.getDescription().toString();
        people.addAll(source.getPeopleInvolved().asUnmodifiableObservableList()
            .stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        shares.addAll(source.getShares().stream()
            .map(i -> i.toString()).collect(Collectors.toList()));
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

        if (!Date.isValid(date)) {
            throw new IllegalValueException(Date.MESSAGE_FORMAT_CONSTRAINTS);
        }

        final Date modelDate = new Date(date);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(Double.parseDouble(amount))) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (people == null || people.size() == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                UniquePersonList.class.getSimpleName()));
        }

        final UniquePersonList peopleInvolved = new UniquePersonList();
        peopleInvolved.setPersons(people.stream().map(jsonAdaptedPerson -> {
            try {
                return jsonAdaptedPerson.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));

        if (shares == null || shares.size() == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "shares"));
        }
        final List<Integer> modelShares = shares.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());


        if (shares.size() == 1) {
            return modelAmount.isNegative()
                ? new LendMoney(people.get(0).toModelType(), modelAmount.makePositive(), modelDate, modelDescription)
                : new ReceiveMoney(people.get(0).toModelType(), modelAmount, modelDate, modelDescription);
        } else {
            if (!Split.isValidSharesLength(modelShares, peopleInvolved)) {
                throw new IllegalValueException(Split.SHARE_CONSTRAINTS);
            }
            return new Split(modelAmount.makePositive(), modelDate, modelDescription, modelShares, peopleInvolved);
        }
    }
}
